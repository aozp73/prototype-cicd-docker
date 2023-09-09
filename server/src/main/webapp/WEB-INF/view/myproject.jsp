<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    
    <%@ include file="layout/header.jsp" %>       

    <div class="py-5" style="background-color: #F9F9F9" id="main-container">
        <button type="button" class="btn btn-outline-primary project-btn-fixed edit-controls" style="display: none;" onclick="getAddForm(event)">등록하기</button>

        <div class="mb-4 container">
            <div class="row">    

            <c:forEach var="project" items="${myProjectList}">
                    <div class="col-lg-3 col-md-6 mb-4" id="project-${project.id}" 
                                                        data-readme-url="${project.readmeUrl}" 
                                                        data-github-url="${project.githubUrl}"
                                                        data-individual-performance-img="${project.individualPerformanceImageNameURL}"
                                                        data-start-date="${project.startDate}"
                                                        data-end-date="${project.endDate}"
                                                        data-role-codes="${project.roleCodes}">

                    <div class="card card-hover-effect" data-card-id="${project.id}" data-members="${project.member}" style="height: 380px; overflow: hidden;">
                        <div class="card-body px-4">
                            <div>
                                <div class="text-center mt-2">
                                    <span id="projectName-${project.id}" class="project-name" style="font-size: 1.6em;">${project.projectName}</span>
                                </div>
                            </div>

                            <div class="mt-2 mb-3 p-2" style="max-height: 33%; height: 243px; overflow: hidden;">
                                <img id="projectImg-${project.id}" src="${project.projectImgURL}" alt="프로젝트 이미지" style="width: 100%; height: 100%; object-fit: fill; ">
                            </div>

                            <div class="card-inner" style="height: 127px;">
                                <div class="ps-3">
                                    <div class="mt-3 mb-2">
                                        <span class="member-icons" style="font-size: 1.3em;"></span>
                                    </div>
                                    <div class="mb-2 ps-1">
                                        &nbsp${project.startDate} ~ ${project.endDate}
                                    </div>
                            <div class="mb-2 ps-1" style="font-size: 15px">
                                <c:forEach var="roleCode" items="${project.roleCodes}" varStatus="status">
                                    <c:if test="${status.first}">&nbsp;</c:if>${roleCode}
                                    <c:if test="${!status.last}"> / </c:if>
                                </c:forEach>
                            </div>
                                </div>
                            </div>

                            <div class="edit-controls" style="position: absolute; right: 10px; bottom: 10px; display: none;">
                                <button class="btn btn-secondary btn-sm" onclick="getUpdateForm(event)">수정</button>
                                <button class="btn btn-danger btn-sm" onclick="deleteProject(event, '${project.id}')">삭제</button>
                            </div>
                            
                        </div>
                    </div>
                </div>
            </c:forEach>

            </div>
        </div>
    </div>

    <!-- 상세보기 Modal -->
    <div class="modal fade" id="projectModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" style ="max-width: 38%;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="projectModalLabel"></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="mb-5 modal-body border-bottom-gray">
                <img id="projectModalImage" src="" alt="Project Image" style="width: 100%; height: auto; cursor: pointer;" onclick="openImageInNewWindow(this.src)">
            </div>
            <div>
                <div class="mb-4">
                    <div class="ms-4 mb-1">
                        <strong>1. 담당기능</strong>
                    </div>
                    <div class="modal-body">
                        <img id="performanceModalImage" src="" alt="Performance Image" style="width: 100%; height: auto; cursor: pointer;" onclick="openImageInNewWindow(this.src)">
                    </div>

                </div>
                <div class="ms-4 mb-4">
                    <div class="mb-1">
                        <strong>2. README (기술블로그, 기술스택, 느낀점 등)</strong>
                    </div>
                    <div class="ms-3">
                        <p id="projectModalREADMELink"></p>
                    </div>
                </div>
                <div class="ms-4 mb-5">
                    <div class="mb-1">
                        <strong>3. Github 소스코드</strong>
                    </div>
                    <div class="ms-3">
                        <p id="projectModalGithubLink"></p>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
        </div>
    </div>
    <!-- 상세보기 Modal -->

    <!-- 등록 Form Modal -->
    <div class="modal fade" id="projectAddForm" tabindex="-1" role="dialog" aria-labelledby="projectModalLabel" aria-hidden="true">
        <div class="modal-dialog" style ="max-width: 38%;">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="projectModalLabel">프로젝트 정보 입력</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="addForm">
                        <div class="form-group mb-3">
                            <label class="mb-2" for="projectName">프로젝트명</label>
                            <input type="text" class="form-control" id="addProjectName" name="projectName" placeholder="프로젝트명을 입력하세요">
                        </div>
                        <div class="form-group mb-3">
                            <label class="mb-2" for="member">인원</label>
                            <input type="number" class="form-control" id="addMember" name="member" placeholder="인원 수를 입력하세요">
                        </div>
                        <div class="form-group mb-3">
                            <label class="mb-2" for="startDate">시작날짜</label>
                            <input type="date" class="form-control" id="addStartDate" name="startDate" pattern="\d{4}-\d{2}-\d{2}" placeholder="YYYY-MM-DD">
                        </div>
                        <div class="form-group mb-3">
                            <label class="mb-2" for="endDate">종료날짜</label>
                            <input type="date" class="form-control" id="addEndDate" name="endDate" pattern="\d{4}-\d{2}-\d{2}" placeholder="YYYY-MM-DD">
                        </div>
                        <div class="form-group mb-3">
                            <label class="mb-2" for="role">참여역할</label>
                            <div>
                                <button type="button" class="btn btn-outline-primary btn-sm role-btn" data-role="BackEnd">BackEnd</button>
                                <button type="button" class="btn btn-outline-primary btn-sm role-btn" data-role="FrontEnd">FrontEnd</button>
                                <button type="button" class="btn btn-outline-primary btn-sm role-btn" data-role="DevOps">DevOps</button>
                            </div>
                            <input type="hidden" id="selectedRoles" name="selectedRoles">
                        </div>
                        <div class="form-group mb-3">
                            <label class="mb-2" for="readmeUrl">README 주소</label>
                            <input type="url" class="form-control" id="addReadmeUrl" name="readmeUrl" placeholder="README 주소를 입력하세요">
                        </div>
                        <div class="form-group mb-4">
                            <label class="mb-2" for="githubUrl">GitHub 주소</label>
                            <input type="url" class="form-control" id="addGithubUrl" name="githubUrl" placeholder="GitHub 주소를 입력하세요">
                        </div>
                        <div class="form-group mb-4">
                            <label class="mb-3" for="image" style="display: block; font-weight: 550;">프로젝트 사진</label>
                            <input type="file" class="form-control-file mb-3" id="postProjectImage" name="postProjectImage" onchange="previewImage(this, 'addImagePreview')">
                            <img id="addImagePreview" src="#" alt="Image Preview" style="width: 100%; height: auto; display: none;">
                        </div>
                        <div class="form-group">
                            <label class="mb-3"  for="featureImage" style="display: block; font-weight: 550;">담당 기능</label>
                            <input type="file" class="form-control-file mb-3" id="postIndividualPerformanceImage" name="postIndividualPerformanceImage" onchange="previewImage(this, 'addFeatureImagePreview')">
                            <img id="addFeatureImagePreview" src="#" alt="Feature Image Preview" style="width: 100%; height: auto; display: none;">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                    <button type="button" class="btn btn-primary" onclick="postProject()">등록하기</button>
                    <input type="hidden" id="updateHiddenId" name="updateHiddenId" value=0>
                </div>
            </div>
        </div>
    </div>
    <!-- 등록 Form Modal -->

    <!-- 수정 Form Modal -->
    <div class="modal fade" id="projectUpdateForm" tabindex="-1" role="dialog" aria-labelledby="projectModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="projectModalLabel">프로젝트 정보 수정</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="updateForm">
                        <div class="form-group mb-3">
                            <label class="mb-2" for="projectName">프로젝트명</label>
                            <input type="text" class="form-control" id="updateProjectName" name="projectName">
                        </div>
                        <div class="form-group mb-3">
                            <label class="mb-2" for="members">참여인원</label>
                            <input type="number" class="form-control" id="updateMembers" name="members">
                        </div>
                        <div class="form-group mb-3">
                            <label class="mb-2" for="startDate">시작날짜</label>
                            <input type="date" class="form-control" id="updateStartDate" name="startDate" pattern="\d{4}-\d{2}-\d{2}" >
                        </div>
                        <div class="form-group mb-3">
                            <label class="mb-2" for="endDate">종료날짜</label>
                            <input type="date" class="form-control" id="updateEndDate" name="endDate" pattern="\d{4}-\d{2}-\d{2}" placeholder="YYYY-MM-DD">
                        </div>
                        <div class="form-group mb-3">
                            <label class="mb-2" for="role">참여역할</label>
                            <div>
                                <button type="button" class="btn btn-outline-primary btn-sm role-btn update-role-btn" data-role="BackEnd">BackEnd</button>
                                <button type="button" class="btn btn-outline-primary btn-sm role-btn update-role-btn" data-role="FrontEnd">FrontEnd</button>
                                <button type="button" class="btn btn-outline-primary btn-sm role-btn update-role-btn" data-role="DevOps">DevOps</button>
                            </div>
                            <input type="hidden" id="selectedRoles" name="selectedRoles">
                        </div>
                        <div class="form-group mb-3">
                            <label class="mb-2" for="readmeUrl">README 주소</label>
                            <input type="url" class="form-control" id="updateReadmeUrl" name="readmeUrl">
                        </div>
                        <div class="form-group mb-4">
                            <label class="mb-2"  for="githubUrl">GitHub 주소</label>
                            <input type="url" class="form-control" id="updateGithubUrl" name="githubUrl">
                        </div>
                        <div class="form-group mb-4">
                            <label class="mb-3" for="image" style="display: block; font-weight: 550;">프로젝트 사진</label>
                            <input type="file" class="form-control-file mb-3" id="updateProjectImg" name="image" onchange="previewImage(this, 'updateImagePreview')">
                            <img id="updateImagePreview" src="#" alt="Image Preview" style="width: 100%; height: auto; display: none;">
                        </div>
                        <div class="form-group">
                            <label class="mb-3"  for="featureImage" style="display: block; font-weight: 550;">담당 기능</label>
                            <input type="file" class="form-control-file mb-3" id="updateIndividualPerformanceImg" name="featureImage" onchange="previewImage(this, 'updateFeatureImagePreview')">
                            <img id="updateFeatureImagePreview" src="#" alt="Feature Image Preview" style="width: 100%; height: auto; display: none;">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                    <button type="button" class="btn btn-primary" onclick="updateProject()">수정하기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 수정 Form Modal -->


<script src="/js/myproject.js"></script>
    
<%@ include file="layout/footer.jsp" %>