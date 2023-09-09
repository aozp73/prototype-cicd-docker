let addCnt = 0
let isEditMode = false;
let sortables = [];

// hedaer.jsp의 편집모드 버튼 클릭 시, 호출되는 콜백 합수
function toggleEditMode() {
    isEditMode = !isEditMode;
    // toggleMode 클래스 : '+' 버튼 & 입력 Form
    let elements = document.querySelectorAll('.toggleMode');

    for (let element of elements) {
        if (!isEditMode) { 
            element.style.display = 'none';  
        } else { 
            element.style.display = '';  
        }
    }

    // layout을 확인하는 모드에서는 row이동을 하지 못하게 진행
    // 기존 Sortable를 파괴하고 'if (isEditMode)' if문을 타지 않음
    initEditMode();
}


function initEditMode() {
    // 기존 Sortable 인스턴스들 파괴
    for (let sortable of sortables) {
        sortable.destroy();
    }
    sortables = [];

    // 편집 모드이면, 어떤 row이동이 가능할지 명시하면서 Sortable 생성 
    if (isEditMode) {
        document.querySelectorAll('.sortableTable tbody').forEach(function(tbody) {
            let sortable = new Sortable(tbody, {
                handle: '.drag-handle',  
                animation: 500,
                filter: '.no-drag', 
                
                // '+'버튼 row, 각 표의 제목 row, 입력 form이 생기는 row에는 이동할 수 없게하는 코드
                onMove: function (evt, originalEvent) {
                    var nextItem = evt.related; 
                    if ($(nextItem).hasClass('no-border') || $(nextItem).hasClass('table-secondary') || $(nextItem).hasClass('add-row') || $(nextItem).hasClass('add-form')) {
                        return false;  // 드래그 이동 취소
                    }
                },
                
                // 드래그 이벤트가 끝난 후에 실행되는 코드
                onEnd: function(evt) {
                    const tbody = evt.from;
                    const tbodyID = tbody.getAttribute('id');
                    const rows = tbody.querySelectorAll('tr:not(:nth-child(1)):not(:nth-child(2)):not(:last-child):not(.add-form)');
                    
                    // 해당 테이블의 '현재 row 순서'와 'Document id값'을 리스트에 저장
                    let rowPKs = [];
                    rows.forEach((row, index) => {
                        const rowID = row.getAttribute('id');
                        rowPK = rowID.split('-')[1];
                        if (rowID) {
                            rowPKs.push({id: rowPK, order: index + 1});
                        }
                    });

                    const jwtToken = localStorage.getItem('jwtToken'); 

                    // ajax 통신으로 Row 드래그 후, 순서를 갱신하여 기록 (페이지 요청 시 그대로 적용)
                    $.ajax({
                        url: `/auth/resume/updateOrder/${tbodyID}`,
                        method: 'POST',
                        data: JSON.stringify(rowPKs),
                        contentType: 'application/json',
                        headers: {
                            'Authorization': jwtToken  
                        },

                        success: function(response) {
                            console.log(response);
                        },
                        error: function(error) {
                            alert(error.responseJSON.data);
                        }
                    });
                }
            });
        
            sortables.push(sortable);
        });
    }
}

// 시작날짜 ~ 종료날짜란이 있는 테이블 (교육관련) 에서 사용하는 함수
function createDateInput() {
    return '<input type="date" class="form-control" style="width: 135px; display: inline-block;">';
}

