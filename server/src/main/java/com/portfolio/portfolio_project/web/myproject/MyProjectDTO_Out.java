package com.portfolio.portfolio_project.web.myproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.portfolio.portfolio_project.domain.jpa.myproject.my_project.MyProject;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project_role.MyProjectRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MyProjectDTO_Out {
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindAllDTO {
        private Long id;
        private String projectName;
        private Integer member;
        private String startDate;
        private String endDate;
        private List<String> roleCodes;
        private String readmeUrl;
        private String githubUrl;
        private String projectImgURL;
        private String individualPerformanceImageNameURL;

        public static FindAllDTO fromEntity(MyProject myProject, List<MyProjectRole> myProjectRoles) {
            // Server 측에서 변환하고 View에 넘기기
            List<String> roleCodes = myProjectRoles.stream()
                .map(myProjectRole -> {
                    Long roleCodeLong = myProjectRole.getRoleCode().getId();
                    int roleCode = roleCodeLong.intValue();
                    switch (roleCode) {
                        case 1:
                            return "BackEnd";
                        case 2:
                            return "FrontEnd";
                        case 3:
                            return "DevOps";
                        default:
                            return "Unknown";
                    }
                })
                .collect(Collectors.toList());

            return new FindAllDTO(
                myProject.getId(),
                myProject.getProjectName(),
                myProject.getMember(),
                myProject.getStartDate().toString(),
                myProject.getEndDate().toString(),
                roleCodes,
                myProject.getReadmeUrl(),
                myProject.getGithubUrl(),
                myProject.getProjectImgUrl(),
                myProject.getIndividualPerformanceImgUrl()
            );
        }

        public static List<FindAllDTO> fromEntityList(List<MyProject> myProjects, Map<Long, List<MyProjectRole>> roleMap) {
            List<FindAllDTO> dtoList = new ArrayList<>();
            for (MyProject myProject : myProjects) {
                // Service에서 MyProject id값을 key로 대응되는 role을 value로 저장하였음
                List<MyProjectRole> myProjectRoles = roleMap.getOrDefault(myProject.getId(), new ArrayList<>());
                dtoList.add(FindAllDTO.fromEntity(myProject, myProjectRoles));
            }
            return dtoList;
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostDTO {
        private Long id;

        private String projectName;
        private Integer member;
        private String startDate;
        private String endDate;

        // BackEnd,FrontEnd,DevOps 형식
        private List<String> selectedRoles;

        private String readmeUrl;
        private String githubUrl;

        private String projectImgURL;
        private String individualPerformanceImageNameURL;

        public static MyProjectDTO_Out.PostDTO fromEntity(MyProject myProjectPS, List<MyProjectRole> myProjectRoles){
            List<String> selectedRoles = myProjectRoles.stream()
                .map(myProjectRole -> {
                    Long roleCodeLong = myProjectRole.getRoleCode().getId();
                    int roleCode = roleCodeLong.intValue();
                    switch (roleCode) {
                        case 1:
                            return "BackEnd";
                        case 2:
                            return "FrontEnd";
                        case 3:
                            return "DevOps";
                        default:
                            return "Unknown";
                    }
                })
                .collect(Collectors.toList());

            return MyProjectDTO_Out.PostDTO.builder()
                .id(myProjectPS.getId())
                .projectName(myProjectPS.getProjectName())
                .member(myProjectPS.getMember())
                .startDate(myProjectPS.getStartDate().toString())
                .endDate(myProjectPS.getEndDate().toString())
                .selectedRoles(selectedRoles)
                .readmeUrl(myProjectPS.getReadmeUrl())
                .githubUrl(myProjectPS.getGithubUrl())
                .projectImgURL(myProjectPS.getProjectImgUrl())
                .individualPerformanceImageNameURL(myProjectPS.getIndividualPerformanceImgUrl())
                .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PutDTO {
        private Long id;

        private String projectName;
        private Integer member;
        private String startDate;
        private String endDate;

        // BackEnd,FrontEnd,DevOps 형식
        private List<String> selectedRoles;

        private String readmeUrl;
        private String githubUrl;

        private String projectImgURL;
        private String individualPerformanceImageNameURL;

        public static MyProjectDTO_Out.PutDTO fromEntity(MyProject myProjectPS, List<MyProjectRole> myProjectRoles){
            List<String> selectedRoles = myProjectRoles.stream()
                .map(myProjectRole -> {
                    Long roleCodeLong = myProjectRole.getRoleCode().getId();
                    int roleCode = roleCodeLong.intValue();
                    switch (roleCode) {
                        case 1:
                            return "BackEnd";
                        case 2:
                            return "FrontEnd";
                        case 3:
                            return "DevOps";
                        default:
                            return "Unknown";
                    }
                })
                .collect(Collectors.toList());

            return MyProjectDTO_Out.PutDTO.builder()
                .id(myProjectPS.getId())
                .projectName(myProjectPS.getProjectName())
                .member(myProjectPS.getMember())
                .startDate(myProjectPS.getStartDate().toString())
                .endDate(myProjectPS.getEndDate().toString())
                .selectedRoles(selectedRoles)
                .readmeUrl(myProjectPS.getReadmeUrl())
                .githubUrl(myProjectPS.getGithubUrl())
                .projectImgURL(myProjectPS.getProjectImgUrl())
                .individualPerformanceImageNameURL(myProjectPS.getIndividualPerformanceImgUrl())
                .build();
        }
    }
}
