package com.portfolio.portfolio_project.integration_test;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.portfolio_project.core.jwt.MyJwtProvider;
import com.portfolio.portfolio_project.domain.jpa.main.main_introduce.MainIntroduce;
import com.portfolio.portfolio_project.domain.jpa.main.main_introduce.MainIntroduceRepository;
import com.portfolio.portfolio_project.domain.jpa.user.User;
import com.portfolio.portfolio_project.integration_test.dummy.MainIntroduceDummy;
import com.portfolio.portfolio_project.web.main.MainIntroduceDTO_In;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisplayName("메인페이지 통합 테스트")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class MainIntroduceIntegrationTest {
    
        @Autowired
        private MockMvc mvc;
        @Autowired
        private ObjectMapper om;
        
        @Autowired
        private MyJwtProvider myJwtProvider;
        @Autowired
        private MainIntroduceRepository mainIntroduceRepository;

        @BeforeEach
        public void setUp() {
                List<MainIntroduce> mainIntroduces = new ArrayList<>();
                mainIntroduces.add(MainIntroduceDummy.newMainIntroduce1());
                mainIntroduces.add(MainIntroduceDummy.newMainIntroduce2());
                mainIntroduceRepository.saveAll(mainIntroduces);
        }

        @DisplayName("게시글 등록")
        @Test
        public void main_post_test() throws Exception {
                // given
                String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());
                MainIntroduceDTO_In.PostDTO postDTO_In = new MainIntroduceDTO_In.PostDTO("등록 제목", 
                                                                                         "등록 내용", 
                                                                                         "등록 이미지 이름.png",
                                                                                         "image/png",
                                                                                         "data:image/png;base64,aGVsbG8=");

                String requestBody = om.writeValueAsString(postDTO_In);

                // when
                ResultActions resultActions = mvc
                                .perform(post("/auth/main").content(requestBody)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                log.info("결과 : " + responseBody);

                // then
                resultActions
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.data.id").exists())
                        .andExpect(jsonPath("$.data.postTitle").value("등록 제목"))
                        .andExpect(jsonPath("$.data.postContent").value("등록 내용"))
                        .andExpect(jsonPath("$.data.imgURL").exists());

        }

        @DisplayName("게시글 수정")
        @Test
        public void main_put_test() throws Exception {
                // given
                String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());
                MainIntroduceDTO_In.PutDTO putDTO_In = new MainIntroduceDTO_In.PutDTO(1L,
                                                                                      "수정 제목", 
                                                                                      "수정 내용", 
                                                                                      "수정 이미지 이름.png",
                                                                                      "image/png",
                                                                                      "data:image/png;base64,aGVsbG8=",
                                                                                      true);

                String requestBody = om.writeValueAsString(putDTO_In);

                // when
                ResultActions resultActions = mvc
                                .perform(put("/auth/main").content(requestBody)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                log.info("결과 : " + responseBody);

                // then
                resultActions
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.data.id").exists())
                        .andExpect(jsonPath("$.data.postTitle").value("수정 제목"))
                        .andExpect(jsonPath("$.data.postContent").value("수정 내용"))
                        .andExpect(jsonPath("$.data.imgURL").exists());
        }

        @DisplayName("게시글 삭제")
        @Test
        public void main_delete_test() throws Exception {
                // given
                String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());
                Long postPK = 1L;

                // when
                ResultActions resultActions = mvc
                                .perform(delete("/auth/main?postPK="+postPK)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
                                                
                // then
                List<MainIntroduce> mainIntroduces = mainIntroduceRepository.findAll();
                assertEquals(1, mainIntroduces.size());
                resultActions.andExpect(status().isOk());
        }

        @DisplayName("게시글 조회")
        @Test
        public void main_findAll_test() throws Exception {
                // given

                // when
                ResultActions resultActions = mvc
                                .perform(get("/mainpage")
                                                .contentType(MediaType.APPLICATION_JSON));

                // then
                resultActions
                        .andExpect(status().isOk())
                        .andExpect(model().attributeExists("mainIntroduceList"))
                        .andExpect(model().attribute("mainIntroduceList", hasSize(2)));
        }
}
