<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    
    <%@ include file="layout/header.jsp" %>    

    <div class="py-5" style="background-color: #F9F9F9" id="main-container">

        <%-- 학교 교육 이력 --%>
        <div class="ps-5 pe-1 container">
            <table class="sortableTable" style="width: 80%; margin-left: auto; margin-right: auto;">
                <tbody id="schooledu">
                    <tr class="no-border">
                        <th style="width: 25%"><h4>교육사항</h4></th>
                    </tr>
                    <tr class="table-secondary">
                        <td style="width: 33%">재학기간</td>
                        <td style="width: 13%">졸업여부</td>
                        <td style="width: 19%">학교명</td>
                        <td style="width: 17%">전공</td>
                        <td style="width: 13%">학점</td>
                        <td class="no-border non-secondary" style="width: 4.2%"></td>
                    </tr>
                    <c:forEach var="schoolEdu" items="${resumeAllDTO.resumeSchoolEdus}">
                        <tr id="schooledu-${schoolEdu.id}">
                            <td class="drag-handle">${schoolEdu.schoolAdmissionDate} ~ ${schoolEdu.schoolGraduateDate}</td>
                            <td>${schoolEdu.schoolGraduateStatus}</td>
                            <td>${schoolEdu.schoolName}</td>
                            <td>${schoolEdu.schoolMajor}</td>
                            <td>${schoolEdu.schoolCredit}</td>
                            <td class="no-border"><span class="delete-btn">&#10006;</span></td>
                        </tr>
                    </c:forEach>

                    <tr class="add-row toggleMode" style="display: none;">
                        <td class="no-border add-cell" colspan="5" style="text-align: right;"></td>
                        <td class="no-border"><span class="edu-add-btn">➕</span></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br>


        <%-- 학원 수강 이력 --%>
        <div class="ps-5 pe-1 container">
            <table class="sortableTable" style="width: 80%; margin-left: auto; margin-right: auto;">
                <tbody id="academyedu">
                    <tr class="no-border">
                        <th style="width: 25%"><h4></h4></th>
                    </tr>
                    <tr class="table-secondary">
                        <td style="width: 33%">수강기간</td>
                        <td style="width: 13%">수료여부</td>
                        <td style="width: 19%">교육 장소</td>
                        <td style="width: 17%">과정명</td>
                        <td style="width: 13%">기타</td>
                        <td class="no-border non-secondary" style="width: 4.2%"></td>
                    </tr>
                    
                    <c:forEach var="academyEdu" items="${resumeAllDTO.resumeAcademyEdus}">
                        <tr id="academyedu-${academyEdu.id}">
                            <td class="drag-handle">${academyEdu.academyEnrollDate} ~ ${academyEdu.academyCompletionDate}</td>
                            <td>${academyEdu.academyCompletionStatus}</td>
                            <td>${academyEdu.academyName}</td>
                            <td>${academyEdu.academyCourse}</td>
                            <td>${academyEdu.academyEtc}</td>
                            <td class="no-border"><span class="delete-btn">&#10006;</span></td>
                        </tr>
                    </c:forEach>
                    
                    <tr class="add-row toggleMode" style="display: none;">
                        <td class="no-border add-cell" colspan="5" style="text-align: right;"></td>
                        <td class="no-border"><span class="edu-add-btn">➕</span></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br><br><br>


        <%-- 자격증 현황 --%>
        <div class="ps-5 pe-1 container">
            <table class="sortableTable" style="width: 80%; margin-left: auto; margin-right: auto;">
                <tbody id="certificate">
                    <tr class="no-border">
                        <th style="width: 25%"><h4>자격증 / 어학</h4></th>
                    </tr>
                    <tr class="table-secondary">
                        <td style="width: 25%">취득일</td>
                        <td style="width: 13%">종류</td>
                        <td style="width: 27%">자격증명</td>
                        <td style="width: 17%">발급기관</td>
                        <td style="width: 13%">합격여부</td>
                        <td class="no-border non-secondary" style="width: 4.2%"></td>
                    </tr>

                    <c:forEach var="certificate" items="${resumeAllDTO.resumeCertificates}">
                        <tr id="certificate-${certificate.id}">
                            <td class="drag-handle">${certificate.acquisitionDate}</td>
                            <td>${certificate.certificateType}</td>
                            <td>${certificate.certificateName}</td>
                            <td>${certificate.certificateIssuingAgency}</td>
                            <td>${certificate.certificateStatus}</td>
                            <td class="no-border"><span class="delete-btn">&#10006;</span></td>
                        </tr>
                    </c:forEach>

                    <tr class="add-row toggleMode" style="display: none;">
                        <td class="no-border add-cell" colspan="5" style="text-align: right;"></td>
                        <td class="no-border"><span class="add-btn">➕</span></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br><br><br>


        <%-- 자기주도적 학습 내용 --%>
        <div class="ps-5 pe-1 container">
            <table class="sortableTable" style="width: 80%; margin-left: auto; margin-right: auto;">
                <tbody id="selfstudy">
                    <tr class="no-border">
                        <th style="width: 25%"><h4>추가 학습</h4></th>
                    </tr>
                    <tr class="table-secondary">
                        <td style="width: 25%">수강일</td>
                        <td style="width: 13%">종류</td>
                        <td style="width: 27%">강의명</td>
                        <td style="width: 17%">수강장소</td>
                        <td style="width: 13%">블로깅</td>
                        <td class="no-border non-secondary" style="width: 4.2%"></td>
                    </tr>

                    <c:forEach var="selfStudy" items="${resumeAllDTO.resumeSelfStudies}">
                        <tr id="selfstudy-${selfStudy.id}">
                            <td class="drag-handle">${selfStudy.selfStudyDate}</td>
                            <td>${selfStudy.selfStudytype}</td>
                            <td>${selfStudy.selfStudyTheme}</td>
                            <td>${selfStudy.selfStudyPlatform}</td>
                            <td><a href="${selfStudy.selfStudyBloggingLink}" style="text-decoration: none">링크</a></td>
                            <td class="no-border"><span class="delete-btn">&#10006;</span></td>
                        </tr>
                    </c:forEach>

                    <tr class="add-row toggleMode" style="display: none;">
                        <td class="no-border add-cell" colspan="5" style="text-align: right;"></td>
                        <td class="no-border"><span class="add-btn">➕</span></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br><br><br>
    </div>

<script src="/js/resume.js"></script>
    
<%@ include file="layout/footer.jsp" %>