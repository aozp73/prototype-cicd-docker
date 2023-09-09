<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    
    <%@ include file="layout/header.jsp" %>


    <div class="py-5" style="background-color: #F9F9F9" id="main-container">

    <c:forEach var="mainIntroduce" items="${mainIntroduceList}" varStatus="status">
        <c:choose>
        
            <c:when test="${status.index % 2 == 0}">
                <div class="container post-container ps-5" style="height:450px" data-index="${status.index + 1}" id="content-${mainIntroduce.id}">
                     <div class="row">
                        <div class="col-5 me-5">
                            <img src="${mainIntroduce.imgURL}" id="postImage-${mainIntroduce.id}" alt="Description of Image" class="img-fluid responsive-image">
                        </div>
                        <div class="col-5 pt-3">
                            <h2 id="postTitle-${mainIntroduce.id}">${mainIntroduce.postTitle}</h2><hr>
                            <div id="postContent-${mainIntroduce.id}">${mainIntroduce.postContent}</div>
                        </div>
                        <div class="col-2">
                        </div>
                    </div>
                    <div class="edit-controls" style="display: none;">
                        <div class="my-3 me-5 d-flex justify-content-end">  
                            <button type="button" class="btn btn-outline-secondary me-2" onclick="updateForm(event, ${mainIntroduce.id}, ${status.index + 1})">수정하기</button>
                            <button type="button" class="btn btn-outline-danger me-5" onclick="deletePost(${mainIntroduce.id})">삭제하기</button>
                        </div>
                    </div>
                </div>
            </c:when>

            <c:otherwise>
                <div class="container post-container pe-5" style="height:450px" data-index="${status.index + 1}" id="content-${mainIntroduce.id}">
                    <div class="row">
                        <div class="col-1">
                        </div>
                        <div class="col-5 pt-3">
                            <h2 id="postTitle-${mainIntroduce.id}">${mainIntroduce.postTitle}</h2><hr>
                            <div id="postContent-${mainIntroduce.id}">${mainIntroduce.postContent}</div>
                        </div>
                        <div class="col-5 ms-5">
                            <img src="${mainIntroduce.imgURL}" id="postImage-${mainIntroduce.id}" alt="Description of Image" class="img-fluid responsive-image">
                        </div>
                    </div>
                    <div class="edit-controls" style="display: none;">
                        <div class="my-3 me-1 d-flex justify-content-end">
                            <button type="button" class="btn btn-outline-secondary me-2" onclick="updateForm(event, ${mainIntroduce.id}, ${status.index + 1})">수정하기</button>
                            <button type="button" class="btn btn-outline-danger me-5" onclick="deletePost(${mainIntroduce.id})">삭제하기</button>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <div id="postBox">
    
    </div>

        <!-- 새 글 쓰기 -->
        <div class="edit-controls" style="display: none;">
            <div class="container mt-5" style="width: 1100px">

                <form id="postForm">
                    <div class="mb-3">
                        <label for="postTitle" class="form-label">제목</label>
                        <input type="text" class="form-control" id="postTitle-new" placeholder="제목을 입력하세요">
                    </div>
                    <div class="mb-3">
                        <label for="postContent" class="form-label">내용</label>
                        <textarea class="form-control" id="postContent-new" rows="7" placeholder="내용을 입력하세요"></textarea>
                    </div>
                    <div class="mb-3">
                        <label for="postImage" class="form-label">사진 업로드</label>
                        <input type="file" id="fileInput-new">
                        <div id="imagePreview" class="mt-3"></div> 
                    </div>
                    <div class="my-3 d-flex justify-content-end">
                        <button type="button" class="btn btn-primary" onclick="addPost()">글 등록하기</button>
                    </div>
                </form>
            </div>
        </div>
         <!-- 새 글 쓰기 -->



<script src="/js/mainpage.js"></script>
    
<%@ include file="layout/footer.jsp" %>