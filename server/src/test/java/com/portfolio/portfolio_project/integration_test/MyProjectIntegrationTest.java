package com.portfolio.portfolio_project.integration_test;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.portfolio_project.AbstractIntegrationTest;
import com.portfolio.portfolio_project.core.jwt.MyJwtProvider;
import com.portfolio.portfolio_project.domain.jpa.myproject.enums.ProjectRole;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project.MyProject;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project.MyProjectRepository;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project_role_code.MyProjectRoleCode;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project_role_code.MyProjectRoleCodeRepository;
import com.portfolio.portfolio_project.domain.jpa.user.User;
import com.portfolio.portfolio_project.integration_test.dummy.MyProjectDummy;
import com.portfolio.portfolio_project.web.myproject.MyProjectDTO_In;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisplayName("프로젝트 페이지 - 통합 테스트")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class MyProjectIntegrationTest extends AbstractIntegrationTest {
    
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private EntityManager em;
    @Autowired
    private MyJwtProvider myJwtProvider;
    @Autowired
    private MyProjectRepository myProjectRepository;
    @Autowired
    private MyProjectRoleCodeRepository myProjectRoleCodeRepository;

    @BeforeEach
    public void init() {
            em.createNativeQuery("ALTER TABLE my_project_tb AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("ALTER TABLE my_project_role_code_tb AUTO_INCREMENT = 1").executeUpdate();
            setup();
    }

    
    @DisplayName("프로젝트 등록")
    @Test
    public void project_post_test() throws Exception {
        // given
        String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("projectName", "등록 프로젝트 이름");
            params.add("member", "3");
            params.add("startDate", "2023-09-03");
            params.add("endDate", "2023-09-05");
            params.add("selectedRoles", "BackEnd,FrontEnd");
            params.add("readmeUrl", "등록 readme 주소");
            params.add("githubUrl", "등록 github 주소");
            params.add("projectImgBase64", "data:image/png;base64,aGVsbG8=");
            params.add("individualPerformanceBase64", "data:image/png;base64,aGVsbG8=");
            params.add("projectImageName", "등록 프로젝트 이미지 이름.png");
            params.add("projectImageType", "image/png");
            params.add("individualPerformanceImageName", "등록 개인수행 이미지 이름.png");
            params.add("individualPerformanceImageType", "image/png");

        // when
        ResultActions resultActions = mvc
                                        .perform(post("/auth/myproject")
                                        .params(params)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.info("결과 : " + responseBody);

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(3L))
                .andExpect(jsonPath("$.data.projectName").value("등록 프로젝트 이름"))
                .andExpect(jsonPath("$.data.member").value(3))
                .andExpect(jsonPath("$.data.selectedRoles", hasSize(2)));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("프로젝트 수정")
    @Test
    public void project_put_test() throws Exception {
            // given
            String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());

            MyProjectDTO_In.PutDTO.ImageDetails projectImageDetails = new MyProjectDTO_In.PutDTO.ImageDetails(
                    "data:image/png;base64,aGVsbG8=",
                    "수정 프로젝트 이미지 이름.png",
                    "image/png",
                    true 
            );

            MyProjectDTO_In.PutDTO.ImageDetails featureImageDetails = new MyProjectDTO_In.PutDTO.ImageDetails(
                    "data:image/png;base64,aGVsbG8=",
                    "수정 개인수행 이미지 이름.png",
                    "image/png",
                    true 
            );

            List<String> selectedRoles = Arrays.asList("BackEnd", "FrontEnd", "DevOps");

            MyProjectDTO_In.PutDTO putDTO_In = new MyProjectDTO_In.PutDTO(1L,
                                                                            "수정 프로젝트 이름",
                                                                            2,
                                                                            "2023-09-03",
                                                                            "2023-09-05",
                                                                            "수정 readme 주소",
                                                                            "수정 github 주소",
                                                                            selectedRoles,
                                                                            true, 
                                                                            projectImageDetails,
                                                                            featureImageDetails);

            String requestBody = om.writeValueAsString(putDTO_In);

            // when
            ResultActions resultActions = mvc
                                            .perform(put("/auth/myproject").content(requestBody)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
            String responseBody = resultActions.andReturn().getResponse().getContentAsString();
            log.info("결과 : " + responseBody);

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.id").value(1L))
                    .andExpect(jsonPath("$.data.projectName").value("수정 프로젝트 이름"))
                    .andExpect(jsonPath("$.data.member").value(2))
                    .andExpect(jsonPath("$.data.githubUrl").value("수정 github 주소"))
                    .andExpect(jsonPath("$.data.selectedRoles", hasSize(3)));
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("프로젝트 삭제")
    @Test
    public void project_delete_test() throws Exception {
            // given
            String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());
            Long projectPK = 1L;

            // when
            ResultActions resultActions = mvc
                                            .perform(delete("/auth/myproject?projectPK="+projectPK)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
                                            
            // then
            List<MyProject> mainIntroduces = myProjectRepository.findAll();
            assertEquals(1, mainIntroduces.size());
            resultActions.andExpect(status().isOk());
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("프로젝트 조회")
    @Test
    public void project_findAll_test() throws Exception {
            // given

            // when
            ResultActions resultActions = mvc
                                            .perform(get("/project")
                                            .contentType(MediaType.APPLICATION_JSON));

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("myProjectList"))
                    .andExpect(model().attribute("myProjectList", hasSize(2)));
    }


    // ===========  Entity 세팅  =================================================

    public void setup(){
        List<MyProject> myProjects = new ArrayList<>();
        myProjects.add(MyProjectDummy.newMyProject1());
        myProjects.add(MyProjectDummy.newMyProject2());
        myProjectRepository.saveAll(myProjects);

        for (ProjectRole role : ProjectRole.values()) {
        MyProjectRoleCode myProjectRoleCode = MyProjectRoleCode.builder()
                .projectRole(role)
                .build();
        Optional<MyProjectRoleCode> myProjectRoleCodeCheck = myProjectRoleCodeRepository.findByProjectRole(role);
        if (!myProjectRoleCodeCheck.isPresent()) {
                myProjectRoleCodeRepository.save(myProjectRoleCode);
        }
        }

        em.flush();
        em.clear();
    }
}
