package com.portfolio.portfolio_project.integration_test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.portfolio.portfolio_project.AbstractIntegrationTest;
import com.portfolio.portfolio_project.core.jwt.MyJwtProvider;
import com.portfolio.portfolio_project.domain.jpa.skills.enums.SkillType;
import com.portfolio.portfolio_project.domain.jpa.skills.my_skill.MySkill;
import com.portfolio.portfolio_project.domain.jpa.skills.my_skill.MySkillRepository;
import com.portfolio.portfolio_project.domain.jpa.skills.my_skill_type_code.MySkillTypeCode;
import com.portfolio.portfolio_project.domain.jpa.skills.my_skill_type_code.MySkillTypeCodeRepository;
import com.portfolio.portfolio_project.domain.jpa.user.User;
import com.portfolio.portfolio_project.integration_test.dummy.MySkillDummy;
import com.portfolio.portfolio_project.web.myskills.MySkillDTO_In;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisplayName("스킬 페이지 - 통합 테스트")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class MySkillIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private EntityManager em;
    @Autowired
    private MyJwtProvider myJwtProvider;
    @Autowired
    private MySkillRepository mySkillRepository;
    @Autowired
    private MySkillTypeCodeRepository mySkillTypeCodeRepository;

    @BeforeEach
    public void init() {
            em.createNativeQuery("ALTER TABLE my_skill_tb AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("ALTER TABLE my_skill_type_code_tb AUTO_INCREMENT = 1").executeUpdate();
            setup();
    }


    @DisplayName("스킬 등록/제거")
    @Test
    public void skill_post_delete_test() throws Exception {
            // given
            String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());

            MySkillDTO_In.PostDTO.SkillDTO javaSkill = new MySkillDTO_In.PostDTO.SkillDTO("Java", "removed");
            MySkillDTO_In.PostDTO.SkillDTO cssSkill = new MySkillDTO_In.PostDTO.SkillDTO("CSS", "removed");
            MySkillDTO_In.PostDTO.SkillDTO pythonSkill = new MySkillDTO_In.PostDTO.SkillDTO("Python", "added");

            List<MySkillDTO_In.PostDTO.SkillDTO> BackEnd = Arrays.asList(javaSkill, pythonSkill);
            List<MySkillDTO_In.PostDTO.SkillDTO> FrontEnd = Arrays.asList(cssSkill);
            List<MySkillDTO_In.PostDTO.SkillDTO> DevOps = new ArrayList<>(); 
            List<MySkillDTO_In.PostDTO.SkillDTO> ETC = new ArrayList<>(); 

            MySkillDTO_In.PostDTO postDTO_In = new MySkillDTO_In.PostDTO(BackEnd, FrontEnd, DevOps, ETC);

            String requestBody = om.writeValueAsString(postDTO_In);

            // when
            ResultActions resultActions = mvc
                                            .perform(post("/auth/skills").content(requestBody)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
            String responseBody = resultActions.andReturn().getResponse().getContentAsString();
            log.info("결과 : " + responseBody);

            // then
            List<MySkill> mySkills = mySkillRepository.findAll();
            assertEquals(1, mySkills.size());
            assertEquals("Python", mySkills.get(0).getSkill());
            resultActions.andExpect(status().isOk());
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("스킬 조회")
    @Test
    public void skill_findAll_test() throws Exception {
            // given

            // when
            MvcResult mvcResult = mvc.perform(get("/skills")
                                    .contentType(MediaType.APPLICATION_JSON))
                                    .andReturn();

            ModelAndView modelAndView = mvcResult.getModelAndView();
            
            if (modelAndView != null) {
                    Object allSkillsObj = modelAndView.getModel().get("allSkills");
                    if (allSkillsObj instanceof String) {
                            String allSkillsJson = (String) allSkillsObj;

                            Map<SkillType, List<String>> skillsMap = new Gson().fromJson(allSkillsJson, new TypeToken<Map<SkillType, List<String>>>() {}.getType());
                            assertThat(skillsMap.get(SkillType.BackEnd)).hasSize(1);
                            assertThat(skillsMap.get(SkillType.BackEnd)).containsExactly("Java");
                            assertThat(skillsMap.get(SkillType.FrontEnd)).hasSize(1);
                            assertThat(skillsMap.get(SkillType.FrontEnd)).containsExactly("CSS");
                    }
            }
    }


    // ===========  Entity 세팅  =================================================

    public void setup(){
            for (SkillType type : SkillType.values()) {
                MySkillTypeCode mySkillTypeCode = MySkillTypeCode.builder()
                    .skillType(type)
                    .build();
                Optional<MySkillTypeCode> mySkillTypeCodeCheck = mySkillTypeCodeRepository.findBySkillType(type);
                if (!mySkillTypeCodeCheck.isPresent()) {
                    mySkillTypeCodeRepository.save(mySkillTypeCode);
                }
            }
            
            MySkillTypeCode backEnd = mySkillTypeCodeRepository.findById(1L).get();
            MySkillTypeCode frontEnd = mySkillTypeCodeRepository.findById(2L).get();

            List<MySkill> mySkills = new ArrayList<>();
            mySkills.add(MySkillDummy.newSkill1(backEnd));
            mySkills.add(MySkillDummy.newSkill2(frontEnd));

            mySkillRepository.saveAll(mySkills);


            em.flush();
            em.clear();
    }
}
