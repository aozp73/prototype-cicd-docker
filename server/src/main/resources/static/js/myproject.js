let initialSelectedRoles = [];

function toggleEditMode() {
    const controls = document.querySelectorAll('.edit-controls');
    controls.forEach(control => {
        if (control.style.display === 'none') {
            control.style.display = 'block';
        } else {
            control.style.display = 'none';
        }
    });
}

// 프로젝트 상세정보 보기
$('#main-container .row').on('click', '.card', function() {
    const cardId = $(this).data('card-id');
    showModalWithDetails(cardId);
});

function showModalWithDetails(cardId) {
    const cardElement = $(`#project-${cardId} .card`);
    const parentElement = cardElement.closest(`#project-${cardId}`);
    
    // 상세보기 필요한 정보 가져오기
    const title = parentElement.find('span.project-name').text();
    const project_ImageSrc = parentElement.find('img').attr('src');
    const performance_ImageSrc = parentElement.data('individual-performance-img');
    const readmeUrl = parentElement.data('readme-url');
    const githubUrl = parentElement.data('github-url'); 
    
    // 모달에 정보 랜더링
    document.getElementById("projectModalLabel").innerText = title;
    document.getElementById("projectModalImage").setAttribute("src", project_ImageSrc);
    document.getElementById("performanceModalImage").setAttribute("src", performance_ImageSrc);
    document.getElementById("projectModalREADMELink").innerHTML = `<a href="${readmeUrl}" target="_blank" style="text-decoration: none;">README 링크</a>`;
    document.getElementById("projectModalGithubLink").innerHTML = `<a href="${githubUrl}" target="_blank" style="text-decoration: none;">Github 링크</a>`;

    // 모달을 띄우기
    var modal = new bootstrap.Modal(document.getElementById("projectModal"));
    modal.show();
}

// 등록하기 Modal Form
function getAddForm(){
    var modal = new bootstrap.Modal(document.getElementById("projectAddForm"));
    modal.show(); 
}

// 수정하기 Modal Form
function getUpdateForm(event) {
    event.stopPropagation();
    initialSelectedRoles = []; // 서버에서 role이 변경되었는지 확인 후, 추가적인 DB 통신할지 결정하는데 활용

    const buttonClicked = event.target;
    const cardElement = buttonClicked.closest('.card');
    const cardId = cardElement.getAttribute('data-card-id');
    const parentDiv = cardElement.parentElement;
    const individualPerformanceURL = parentDiv.getAttribute('data-individual-performance-img');
    const projectImgURL = document.getElementById(`projectImg-${cardId}`).src;

    // Card 정보 가져와서 Modal에 세팅하기
    setModalFieldsFromCard(cardElement, parentDiv, cardId);
    // 역할 버튼 설정
    setRoleButtons(parentDiv);
    // 이미지 미리보기
    setImagePreviews(projectImgURL, individualPerformanceURL)
    
    // 모달창 생성
    var modal = new bootstrap.Modal(document.getElementById("projectUpdateForm"));
    modal.show(); 
}


// POST (서버 통신)
function postProject() {
    const jwtToken = localStorage.getItem('jwtToken'); 

    const {
        formData,
        projectImgBase64,
        individualPerformanceBase64,
        projectImageName,
        projectImageType,
        individualPerformanceImageName,
        individualPerformanceImageType
    } = getImageFormData();

    // 이미지 파일인지 체크 후, formData에 Base64 / 이미지 이름 / 이미지 타입 추가 저장
    if (projectImgBase64.startsWith('data:image/') && individualPerformanceBase64.startsWith('data:image/')) {
        formData.append("projectImgBase64", projectImgBase64);
        formData.append("individualPerformanceBase64", individualPerformanceBase64);
        formData.append("projectImageName", projectImageName);
        formData.append("projectImageType", projectImageType);
        formData.append("individualPerformanceImageName", individualPerformanceImageName);
        formData.append("individualPerformanceImageType", individualPerformanceImageType);
    } else {
        alert("이미지 파일을 등록해야 합니다.")
        return
    }

    $.ajax({
        url: "/auth/myproject",
        type: "POST",
        data: formData,
        contentType: false, // 서버에 데이터의 contentType을 자동으로 설정하도록 함
        processData: false, // jQuery가 data를 QueryString으로 처리하지 않도록 설정
        headers: {
            'Authorization': jwtToken  
        },
        success: function(response) {
            console.log(response);
            resetModalForm();
            $('#projectAddForm').modal('hide');

            const newProjectHTML = createProjectHTML(response.data);
            $('#main-container .row').append(newProjectHTML);

            const newCard = $(`#project-${response.data.id} .card`);
            const membersCount = newCard.data('members');
            const iconsForMembers = getMembersIcons(membersCount);
            newCard.find('.member-icons').html("&nbsp;" + iconsForMembers);
        },
        error: function(error) {
            alert(error.responseJSON.data);
        }
    });
}

