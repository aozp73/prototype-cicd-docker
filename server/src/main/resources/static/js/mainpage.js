// 편집 모드 (toggle), (버튼 보이지 않게 하여 layout 느낌 확인)
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

// 글 등록하기 버튼 클릭 시, Server 통신 후 appendNewPost() 호출하여 랜더링
function addPost() {
    const jwtToken = localStorage.getItem('jwtToken'); 

    let input = document.getElementById('fileInput-new');
    let file = input.files[0];
    if (!file) {
        alert("파일이 선택되지 않았습니다.");
        return;
    }

    let postTitle = $('#postTitle-new').val();
    let postContent = $('#postContent-new').val();
    postContent = postContent.replace(/\n/g, "<br>");

    readFileAsDataURL(input, function(dataURL) {
        let payload = {
            postTitle: postTitle,
            postContent: postContent,
            imageName: file.name,
            contentType: file.type,
            imageData: JSON.stringify(dataURL)
        };

        $.ajax({
            url: '/auth/main',
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            headers: {
                'Authorization': jwtToken 
            },
            data: JSON.stringify(payload),  

            success: function(response, textStatus, jqXHR) {
                console.log(response);
                appendNewPost(response.data);
            },
            error: function(error) {
                alert(error.responseJSON.data);
            }
        });
    });
}  

// 수정하기 버튼 클릭 시, Form 랜더링
function updateForm(event, pk, postIndex) {
    event.preventDefault();
    let postTitle = $("#postTitle-" + pk).text();
    let postContent = $("#postContent-" + pk).html();
    postContent = postContent.replace(/<br>/g, "\n");
    let postImageSrc = $("#postImage-" + pk).attr('src');

    let section = document.getElementById('content-' + pk);
        if (postIndex % 2 !== 0) { 

            section.innerHTML = `
                <div class="row">
                    <div class="col-5 me-5">
                        <div class="mb-2">
                            <img src="${postImageSrc}" alt="Description of Image" class="img-fluid responsive-image" id="postImage-${pk}">
                        </div>
                        <input type="file" id="fileInput-${pk}" onchange="checkImage(${pk})">
                    </div>
                    <div class="col-5 pt-3">
                        <div class="mb-3">
                            <input type="text" class="form-control" id="postTitle-${pk}" placeholder="제목을 입력하세요" value="${postTitle}">
                        </div>
                        <hr>
                        <div class="mb-3">
                            <textarea class="form-control" id="postContent-${pk}" rows="5" placeholder="내용을 입력하세요">${postContent}</textarea>
                        </div>
                    </div>
                    <div class="col-2">
                    </div>
                </div>
                <div class="my-3 me-5 d-flex justify-content-end">  
                    <button class="btn btn-outline-secondary" onclick="updatePost(${pk}, ${postIndex})">수정완료</button>
                </div>
            `;            

        } else {

            section.innerHTML = `
                <div class="row">
                    <div class="col-1">
                    </div>
                    <div class="col-5 pt-3">
                        <div class="mb-3">
                            <input type="text" class="form-control" id="postTitle-${pk}" placeholder="제목을 입력하세요" value="${postTitle}">
                        </div>
                        <hr>
                        <div class="mb-3">
                            <textarea class="form-control" id="postContent-${pk}" rows="5" placeholder="내용을 입력하세요">${postContent}</textarea>
                        </div>
                    </div>
                    <div class="ms-5 col-5">
                        <div class="mb-2">
                            <img src="${postImageSrc}" alt="Description of Image" class="img-fluid responsive-image" id="postImage-${pk}">
                        </div>
                        <input type="file" id="fileInput-${pk}" onchange="checkImage(${pk})">
                    </div>
                </div>
                <div class="my-3 d-flex justify-content-end">  
                    <button class="btn btn-outline-secondary" onclick="updatePost(${pk}, ${postIndex})">수정완료</button>
                </div>
            `;
        }                
}

