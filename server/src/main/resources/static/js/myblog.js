let modeCnt = 0

function toggleEditMode() {
    modeCnt += 1
    if (modeCnt >= 2) {
        location.reload();
    }

    const controls = document.querySelectorAll('.edit-controls');
    controls.forEach(control => {
        if (control.style.display === 'none') {
            control.style.display = 'block';
        } else {
            control.style.display = 'none';
        }
    });
}


// ====== updateForm / update / add / delete ============================ //

// updateForm
function updateForm(container_number) {
    const section = document.getElementById('container-' + container_number);

    const mainTitle = section.querySelector('#mainTitle-' + container_number + ' h3').innerText;
    const subTitle = section.querySelector('#subTitle-' + container_number + ' h5').innerText;
    let content = section.querySelector('#content-' + container_number + ' p').innerText;
    content = content.replace(/<br>/g, "\n");

    const backgroundImage = section.querySelector('.blog-image-preview-change').style.backgroundImage.slice(5, -2);
    
    section.innerHTML = `
        <div class="mb-4">
            <div class="mb-4">
                <input type="text" class="form-control" id="mainTitle-${container_number}" value="${mainTitle}">
            </div>
        </div>
        <div class="row">
            <div class="col-5">
                <div class="blog-image-preview-change mb-3" id="image-preview-${container_number}" style="height: 261px; background-image: url('${backgroundImage}');" onclick="document.getElementById('fileInput-${container_number}').click();">
                    <input type="file" id="fileInput-${container_number}" style="display: none;" onchange="previewImage(event, ${container_number})">
                </div>
            </div>
            <div class="col-7">
                <form id="postForm-${container_number}">
                    <div class="mb-3">
                        <input type="text" class="form-control" id="subTitle-${container_number}" value="${subTitle}">
                    </div>
                    <div class="mb-3">
                        <textarea class="form-control" id="content-${container_number}" rows="8">${content}</textarea>
                    </div>
                </form>
                <div class="my-3 d-flex justify-content-end">
                    <button type="button" class="btn btn-primary" onclick="updatePost(${container_number})">수정완료</button>
                </div>
            </div>
        </div>
    `;
}

// update
function updatePost(pk) {
    const jwtToken = localStorage.getItem('jwtToken');
    const payload = createPostPayload(pk)

    $.ajax({
        url: '/auth/blog',
        type: 'PUT',
        dataType: 'json',
        contentType: 'application/json',
        headers: {
            'Authorization': jwtToken 
        },
        data: JSON.stringify(payload),  

        success: function(response, textStatus, jqXHR) {

            el = `
                <div class="container" id="container-${response.data.id}" style="padding-left: 150px; padding-right: 150px; margin-top: 20px;">
                    <div class="mb-4" id="mainTitle-${response.data.id}">
                        <h3>${response.data.mainTitle}</h3>
                        <hr>
                    </div>
                    <div class="row">
                        <div class="col-5">
                            <div class="blog-image-preview-change mb-3" style="height: 261px; background-image: url('${response.data.imgURL}');">
                            </div>
                        </div>
                        <div class="col-7">
                            <div class="mb-4" id="subTitle-${response.data.id}">
                                <h5>${response.data.subTitle}</h5>
                            </div>
                            <div class="mb-3" id="content-${response.data.id}">
                                <p>
                                ${response.data.content} 
                                </p>
                            </div>
                        </div>
                    </div>
                    <div style="height: 80px;">
                        <div class="edit-controls" style="display: block; ">
                            <div class="d-flex justify-content-end">
                                <button type="button" class="btn btn-outline-secondary me-2" onclick="updateForm(${response.data.id})">수정하기</button>
                                <button type="button" class="btn btn-outline-danger" onclick="deletePost(${response.data.id})">삭제하기</button>
                            </div>
                        </div>
                    </div>
                </div>
            `

            $("#container-" + response.data.id).replaceWith(el);
        },
        error: function(error) {
            alert(error.responseJSON.data);
        }
    });
}