// PUT (서버 통신)
function updateProject() {
    const jwtToken = localStorage.getItem('jwtToken'); 
    let payload = createPayload();
    let hasRolesChanged = false;

    if (!arraysEqual(initialSelectedRoles, payload.selectedRoles)) {
        hasRolesChanged = true;
    }
    payload["hasRolesChanged"] = hasRolesChanged;

    $.ajax({
        url: '/auth/myproject',
        type: 'PUT',
        dataType: 'json',
        contentType: 'application/json',
        headers: {
            'Authorization': jwtToken 
        },
        data: JSON.stringify(payload),

        success: function(response, textStatus, jqXHR) {
            console.log(response);
            resetModalForm();
            $('#projectUpdateForm').modal('hide');

            const newProjectHTML = createProjectHTML(response.data);
            $(`#project-${response.data.id}`).replaceWith(newProjectHTML);

            const newCard = $(`#project-${response.data.id} .card`);
            const membersCount = newCard.data('members');
            const iconsForMembers = getMembersIcons(membersCount);
            newCard.find('.member-icons').html("&nbsp;" + iconsForMembers);
        },
        error: function(error) {
            alert(error.responseJSON.data);
        }
    });
}

// DELETE (서버 통신)
function deleteProject(event, pk) {
    event.stopPropagation();

    const jwtToken = localStorage.getItem('jwtToken'); 

    $.ajax({
        url: '/auth/myproject?projectPK=' + pk, 
        type: 'DELETE', 
        headers: {
            'Authorization': jwtToken  
        },
        success: function(response) {
            console.log(response);

            $(`#project-${pk}`).fadeOut(function() {
                $(this).remove();
            });
        },
        error: function(error) {
            alert(error.responseJSON.data);
        }
    })
}



// ======= 내부 함수 =========================================================================

// 1. 등록하기 관련 내부 함수
// 수정하기, 등록하기 - modal창의 input 태그에서 onchage() 이벤트에 활용 (이미지 미리보기)
function previewImage(input, previewElementId) {
    const file = input.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const previewElement = document.getElementById(previewElementId);
            previewElement.src = e.target.result;
            previewElement.style.display = "block";
        }
        reader.readAsDataURL(file);
    }
}

// 등록하기 - new FormData에서 hidden input 태그 가져갈 때 사용
function updateSelectedRoles() { 
    var selectedRoles = [];
    $('.role-btn.btn-primary').each(function() {
        selectedRoles.push($(this).data('role'));
    });
    // 버튼 클릭 시, 버튼 아래 hidden input태그에 클릭 값 세팅
    $('#selectedRoles').val(selectedRoles.join(',')); 
}

// 등록하기, 수정하기 - 서버 통신 성공 후 -> 랜더링에 활용
function createProjectHTML(responseData) {
    let selectedRoles = responseData.selectedRoles;
    let roleString = selectedRoles ? selectedRoles.map((role, index, array) =>
        `${index === 0 ? '&nbsp;' : ''}${role}${index < array.length - 1 ? ' / ' : ''}`
    ).join('') : '';

    return `<div class="col-lg-3 col-md-6 mb-4" id="project-${responseData.id}" 
                    data-readme-url="${responseData.readmeUrl}" 
                    data-github-url="${responseData.githubUrl}"
                    data-individual-performance-img="${responseData.individualPerformanceImageNameURL}"
                    data-start-date="${responseData.startDate}"
                    data-end-date="${responseData.endDate}"
                    data-role-codes="${responseData.selectedRoles}">
                    <div class="card card-hover-effect" data-card-id="${responseData.id}" data-members="${responseData.member}" style="height: 380px; overflow: hidden;">
                        <div class="card-body px-4">
                            <div class="text-center mt-2">
                                <span id="projectName-${responseData.id}" class="project-name" style="font-size: 1.6em;">${responseData.projectName}</span>
                            </div>
                            <div class="mt-2 mb-3 p-2" style="max-height: 33%; height: 243px; overflow: hidden;">
                                <img id="projectImg-${responseData.id}" src="${responseData.projectImgURL}" alt="프로젝트 이미지" style="width: 100%; height: 100%; object-fit: fill; ">
                            </div>
                            <div class="card-inner" style="height: 127px;">
                                <div class="ps-3">
                                    <div class="mt-3 mb-2">
                                        <span class="member-icons" style="font-size: 1.3em;"></span>
                                    </div>
                                    <div class="mb-2 ps-1">
                                        &nbsp${responseData.startDate} ~ ${responseData.endDate}
                                    </div>
                                    <div class="mb-2 ps-1" style="font-size: 15px">
                                    ${roleString}
                                    </div>
                                </div>
                            </div>
                            <div class="edit-controls" style="position: absolute; right: 10px; bottom: 10px; display: block;">
                                <button class="btn btn-secondary btn-sm" onclick="getUpdateForm(event)">수정</button>
                                <button class="btn btn-danger btn-sm" onclick="deleteProject(event, '${responseData.id}')">삭제</button>
                            </div>
                        </div>
                    </div>
                </div>`;
}