// 단순히 onclick 속성은 onclick 속성은 HTML 요소가 브라우저에 로드될 때에 해당 요소에 바인딩되며,
// HTML 페이지가 처음 로딩될 때 HTML 소스 코드에 정의된 onclick 속성을 가진 요소만 해당 자바스크립트 함수에 바인딩 됨
// -> 따라서, 추가한 Row에도 여러 이벤트를 적용하기 위해 이벤트 위임 개념과 ready와 .on() 활용 (해당 과정 추가 블로깅)
$(document).ready(function() {

    // + 버튼 클릭 시, 입력 Form 출력
    $(".add-btn, .edu-add-btn").on('click', function() {
        addCnt += 1
        if (addCnt >= 2) {
            return;
        }

        let tableBody = $(this).closest('tbody');
        let isSchoolEdu = $(this).hasClass('edu-add-btn');
        let dateInputs = isSchoolEdu ? createDateInput() + '&nbsp ~ &nbsp' + createDateInput() : createDateInput();

        let newRow = `
            <tr class="add-form toggleMode">
                <td>
                    ${dateInputs}
                </td>
                <td><input type="text" class="form-control"></td>
                <td><input type="text" class="form-control"></td>
                <td><input type="text" class="form-control"></td>
                <td><input type="text" class="form-control"></td>
                <td class="no-border">
                    
                </td>
            </tr>
        `;
        let newBtn = `
        <button class="btn btn-outline-primary submit-btn" onclick="enroll(event)">등록</button>
        <button class="btn btn-outline-danger submit-btn" onclick="add_cancle(event)">취소</button>
        `                
        $(newRow).insertBefore(tableBody.find('.add-row'));
        $(newBtn).appendTo(tableBody.find('.add-cell'));
    });

    // 각 Row 마우스 올리면 css 효과 & 삭제 버튼 show
    $(".sortableTable tbody").on('mouseenter', 'tr:not(.table-secondary, .no-border, .add-row, .add-form)', function() {
        if (isEditMode){
            $(this).addClass('highlighted-row');
            $(this).find('.delete-btn').show();
        }
    }).on('mouseleave', 'tr:not(.table-secondary, .no-border, .add-row)', function() {
        if (isEditMode){
            $(this).removeClass('highlighted-row');
            $(this).find('.delete-btn').hide();
        }
    });

    // x 버튼 클릭 시 삭제 진행
    $(".sortableTable").on('click', 'tbody tr .delete-btn', function() {
        let tr = $(this).closest('tr');
        let trID = tr.attr('id');  // 예시 : schooledu-64ec3392bec6a72ecb9dd3fe
    
        if(trID) {
            const jwtToken = localStorage.getItem('jwtToken'); 
            let parts = trID.split('-');  
            let path = parts[0];      // "schooledu"
            let resumeID = parts[1];  // "64ec3392bec6a72ecb9dd3fe"

            $.ajax({
                url: `/auth/resume/${path}?resumeID=` + resumeID, 
                type: 'DELETE', 
                headers: {
                    'Authorization': jwtToken  
                },
                success: function(response) {
                    console.log(response);

                    // 삭제 후, 남은 row 순서 Ajax 통신으로 갱신
                    const tbody = tr.closest("tbody")[0];
                    const tbodyID = tbody.getAttribute('id');

                    tr.remove();
                    const rows = tbody.querySelectorAll('tr:not(:nth-child(1)):not(:nth-child(2)):not(:last-child):not(.add-form)');
                    
                    let rowPKs = [];
                    rows.forEach((row, index) => {
                        const rowID = row.getAttribute('id');
                        let rowPK = rowID.split('-')[1];
                        if (rowID) {
                            rowPKs.push({id: rowPK, order: index + 1});
                        }
                    });
    
                    $.ajax({
                        url: `/auth/resume/updateOrder/${tbodyID}`,
                        method: 'POST',
                        data: JSON.stringify(rowPKs),
                        contentType: 'application/json',
                        headers: {
                            'Authorization': jwtToken  
                        },
                        success: function(response) {
                            console.log(response);
                        },
                        error: function(error) {
                            alert(error.responseJSON.data);
                        }
                    });

                    
                },
                error: function(error) {
                    alert(error.responseJSON.data);
                }
            });


        }
    });
    
});

