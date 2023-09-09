<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    
    <%@ include file="layout/header.jsp" %>     

    <div id="allSkills" data-skills='${allSkills}'></div>

    <div class="py-5" style="background-color: #F9F9F9; position: relative;" id="main-container">    
        <button type="button" class="btn btn-outline-primary skills-btn-fixed edit-controls" style="display: none;" onclick="getUpdateForm(event)">수정하기</button>
        <div id="backend-container" class="container container-custom-width" style="margin-bottom: 53px;">
            <h3>Back-End</h3>
            <hr>
        </div>

        <div id="frontend-container" class="container container-custom-width" style="margin-bottom: 53px;">
            <h3>Front-End</h3>
            <hr>
        </div>

        <div id="devops-container" class="container container-custom-width" style="margin-bottom: 53px;">
            <h3>Dev-Ops</h3>
            <hr>
        </div>

        <div id="etc-container" class="container container-custom-width" style="margin-bottom: 50px;">
            <h3>ETC</h3>
            <hr>
        </div>

    </div>
       <!-- 수정 Form Modal -->
    <div class="modal fade" id="skillsAddForm" tabindex="-1" role="dialog" aria-labelledby="skillsModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="skillsModalLabel">Skill 정보 수정</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="updateSkillsForm">

                        <div class="mb-5">
                            <h3 class="me-2"style="display:inline">Back-End</h3>
                            <select id="BackEndSelect" onchange="addSkills('BackEnd')" style="width: 20%;">
                                <option selected></option>
                                <option id="Python" value="Python">Python</option>
                                <option id="Java" value="Java">Java</option>
                                <option id="SpringBoot" value="SpringBoot">SpringBoot</option>
                                <option id="SpringSecurity" value="SpringSecurity">SpringSecurity</option>
                                <option id="Mybatis" value="Mybatis">Mybatis</option>
                                <option id="Jpa" value="Jpa">JPA</option>
                                
                                <option id="MySQL" value="MySQL">MySQL</option>
                                <option id="MongoDB" value="MongoDB">MongoDB</option>

                                <option id="JUnit5" value="JUnit5">JUnit5</option>
                                <option id="Postman" value="Postman">Postman</option>
                            </select>
                            <hr>
                            <div id="BackEndSkills" class="mt-2" style="height: 60px;"></div>
                        </div>

                        <div class="mb-5">
                            <h3 class="me-2"style="display:inline">Front-End</h3>
                            <select id="FrontEndSelect" onchange="addSkills('FrontEnd')" style="width: 20%;">
                                <option selected></option>
                                <option id="HTML5" value="HTML5">HTML5</option>
                                <option id="CSS3" value="CSS3">CSS3</option>
                                <option id="JavaScript" value="JavaScript">JavaScript</option>
                                <option id="JQuery" value="JQuery">jQuery</option>
                                <option id="BootStrap" value="BootStrap">BootStrap</option>
                            </select>
                            <hr>
                            <div id="FrontEndSkills" class="mt-2"  style="height: 60px;"></div>
                        </div>

                        <div class="mb-5">
                            <h3 class="me-2"style="display:inline">Dev-Ops</h3>
                            <select id="DevOpsSelect" onchange="addSkills('DevOps')" style="width: 20%;">
                                <option selected></option>
                                <option id="Docker" value="Docker">Docker</option>
                                <option id="Kubernetes" value="Kubernetes">Kubernetes</option>
                                <option id="AmazonEC2" value="AmazonEC2">AmazonEC2</option>
                            </select>
                            <hr>
                            <div id="DevOpsSkills" class="mt-2"  style="height: 60px;"></div>
                        </div>

                        <div class="mb-5">
                            <h3 class="me-2"style="display:inline">ETC</h3>
                            <select id="ETCSelect" onchange="addSkills('ETC')" style="width: 20%;">
                                <option selected></option>
                                <option id="GitHub" value="GitHub">GitHub</option>
                                <option id="Notion" value="Notion">Notion</option>
                                <option id="Jira" value="Jira">Jira</option>
                                <option id="VisualStudioCode" value="VisualStudioCode">VisualStudioCode</option>
                                <option id="Eclipse" value="Eclipse">Eclipse</option>
                                <option id="AWS" value="AWS">AWS</option>
                                <option id="Firebase" value="Firebase">Firebase</option>
                                <option id="AmazonS3" value="AmazonS3">AmazonS3</option>
                            </select>
                            <hr>
                            <div id="ETCSkills" class="mt-2"  style="height: 60px;"></div>
                        </div>

                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                    <button type="button" class="btn btn-primary" onclick="updateSkills()">수정하기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 수정 Form Modal -->


<script src="/js/myskills.js"></script>
    
<%@ include file="layout/footer.jsp" %>