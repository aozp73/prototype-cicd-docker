<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    
    <%@ include file="layout/header.jsp" %>     

        <div class="py-5" style="background-color: #F9F9F9" id="main-container">
        
            <div class="mt-3 container d-flex align-items-center mb-4" style="position: relative;">
                <div id="blogURL" style="position: absolute; right: 162px;">
                    <span style="font-size: 17px;">블로그 주소 &nbsp;<a style="text-decoration: none; font-size: 16px;" href="https://blog.naver.com/aozp73" target="_blank">https://blog.naver.com/aozp73</a> </span>
                </div>
            </div>
            <br>

            <c:forEach var="blog" items="${myBlogList}">
                <div class="container" id="container-${blog.id}" style="padding-left: 150px; padding-right: 150px; margin-top: 20px;">
                    <div class="mb-4" id="mainTitle-${blog.id}">
                        <h3>${blog.mainTitle}</h3>
                        <hr>
                    </div>
                    <div class="row">
                        <div class="col-5">
                            <div class="blog-image-preview-change mb-3" style="height: 261px; background-image: url('${blog.imgURL}');">
                            </div>
                        </div>
                        <div class="col-7">
                            <div class="mb-4" id="subTitle-${blog.id}">
                                <h5>${blog.subTitle}</h5>
                            </div>
                            <div class="mb-3" id="content-${blog.id}">
                                <p>
                                ${blog.content} 
                                </p>
                            </div>
                        </div>
                    </div>
                    <div style="height: 80px;">
                        <div class="edit-controls" style="display: none; ">
                            <div class="d-flex justify-content-end">
                                <button type="button" class="btn btn-outline-secondary me-2" onclick="updateForm(${blog.id})">수정하기</button>
                                <button type="button" class="btn btn-outline-danger" onclick="deletePost(${blog.id})">삭제하기</button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
 
            <%-- 등록하기 Form --%>
            <div class="edit-controls container" style="margin-top: 120px; padding-left: 150px; padding-right: 150px; display: none;">

                <div class="mb-4">
                    <div class="ms-2 mb-4">
                        <h2>새 글쓰기</h2>
                    </div>
                    <div>
                        <input type="text" class="form-control" id="mainTitle-new" placeholder="제목을 입력하세요">
                    </div>
                </div>
                <div class="row">

                    <div class="col-5">
                        <div class="blog-image-preview mb-3" id="image-preview-new" style="height: 261px;" onclick="document.getElementById('fileInput').click();">
                            <input type="file" id="fileInput" style="display: none;" onchange="previewImage(event, 'new')">
                            <div class="plus-icon">+</div>
                        </div>
                    </div>

                    <div class="col-7">
                        <form id="postForm">
                            <div class="mb-3">
                                <input type="text" class="form-control" id="subTitle-new" placeholder="소제목을 입력하세요">
                            </div>
                            <div class="mb-3">
                                <textarea class="form-control" id="content-new" rows="8" placeholder="내용을 입력하세요"></textarea>
                            </div>
                        </form>
                        <div class="my-3 d-flex justify-content-end">
                            <button type="button" class="btn btn-primary" onclick="addPost()">글 등록하기</button>
                        </div>
                    </div>

                </div>
            </div>

            
        </div>
        

<script src="/js/myblog.js"></script>
    
<%@ include file="layout/footer.jsp" %>