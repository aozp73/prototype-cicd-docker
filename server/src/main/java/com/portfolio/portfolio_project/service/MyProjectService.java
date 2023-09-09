package com.portfolio.portfolio_project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.portfolio_project.core.exception.Exception400;
import com.portfolio.portfolio_project.core.exception.Exception500;
import com.portfolio.portfolio_project.core.util.s3_utils.S3Utils;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project.MyProject;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project.MyProjectRepository;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project_role.MyProjectRole;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project_role.MyProjectRoleRepository;
import com.portfolio.portfolio_project.service.module.MyProjectModules;
import com.portfolio.portfolio_project.web.myproject.MyProjectDTO_In;
import com.portfolio.portfolio_project.web.myproject.MyProjectDTO_Out;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyProjectService {
    
    private final MyProjectRoleRepository myProjectRoleRepository;
    private final MyProjectRepository myProjectRepository;
    private final S3Utils s3Utils;
    private final MyProjectModules myProjectUtils;

    // FindAll
    @Transactional(readOnly = true)
    public List<MyProjectDTO_Out.FindAllDTO> findAllProjectsAndRoles() {
        // 1. MyProject 엔터티 리스트 가져오기
        List<MyProject> myProjects = myProjectRepository.findAll();

        // 2. MyProject 각각에 해당하는 MyProjectRole 리스트를 맵 형태에 저장 (fromEntityList에서 활용)
        Map<Long, List<MyProjectRole>> roleMap = new HashMap<>();
        for (MyProject myProject : myProjects) {
            List<MyProjectRole> myProjectRoles = myProjectRoleRepository.findAllByProject(myProject);
            roleMap.put(myProject.getId(), myProjectRoles);
        }

        return MyProjectDTO_Out.FindAllDTO.fromEntityList(myProjects, roleMap);
    }

    // POST
    @Transactional
    public MyProjectDTO_Out.PostDTO myProject_post(MyProjectDTO_In.PostDTO postDTO_In){
        // 1. 프로젝트 이미지, 개인수행 이미지 S3 업로드
        List<String> projectImg_nameAndUrl = s3Utils.uploadImageToS3(postDTO_In.getProjectImgBase64(), 
                                                                     postDTO_In.getProjectImageName(), 
                                                                     postDTO_In.getProjectImageType(),
                                                                     "my_project");
        List<String> individualPerformanceImg_nameAndUrl = s3Utils.uploadImageToS3(postDTO_In.getIndividualPerformanceBase64(), 
                                                                                   postDTO_In.getIndividualPerformanceImageName(), 
                                                                                   postDTO_In.getIndividualPerformanceImageType(),
                                                                                   "my_project_performance");

        // 2. MyProject 엔터티 저장
        MyProject myProject = postDTO_In.toEntity(projectImg_nameAndUrl, individualPerformanceImg_nameAndUrl);
        try {
            myProjectRepository.save(myProject);
        } catch (Exception e) {
            throw new Exception500("Project DB 저장에 실패하였습니다.");
        }

        // 3. 역할 파싱 및 MyProjectRole 엔터티 저장
        List<MyProjectRole> myProjectRoles = myProjectUtils.saveRolesForProject(postDTO_In.getSelectedRoles(), myProject);

        return MyProjectDTO_Out.PostDTO.fromEntity(myProject, myProjectRoles);
    }

    @Transactional
    public MyProjectDTO_Out.PutDTO myProject_put(MyProjectDTO_In.PutDTO putDTO_In) {
        // 1. MyProject 엔터티 저장
        List<String> projectImg_nameAndUrl = new ArrayList<>();
        List<String> individualPerformanceImg_nameAndUrl = new ArrayList<>();

        if (putDTO_In.getProjectImageDetails().getImgChangeCheck()) {
            projectImg_nameAndUrl = s3Utils.uploadImageToS3(putDTO_In.getProjectImageDetails().getImageSrc(), 
                                                                     putDTO_In.getProjectImageDetails().getImageName(), 
                                                                     putDTO_In.getProjectImageDetails().getContentType(),
                                                                     "my_project");
        }
        if (putDTO_In.getFeatureImageDetails().getImgChangeCheck()) {
            individualPerformanceImg_nameAndUrl = s3Utils.uploadImageToS3(putDTO_In.getFeatureImageDetails().getImageSrc(), 
                                                                                   putDTO_In.getFeatureImageDetails().getImageName(), 
                                                                                   putDTO_In.getFeatureImageDetails().getContentType(),
                                                                                   "my_project_performance");
        }

        MyProject myProjectPS = myProjectRepository.findById(putDTO_In.getProjectId()).orElseThrow(() ->{
            throw new Exception400("존재하지 않는 게시물 입니다.");
        });
        putDTO_In.toEntity(myProjectPS, projectImg_nameAndUrl, individualPerformanceImg_nameAndUrl);


        // 2. MyProjectRole 체크 및 갱신
        List<MyProjectRole> myProjectRolesPS = myProjectRoleRepository.findAllByProject(myProjectPS);

        if (putDTO_In.getHasRolesChanged()) {
            myProjectRoleRepository.deleteAll(myProjectRolesPS);

            String rolesString = String.join(",", putDTO_In.getSelectedRoles());
            myProjectRolesPS = myProjectUtils.saveRolesForProject(rolesString, myProjectPS);
        }
        
        return MyProjectDTO_Out.PutDTO.fromEntity(myProjectPS, myProjectRolesPS);
    }

    @Transactional
    public void myProject_delete(Long projectPK){
        MyProject myProjectPS = myProjectRepository.findById(projectPK).orElseThrow(() ->{
            throw new Exception400("존재하지 않는 게시물 입니다.");
        });
        
        try {
            myProjectRoleRepository.deleteAll(myProjectRoleRepository.findAllByProject(myProjectPS));
        } catch (Exception e) {
            throw new Exception500("Project role 삭제에 실패했습니다.");
        }
        try {
            myProjectRepository.delete(myProjectPS);            
        } catch (Exception e) {
            throw new Exception500("Project 삭제에 실패했습니다.");
        }
    }
}