// 값 입력 후 등록 버튼 클릭 시 -> 입력 Form & 등록 버튼 & 취소 버튼 없애기
function add_cancle(event) {
    let clickedButton = event.target;
    let tableBody = $(clickedButton).closest('tbody');

    // 마지막에 추가한 행(입력 양식) 제거
    tableBody.find("tr").eq(-2).remove();

    // add-cell 클래스를 가진 셀의 내용(등록/취소 버튼) 비우기
    tableBody.find('.add-cell').empty();
    addCnt = 0;
}

// 등록 버튼 클릭 시, AJAX 통신 후, 등록 값 랜더링
function enroll(event) {
    const jwtToken = localStorage.getItem('jwtToken'); 

    let clickedButton = event.target;
    let tableBody = $(clickedButton).closest('tbody');
    let currentRow = $(clickedButton).closest('tr').prev();
    let values = [];
    currentRow.find("input[type='date']").each(function() {
        values.push($(this).val());
    });
    currentRow.find("input[type='text']").each(function() {
        values.push($(this).val());
    });

    // tableID : schooledu, academyedu, certificate, selfstudy
    let tableID = currentRow.closest('tbody').attr('id'); 

    let payload = {
        values: values 
    };

    $.ajax({
        type: "POST",
        url: `/auth/resume/${tableID}`,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        headers: {
            'Authorization': jwtToken 
        },
        data: JSON.stringify(payload),
        
        success: function(response) {
            console.log(response)
            
            const enrollContent = renderRow(tableID, response.data);
            currentRow.before(enrollContent); 
            currentRow.remove();
            tableBody.find('.add-cell').empty();
            addCnt = 0;
        },
        error: function(error) {
            alert(error.responseJSON.data);
        }
    });

    // 각 table column 개수가 동일하게 되어있어서 List 0 ~ 4인덱스에 저장하고 뿌려도 되지만,
    // 확장성을 위해 랜더링을 테이블마다 구분하여 진행
    function renderRow(tableID, data) {
        if (tableID === 'schooledu') {
          return `
            <tr id="${tableID}-${data.id}"> 
                <td class="drag-handle ">${data.schoolAdmissionDate} ~ ${data.schoolGraduateDate}</td>
                <td>${data.schoolGraduateStatus}</td>
                <td>${data.schoolName}</td>
                <td>${data.schoolMajor}</td>
                <td>${data.schoolCredit}</td>
                <td class="no-border"><span class="delete-btn">&#10006;</span></td>
            </tr>
          `;
        } else if (tableID === 'academyedu') {
            return `
            <tr id="${tableID}-${data.id}"> 
                <td class="drag-handle ">${data.academyEnrollDate} ~ ${data.academyCompletionDate}</td>
                <td>${data.academyCompletionStatus}</td>
                <td>${data.academyName}</td>
                <td>${data.academyCourse}</td>
                <td>${data.academyEtc}</td>
                <td class="no-border"><span class="delete-btn">&#10006;</span></td>
            </tr>
          `;
        } else if (tableID === 'certificate') {
            return `
            <tr id="${tableID}-${data.id}"> 
                <td class="drag-handle ">${data.acquisitionDate}</td>
                <td>${data.certificateType}</td>
                <td>${data.certificateName}</td>
                <td>${data.certificateIssuingAgency}</td>
                <td>${data.certificateStatus}</td>
                <td class="no-border"><span class="delete-btn">&#10006;</span></td>
            </tr>
          `;
        } else if (tableID === 'selfstudy') {
            return `
            <tr id="${tableID}-${data.id}"> 
                <td class="drag-handle ">${data.selfStudyDate}</td>
                <td>${data.selfStudytype}</td>
                <td>${data.selfStudyTheme}</td>
                <td>${data.selfStudyPlatform}</td>
                <td><a href="${data.selfStudyBloggingLink}" style="text-decoration: none">링크</a></td>
                <td class="no-border"><span class="delete-btn">&#10006;</span></td>
            </tr>
          `;
        }
   
      }
}

$(document).ready(function() {
    initEditMode();
});
