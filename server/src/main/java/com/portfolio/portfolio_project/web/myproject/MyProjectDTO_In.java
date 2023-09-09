package com.portfolio.portfolio_project.web.myproject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.portfolio.portfolio_project.domain.jpa.myproject.my_project.MyProject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MyProjectDTO_In {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostDTO {
        // 프로젝트명, 인원, 날짜 정보
        private String projectName;
        private Integer member;
        private String startDate;
        private String endDate;

        // 수행 역할 정보 (BackEnd,FrontEnd,DevOps 형식)
        private String selectedRoles;

        // readme, github 주소
        private String readmeUrl;
        private String githubUrl;

        // 프로젝트, 개인수행 이미지 정보
        private String projectImgBase64;
        private String individualPerformanceBase64;
        private String projectImageName;
        private String projectImageType;
        private String individualPerformanceImageName;
        private String individualPerformanceImageType;

        public MyProject toEntity(List<String> projectImg_nameAndUrl, List<String> individualPerformanceImg_nameAndUrl) {

            return MyProject.builder()
                    .projectName(this.projectName)
                    .member(this.member)
                    .startDate(LocalDate.parse(this.startDate))
                    .endDate(LocalDate.parse(this.endDate))
                    .readmeUrl(this.readmeUrl)
                    .githubUrl(this.githubUrl)
                    .projectImgName(projectImg_nameAndUrl.get(0))
                    .projectImgUrl(projectImg_nameAndUrl.get(1))
                    .individualPerformanceImgName(individualPerformanceImg_nameAndUrl.get(0))
                    .individualPerformanceImgUrl(individualPerformanceImg_nameAndUrl.get(1)) 
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PutDTO {
        private Long projectId;
        private String projectName;
        private Integer member; 
        private String startDate;
        private String endDate;
        private String readmeUrl;
        private String githubUrl;

        private List<String> selectedRoles; 
        private Boolean hasRolesChanged;

        private ImageDetails projectImageDetails;
        private ImageDetails featureImageDetails;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ImageDetails {
            private String imageSrc;
            private String imageName;
            private String contentType;
            private Boolean imgChangeCheck;
        }

        public MyProject toEntity(MyProject myProjectPS, List<String> projectImg_nameAndUrl, List<String> individualPerformanceImg_nameAndUrl) {
    
            // 기본 정보 업데이트
            myProjectPS.setProjectName(this.projectName);
            myProjectPS.setMember(this.member);
            myProjectPS.setStartDate(LocalDate.parse(this.startDate));
            myProjectPS.setEndDate(LocalDate.parse(this.endDate));
            myProjectPS.setReadmeUrl(this.readmeUrl);
            myProjectPS.setGithubUrl(this.githubUrl);
            myProjectPS.setUpdatedAt(LocalDateTime.now());
        
            // 이미지 변경 체크
            if (projectImageDetails.getImgChangeCheck()) {
                myProjectPS.setProjectImgName(projectImg_nameAndUrl.get(0));
                myProjectPS.setProjectImgUrl(projectImg_nameAndUrl.get(1));
            }
            
            if (featureImageDetails.getImgChangeCheck()) {
                myProjectPS.setIndividualPerformanceImgName(individualPerformanceImg_nameAndUrl.get(0));
                myProjectPS.setIndividualPerformanceImgUrl(individualPerformanceImg_nameAndUrl.get(1));
            }
        
            return myProjectPS;
        }
    }
}