// 등록하기 - 값 가져와서 반환
function getImageFormData() {
    // 입력 값 (프로젝트명 / 인원 / 시작날짜 / 종료날짜 / 참여역할 / README 주소 / GitHub주소)
    let formData = new FormData(document.getElementById('addForm'));
    formData.delete("postProjectImage");
    formData.delete("postIndividualPerformanceImage");

    // 프로젝트 이미지, 개인 수행 각 Base64 문자열 저장 
    let projectImgElement = document.getElementById('addImagePreview');
    let projectImgBase64 = projectImgElement.src;
    let featureImgElement = document.getElementById('addFeatureImagePreview');
    let individualPerformanceBase64 = featureImgElement.src;

    // 이미지 파일 이름과 타입 저장 (S3 전송에 사용)
    let projectImage_Input = document.getElementById('postProjectImage');
    let individualPerformanceImage_Input = document.getElementById('postIndividualPerformanceImage');

    let projectImageName = '';
    let projectImageType = '';
    let individualPerformanceImageName = '';
    let individualPerformanceImageType = '';

    if (projectImage_Input.files && projectImage_Input.files[0]) {
        projectImageName = projectImage_Input.files[0].name;
        projectImageType = projectImage_Input.files[0].type;
    }
    if (individualPerformanceImage_Input.files && individualPerformanceImage_Input.files[0]) {
        individualPerformanceImageName = individualPerformanceImage_Input.files[0].name;
        individualPerformanceImageType = individualPerformanceImage_Input.files[0].type;
    }

    return {
        formData,
        projectImgBase64,
        individualPerformanceBase64,
        projectImageName,
        projectImageType,
        individualPerformanceImageName,
        individualPerformanceImageType
    };
}

// 2. 수정 관련 내부 함수
// 수정하기 -데이터 가져와서 Payload 생성 
function createPayload() {
    const projectId = document.getElementById('updateHiddenId').value
    const projectName = document.getElementById('updateProjectName').value;
    const member = document.getElementById('updateMembers').value;
    const startDate = document.getElementById('updateStartDate').value;
    const endDate = document.getElementById('updateEndDate').value;
    const readmeUrl = document.getElementById('updateReadmeUrl').value;
    const githubUrl = document.getElementById('updateGithubUrl').value;
    const roleButtons = document.querySelectorAll('.btn-primary.update-role-btn');
    let selectedRoles = [];
    roleButtons.forEach(button => {
        selectedRoles.push(button.getAttribute('data-role'));
    });

    let projectImageDetails = getImageDetails('updateProjectImg', 'updateImagePreview');
    let featureImageDetails = getImageDetails('updateIndividualPerformanceImg', 'updateFeatureImagePreview');

    let payload = {
        projectId: projectId,
        projectName: projectName,
        member: member,
        startDate: startDate,
        endDate: endDate,
        readmeUrl: readmeUrl,
        githubUrl: githubUrl,
        selectedRoles: selectedRoles,

        projectImageDetails : projectImageDetails,
        featureImageDetails : featureImageDetails
    };
    return payload;
}

// 수정하기 - 해당 이미지가 수정되었는지 여부를 확인 후 관련 리스트 값 세팅 
// (이미지가 수정되지 않았다면 S3 Upload 하지 않음)
function getImageDetails(inputId, imageId) {
    let input = document.getElementById(inputId);
    let file = input.files[0];
    let imageName = '';
    let contentType = '';
    let imgChangeCheck = false;

    if(file && !file.type.startsWith('image/')){
        alert("이미지 파일을 등록해야 합니다.")
        return
    }

    let imageElement = document.getElementById(imageId);
    let imageSrc = imageElement.src;

    let isBase64Image = imageSrc.startsWith('data:image/');

    if (isBase64Image) {
        imageName = file.name;
        contentType = file.type;
        imgChangeCheck = true;
    } else {
        imageSrc = '';
    }

    return {
        imageSrc,
        imageName,
        contentType,
        imgChangeCheck
    };
}