// 수정완료 버튼 클릭 시, Server 통신 후 랜더링
function updatePost(pk, index) {
    const jwtToken = localStorage.getItem('jwtToken');
    const payload = createPostPayload(pk, index);
    
    $.ajax({
        url: '/auth/main',
        type: 'PUT',
        dataType: 'json',
        contentType: 'application/json',
        headers: {
            'Authorization': jwtToken 
        },
        data: JSON.stringify(payload),  

        success: function(response, textStatus, jqXHR) {
            const db_pk = response.data.id;
            const db_postTitle = response.data.postTitle;
            const db_postContent = response.data.postContent;
            const db_imgURL = response.data.imgURL;

            const container = document.getElementById('content-' + pk);

            if (index % 2 !== 0) {

                container.innerHTML = `
                    <div class="row">
                        <div class="col-5 me-5">
                            <img src="${db_imgURL}" id="postImage-${db_pk}" alt="Description of Image" class="img-fluid responsive-image">
                        </div>
                        <div class="col-5 pt-3">
                            <h2 id="postTitle-${db_pk}">${db_postTitle}</h2><hr>
                            <div id="postContent-${db_pk}">${db_postContent}</div>
                        </div>
                        <div class="col-2">
                        </div>
                    </div>
                    <div class="edit-controls" style="display: block;">
                        <div class="my-3 me-5 d-flex justify-content-end">  
                            <button type="button" class="btn btn-outline-secondary me-2" onclick="updateForm(event, ${db_pk}, ${index})">수정하기</button>
                            <button type="button" class="btn btn-outline-danger me-5" onclick="deletePost(${db_pk})">삭제하기</button>
                        </div>
                    </div>
                
                `

            } else {

                container.innerHTML = `
                    <div class="row">
                        <div class="col-1">
                        </div>
                        <div class="col-5 pt-3">
                            <h2 id="postTitle-${db_pk}">${postTitle}</h2><hr>
                            <div id="postContent-${db_pk}">${postContent}</div>
                        </div>
                        <div class="col-5 ms-5">
                            <img src="${db_imgURL}" id="postImage-${db_pk}" alt="Description of Image" class="img-fluid responsive-image">
                        </div>
                    </div>
                    <div class="edit-controls" style="display: block;">
                        <div class="my-3 me-1 d-flex justify-content-end">
                            <button type="button" class="btn btn-outline-secondary me-2" onclick="updateForm(event, ${db_pk}, ${index})">수정하기</button>
                            <button type="button" class="btn btn-outline-danger me-5" onclick="deletePost(${db_pk})">삭제하기</button>
                        </div>
                    </div>
                `

            }
        },
        error: function(error) {
            alert(error.responseJSON.data);
        }
    });
}

// 삭제하기 버튼 클릭 시 삭제 후, reload
function deletePost(pk) {
    const jwtToken = localStorage.getItem('jwtToken'); 

    $.ajax({
        url: '/auth/main?postPK=' + pk, 
        type: 'DELETE', 
        headers: {
            'Authorization': jwtToken  
        },
        success: function(response) {
            console.log(response);
            location.reload(true);
        },
        error: function(error) {
            alert(error.responseJSON.data);
        }
    });
}

// ======= 내부 함수 =========================================================================

// 등록 시, 등록 된 값 불러오는 함수 (main 페이지에서의 등록은 img태그를 사용하지 않음에 따라 해당 함수 사용)
function readFileAsDataURL(input, callback) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        
        reader.onload = function (e) {
            callback(e.target.result);
        };
        
        reader.readAsDataURL(input.files[0]);
    }
}

// UpdateForm에서 사진 선택 시, 미리보기 제공
function checkImage(pk) {
    const fileInput = document.getElementById('fileInput-'+pk);
    const imageToChange = document.getElementById('postImage-'+pk);
    const file = fileInput.files[0];

    if (file) {
        const reader = new FileReader();
        // 이벤트 큐에 등록. CallBack 함수 작성
        reader.onload = function(e) {
            imageToChange.src = e.target.result;
        }
        // 다 읽고 난 뒤, 위 CallBack 함수 작동
        reader.readAsDataURL(file);
    }
}