// add
function addPost() {
    const jwtToken = localStorage.getItem('jwtToken'); 
    
    const imagePreview = document.getElementById('image-preview-new');
    const backgroundImage = imagePreview.style.backgroundImage.slice(5, -2); 
    
    if (!backgroundImage || !backgroundImage.startsWith('data:image')) {
        alert("이미지를 등록해야 합니다.")
        return;
    }

    let fileInput = document.getElementById('fileInput');
    const file = fileInput.files[0]
    const mainTitle = $("#mainTitle-new").val();
    const subTitle = $("#subTitle-new").val();
    let content = $("#content-new").val();
    content = content.replace(/\n/g, "<br>");

    const postData = {
        imageData: backgroundImage,
        imageName: file.name,
        contentType: file.type,

        mainTitle: mainTitle,
        content: content,
        subTitle: subTitle
    };

    $.ajax({
        url: '/auth/blog',
        type: 'POST',
        data: JSON.stringify(postData),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        headers: {
            'Authorization': jwtToken 
        },

        success: function(response, textStatus, jqXHR) {
            const el = `
                <div class="container" id="container-${response.data.id}" style="padding-left: 150px; padding-right: 150px; margin-top: 20px;">
                <div class="mb-4" id="mainTitle-${response.data.id}">
                    <h3>${response.data.mainTitle}</h3>
                    <hr>
                </div>
                <div class="row">
                    <div class="col-5">
                        <div class="blog-image-preview-change mb-3" style="height: 261px; background-image: url('${response.data.imgURL}');">
                        </div>
                    </div>
                    <div class="col-7">
                        <div class="mb-4" id="subTitle-${response.data.id}">
                            <h5>${response.data.subTitle}</h5>
                        </div>
                        <div class="mb-3" id="content-${response.data.id}">
                            <p>
                            ${response.data.content}
                            </p>
                        </div>
                    </div>
                </div>
                <div style="height: 80px;">
                    <div class="edit-controls" style="display: block; ">
                        <div class="d-flex justify-content-end">
                            <button type="button" class="btn btn-outline-secondary me-2" onclick="updateForm(${response.data.id})">수정하기</button>
                            <button type="button" class="btn btn-outline-danger" onclick="deletePost(${response.data.id})">삭제하기</button>
                        </div>
                    </div>
                </div>
            </div>
            `

            $(el).insertBefore('#main-container > :last-child');
            resetFormAndImage()
        },
        error: function(error) {
            alert(error.responseJSON.data);
        }
    });
}

// delete 
function deletePost(pk) {
    const jwtToken = localStorage.getItem('jwtToken'); 

    $.ajax({
        url: '/auth/blog?blogPK=' + pk, 
        type: 'DELETE', 
        headers: {
            'Authorization': jwtToken  
        },
        success: function(response) {
            console.log(response)

            $(`#container-${pk}`).fadeOut(function() {
                $(this).remove();
            });
        },
        error: function(error) {
            alert(error.responseJSON.data);
        }
    })
}

// ====== updateForm / update / add / delete ============================ //


// ====== 이미지 관련 및 기타 함수 ============================ //

// 이미지 미리보기 
function previewImage(event, container_number) {
    const reader = new FileReader();
    const file = event.target.files[0];
    
    reader.onloadend = function() {
        const imagePreview = document.querySelector('#image-preview-' + container_number);
        imagePreview.classList.remove('blog-image-preview'); 
        imagePreview.classList.add('blog-image-preview-change');
        imagePreview.style.backgroundImage = 'url(' + reader.result + ')';
       
        if (container_number === 'new') {
             document.querySelector('.plus-icon').style.display = 'none'; 
        }
    }
    
    if (file) {
        reader.readAsDataURL(file);
    } else {
        resetPreview();
    }
}

function resetPreview() {
    const imagePreview = document.querySelector('#image-preview-new');
    imagePreview.style.backgroundImage = 'none';
    document.querySelector('.plus-icon').style.display = 'block';
}

// 이미지 등록 후 등록 Form 초기화
function resetFormAndImage() {
    document.getElementById('mainTitle-new').value = '';
    document.getElementById('subTitle-new').value = '';
    document.getElementById('content-new').value = '';

    const imagePreview = document.getElementById('image-preview-new');
    imagePreview.style.backgroundImage = 'none';
    imagePreview.classList.remove('blog-image-preview-change');
    imagePreview.classList.add('blog-image-preview'); 
    document.querySelector('.plus-icon').style.display = 'block'

    document.getElementById('fileInput').value = '';
}

// 수정하기 버튼 클릭 시, payload 생성 함수
function createPostPayload(pk) {
    const input = document.getElementById('fileInput-' + pk);
    const file = input.files[0];
    let imageName = '';
    let contentType = '';
    let imgChangeCheck = false;
    
    const imagePreview_div = document.getElementById('image-preview-' + pk);
    let imgSrc = imagePreview_div.style.backgroundImage.slice(5, -2); 
    const isBase64Image = imgSrc.startsWith('data:image/');

    if (isBase64Image) {
        imageName = file.name;
        contentType = file.type;
        imgChangeCheck = true;
    } else {
        imgSrc = '';
    }

    let mainTitle = document.getElementById('mainTitle-' + pk).value;
    let subTitle = document.getElementById('subTitle-' + pk).value;
    let content = document.getElementById('content-' + pk).value;
    content = content.replace(/\n/g, "<br>");

    let payload = {
        id: pk,
        mainTitle: mainTitle,
        subTitle: subTitle,
        content: content,

        imageName: imageName,
        contentType: contentType,
        imageData: imgSrc,
        imgChangeCheck: imgChangeCheck
    };

    return payload;
}

// ====== 이미지 관련 및 기타 함수 ============================ //