// 수정하기 - role 값이 달라졌는지 체크
function arraysEqual(a, b) {
    return a.length === b.length && a.every((val, index) => val === b[index]);
}

// 수정하기 Form - 모달 창 값 세팅
function setModalFieldsFromCard(cardElement, parentDiv, cardId) {
    // 해당 카드 정보 가져오기
    const projectName = document.getElementById(`projectName-${cardId}`).textContent; 
    const members = cardElement.getAttribute('data-members');
    const readmeUrl = parentDiv.getAttribute('data-readme-url');
    const githubUrl = parentDiv.getAttribute('data-github-url');
    const startDate = parentDiv.getAttribute('data-start-date');
    const endDate = parentDiv.getAttribute('data-end-date');
    
    // 가져온 정보를 모달의 input 필드에 저장
    document.getElementById('updateProjectName').value = projectName;
    document.getElementById('updateMembers').value = members;
    document.getElementById('updateStartDate').value = startDate;
    document.getElementById('updateEndDate').value = endDate;
    document.getElementById('updateReadmeUrl').value = readmeUrl;
    document.getElementById('updateGithubUrl').value = githubUrl;
    document.getElementById('updateHiddenId').value = cardId;
}

// 수정하기 Form - roles 버튼 세팅 
function setRoleButtons(parentDiv) {
    // roles (BackEnd, FrontEnd DevOps) 버튼 활성화
    const roleCodes = parentDiv.getAttribute('data-role-codes');
    // 해당 게시물 역할 정보 가져오기 + [BackEnd, FrontEnd]에서 '[', ']'제거
    const roleArray = roleCodes.replace(/[\[\]]/g, '').split(',').map(role => role.trim()); 
    const roleButtons = document.querySelectorAll('.update-role-btn');
    // 수정하기 Form에서 버튼 3개 태그를 불러와, 활성화 시작
    roleButtons.forEach(button => {
        const role = button.getAttribute('data-role');

        if (roleArray.includes(role)) {
            button.classList.remove('btn-outline-primary');
            button.classList.add('btn-primary');
            // 해당 Form 값 입력 후, updateProject() 시 변경여부 확인 (DB 통신 추가로 할 지 결정)
            initialSelectedRoles.push(role); 
        } else {
            button.classList.remove('btn-primary');
            button.classList.add('btn-outline-primary');
        }
    });
}

// 수정하기 Form - 이미지 미리보기 설정 함수
function setImagePreviews(projectImgURL, individualPerformanceURL) {
    const imgPreview = document.getElementById('updateImagePreview');
    imgPreview.src = projectImgURL;
    imgPreview.style.display = 'block';

    const featureImagePreview = document.getElementById('updateFeatureImagePreview');
    featureImagePreview.src = individualPerformanceURL;
    featureImagePreview.style.display = 'block';
}


// 3. 기타 함수
// 참여역할 버튼 (BackEnd FrontEnd DevOps 버튼 클릭시 toggle 기능)
$(document).ready(function() {
    $('.role-btn').click(function() {
        var $this = $(this);
        if ($this.hasClass('btn-outline-primary')) {
            $this.removeClass('btn-outline-primary').addClass('btn-primary');
        } else {
            $this.removeClass('btn-primary').addClass('btn-outline-primary');
        }
        updateSelectedRoles();
    });
});

// 모달 초기화 
$('#projectAddForm').on('hidden.bs.modal', function () {
    resetModalForm();
});
$('#projectUpdateForm').on('hidden.bs.modal', function () {
    resetModalForm();
});

function resetModalForm() {
    // 모든 input 필드 초기화
    $('#projectAddForm').find('input').val('');
    $('#projectUpdateForm').find('input').val('');

    // 이미지 프리뷰 초기화
    $('#addImagePreview').attr('src', '#').hide();
    $('#updateImagePreview').attr('src', '#').hide();
    $('#addFeatureImagePreview').attr('src', '#').hide();
    $('#updateFeatureImagePreview').attr('src', '#').hide();

    // 참여역할 버튼 초기화
    $('.role-btn.btn-primary').removeClass('btn-primary').addClass('btn-outline-primary');
}

// 이모지 (숫자 값 -> 이모지로 세팅)
function getMembersIcons(membersCount) {
    const icons = ['🧔', '👦', '🧑', '👩', '👱'];
    return icons.slice(0, membersCount).join('');
}

$(document).ready(function() {
    $('.card').each(function() {
        const card = $(this);
        const membersCount = card.data('members');
        const iconsForMembers = getMembersIcons(membersCount);

        card.find('.member-icons').html("&nbsp;" + iconsForMembers);
    });
});

// 상세정보 모달창 url, 이미지 새창 열기 
function openImageInNewWindow(src) {
    window.open(src, '_blank');
}