// 등록 시, Server 통신 후 랜더링하는 함수
function appendNewPost(postDTO) {
    const index = document.querySelectorAll('.post-container').length + 1;

    let newPostHTML = "";
    if (index % 2 !== 0) {

        newPostHTML = `
            <div class="container post-container ps-5" style="height:450px" data-index="${index}" id="content-${postDTO.id}">
                <div class="row">
                <div class="col-5 me-5">
                    <img src="${postDTO.imgURL}" alt="Description of Image" id="postImage-${postDTO.id}" class="img-fluid responsive-image">
                </div>
                <div class="col-5 pt-3">
                    <h2 id="postTitle-${postDTO.id}">${postDTO.postTitle}</h2><hr>
                    <div id="postContent-${postDTO.id}">${postDTO.postContent}</div>
                </div>
                <div class="col-2"></div>
                </div>
                <div class="edit-controls" style="display: none;">
                <div class="my-3 me-5 d-flex justify-content-end">
                    <button type="button" class="btn btn-outline-secondary me-2" onclick="updateForm(event, ${postDTO.id}, ${index})">수정하기</button>
                    <button type="button" class="btn btn-outline-danger me-5" onclick="deletePost(${postDTO.id})">삭제하기</button>
                </div>
                </div>
            </div>
            `;
            
    } else {

        newPostHTML = `
            <div class="container post-container pe-5" style="height:450px" data-index="${index}" id="content-${postDTO.id}">
                <div class="row">
                <div class="col-1"></div>
                <div class="col-5 pt-3">
                    <h2 id="postTitle-${postDTO.id}">${postDTO.postTitle}</h2><hr>
                    <div id="postContent-${postDTO.id}">${postDTO.postContent}</div>
                </div>
                <div class="col-5 ms-5">
                    <img src="${postDTO.imgURL}" alt="Description of Image" id="postImage-${postDTO.id}" class="img-fluid responsive-image">
                </div>
                </div>
                <div class="edit-controls" style="display: none;">
                <div class="my-3 me-1 d-flex justify-content-end">
                    <button type="button" class="btn btn-outline-secondary me-2" onclick="updateForm(event, ${postDTO.id}, ${index})">수정하기</button>
                    <button type="button" class="btn btn-outline-danger me-5" onclick="deletePost(${postDTO.id})">삭제하기</button>
                </div>
                </div>
            </div>
            `;
    }

    $("#postBox").append(newPostHTML);
    const controls = document.querySelectorAll('.edit-controls');
    controls.forEach(control => {
        if (control.style.display === 'none') {
            control.style.display = 'block';
        } 
    });

    resetForm();
}

// 등록 한 뒤, 등록 Form 초기화
function resetForm() {
    // 제목, 내용 필드 초기화
    $('#postTitle-new').val('');
    $('#postContent-new').val('');
  
    // 파일 입력 및 이미지 미리보기 초기화
    $('#fileInput-new').val('');
    $('#imagePreview').empty();
}

// 수정하기 버튼 클릭 시, payload 생성 함수
function createPostPayload(pk, index) {
    let input = document.getElementById('fileInput-' + pk);
    let file = input.files[0];
    let imageName = '';
    let contentType = '';
    let imgChangeCheck = false;

    let postImageElement = document.getElementById('postImage-' + pk);
    let postImageSrc = postImageElement.src;
    let isBase64Image = postImageSrc.startsWith('data:image/');

    if (isBase64Image) {
        imageName = file.name;
        contentType = file.type;
        imgChangeCheck = true;
    } else {
        postImageSrc = '';
    }

    let postTitle = $(`#postTitle-${pk}`).val();
    let postContent = $(`#postContent-${pk}`).val();
    postContent = postContent.replace(/\n/g, "<br>");

    let payload = {
        id: pk,
        postTitle: postTitle,
        postContent: postContent,
        imageName: imageName,
        contentType: contentType,
        imageData: postImageSrc,
        imgChangeCheck: imgChangeCheck
    };

    return payload;
}