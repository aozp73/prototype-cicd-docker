package com.portfolio.portfolio_project.integration_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.portfolio_project.AbstractIntegrationTest;
import com.portfolio.portfolio_project.core.jwt.MyJwtProvider;
import com.portfolio.portfolio_project.domain.jpa.user.User;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_academy_edu.ResumeAcademyEdu;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_academy_edu.ResumeAcademyEduRepository;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_certificate.ResumeCertificate;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_certificate.ResumeCertificateRepository;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_school_edu.ResumeSchoolEdu;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_school_edu.ResumeSchoolEduRepository;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_self_study.ResumeSelfStudy;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_self_study.ResumeSelfStudyRepository;
import com.portfolio.portfolio_project.integration_test.dummy.ResumeDummy;
import com.portfolio.portfolio_project.web.resume.ResumeDTO_In.Academyedu_postDTO;
import com.portfolio.portfolio_project.web.resume.ResumeDTO_In.Certificate_postDTO;
import com.portfolio.portfolio_project.web.resume.ResumeDTO_In.OrderUpdateDto;
import com.portfolio.portfolio_project.web.resume.ResumeDTO_In.Schooledu_postDTO;
import com.portfolio.portfolio_project.web.resume.ResumeDTO_In.Selfstudy_postDTO;
import com.portfolio.portfolio_project.web.resume.ResumeDTO_Out.FindAllDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisplayName("이력 페이지 - 통합 테스트")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
@Rollback(value = true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ResumeIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private EntityManager em;
    @Autowired
    private MyJwtProvider myJwtProvider;
    
    @Autowired
    private ResumeSchoolEduRepository resumeSchoolEduRepository;
    @Autowired
    private ResumeAcademyEduRepository resumeAcademyEduRepository;
    @Autowired
    private ResumeCertificateRepository resumeCertificateRepository;
    @Autowired
    private ResumeSelfStudyRepository resumeSelfStudyRepository;

    @BeforeEach
    public void init() {
        resumeSchoolEduRepository.deleteAll();
        resumeAcademyEduRepository.deleteAll();
        resumeCertificateRepository.deleteAll();
        resumeSelfStudyRepository.deleteAll();

        setup();
    }

    @AfterEach
    public void clean() {
        resumeSchoolEduRepository.deleteAll();
        resumeAcademyEduRepository.deleteAll();
        resumeCertificateRepository.deleteAll();
        resumeSelfStudyRepository.deleteAll();
    }

    
    // POST 테스트
    @DisplayName("학교교육 이력 등록")
    @Test
    public void resume_schooledu_post_test() throws Exception {
            // given
            String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());
            Schooledu_postDTO schooledu_postDTO = new Schooledu_postDTO();
            schooledu_postDTO.setValues(Arrays.asList("2018-09-01","2022-12-31","졸업","자바 대학교","기계 공학과","3.7"));
            String requestBody = om.writeValueAsString(schooledu_postDTO);

            // when
            ResultActions resultActions = mvc
                                            .perform(post("/auth/resume/schooledu").content(requestBody)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
            String responseBody = resultActions.andReturn().getResponse().getContentAsString();
            log.info("결과 : " + responseBody);

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.schoolAdmissionDate").value("2018-09-01"))
                    .andExpect(jsonPath("$.data.schoolName").value("자바 대학교"));
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    } 

    @DisplayName("학원교육 이력 등록")
    @Test
    public void resume_academyedu_post_test() throws Exception {
            // given
            String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());
            Academyedu_postDTO academyedu_postDTO = new Academyedu_postDTO();
            academyedu_postDTO.setValues(Arrays.asList("2022-09-01","2023-03-31","수료","파이썬 아카데미","웹개발 과정","-"));
            String requestBody = om.writeValueAsString(academyedu_postDTO);

            // when
            ResultActions resultActions = mvc
                                            .perform(post("/auth/resume/academyedu").content(requestBody)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
            String responseBody = resultActions.andReturn().getResponse().getContentAsString();
            log.info("결과 : " + responseBody);

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.academyEnrollDate").value("2022-09-01"))
                    .andExpect(jsonPath("$.data.academyName").value("파이썬 아카데미"));
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    } 

    @DisplayName("자격증 이력 등록")
    @Test
    public void resume_certificate_post_test() throws Exception {
            // given
            String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());
            Certificate_postDTO certificate_postDTO = new Certificate_postDTO();
            certificate_postDTO.setValues(Arrays.asList("2023-06-01","IT 자격증","정보처리기사","산업인력공단","최종합격"));
            String requestBody = om.writeValueAsString(certificate_postDTO);

            // when
            ResultActions resultActions = mvc
                                            .perform(post("/auth/resume/certificate").content(requestBody)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
            String responseBody = resultActions.andReturn().getResponse().getContentAsString();
            log.info("결과 : " + responseBody);

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.acquisitionDate").value("2023-06-01"))
                    .andExpect(jsonPath("$.data.certificateName").value("정보처리기사"));
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    } 

    @DisplayName("자기주도학습 이력 등록")
    @Test
    public void resume_selfstudy_post_test() throws Exception {
            // given
            String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());
            Selfstudy_postDTO selfstudy_postDTO = new Selfstudy_postDTO();
            selfstudy_postDTO.setValues(Arrays.asList("2023-08-01","데브옵스","Docker","인프런","학습 링크"));
            String requestBody = om.writeValueAsString(selfstudy_postDTO);

            // when
            ResultActions resultActions = mvc
                                            .perform(post("/auth/resume/selfstudy").content(requestBody)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
            String responseBody = resultActions.andReturn().getResponse().getContentAsString();
            log.info("결과 : " + responseBody);

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.selfStudyDate").value("2023-08-01"))
                    .andExpect(jsonPath("$.data.selfStudyTheme").value("Docker"));
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    } 


    // DELETE
    @DisplayName("학교교육 이력 삭제")
    @Test
    public void resume_schooledu_delete_test() throws Exception {
            // given
            String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());
            List<ResumeSchoolEdu> schoolEdus = resumeSchoolEduRepository.findAll();
            String resumeID = schoolEdus.get(0).getId();

            // when
            ResultActions resultActions = mvc
                                            .perform(delete("/auth/resume/schooledu?resumeID="+resumeID)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
                                            
            // then
            resultActions.andExpect(status().isOk());
            List<ResumeSchoolEdu> schoolEdus2 = resumeSchoolEduRepository.findAll();
            assertEquals(1, schoolEdus2.size()); // setup() 데이터를 근거로 검증
            assertEquals("2020-05-03", schoolEdus2.get(0).getSchoolGraduateDate());
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("학원교육 이력 삭제")
    @Test
    public void resume_academyedu_delete_test() throws Exception {
            // given
            String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());
            List<ResumeAcademyEdu> academyEdus = resumeAcademyEduRepository.findAll();
            String resumeID = academyEdus.get(0).getId();

            // when
            ResultActions resultActions = mvc
                                            .perform(delete("/auth/resume/academyedu?resumeID="+resumeID)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
                                            
            // then
            resultActions.andExpect(status().isOk());
            List<ResumeAcademyEdu> academyEdus2 = resumeAcademyEduRepository.findAll();
            assertEquals(1, academyEdus2.size()); // setup() 데이터를 근거로 검증
            assertEquals("2018-09-01", academyEdus2.get(0).getAcademyCompletionDate());
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("자격증 이력 삭제")
    @Test
    public void resume_certificate_delete_test() throws Exception {
            // given
            String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());
            List<ResumeCertificate> certificates = resumeCertificateRepository.findAll();
            String resumeID = certificates.get(0).getId();

            // when
            ResultActions resultActions = mvc
                                            .perform(delete("/auth/resume/certificate?resumeID="+resumeID)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
                                            
            // then
            resultActions.andExpect(status().isOk());
            List<ResumeCertificate> certificates2 = resumeCertificateRepository.findAll();
            assertEquals(1, certificates2.size()); // setup() 데이터를 근거로 검증
            assertEquals("한국TOEIC위원회", certificates2.get(0).getCertificateIssuingAgency());
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("자기주도학습 이력 삭제")
    @Test
    public void resume_selfstudy_delete_test() throws Exception {
            // given
            String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());
            List<ResumeSelfStudy> selfStudies = resumeSelfStudyRepository.findAll();
            String resumeID = selfStudies.get(0).getId();

            // when
            ResultActions resultActions = mvc
                                            .perform(delete("/auth/resume/selfstudy?resumeID="+resumeID)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
                                            
            // then
            resultActions.andExpect(status().isOk());
            List<ResumeSelfStudy> selfStudies2 = resumeSelfStudyRepository.findAll();
            assertEquals(1, selfStudies2.size()); // setup() 데이터를 근거로 검증
            assertEquals("YouTube", selfStudies2.get(0).getSelfStudyPlatform());
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // Row Move
    @DisplayName("학교교육 row 이동")
    @Test
    public void resume_schooledu_rowmove_test() throws Exception {
            // given
            String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());

            List<ResumeSchoolEdu> schoolEdus = resumeSchoolEduRepository.findAll();

            List<OrderUpdateDto> updates = new ArrayList<>();
            OrderUpdateDto dto1 = new OrderUpdateDto(schoolEdus.get(0).getId(),2);
            OrderUpdateDto dto2 = new OrderUpdateDto(schoolEdus.get(1).getId(),1);
            updates.add(dto1);
            updates.add(dto2);
            
            String requestBody = om.writeValueAsString(updates);

            // when
            ResultActions resultActions = mvc
                .perform(post("/auth/resume/updateOrder/schooledu").content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
            String responseBody = resultActions.andReturn().getResponse().getContentAsString();
            log.info("결과 : " + responseBody);

            // then
            List<ResumeSchoolEdu> schoolEdus2 = resumeSchoolEduRepository.findAll();

            resultActions.andExpect(status().isOk());
            assertEquals(2,schoolEdus2.get(0).getOrder());
            assertEquals(1,schoolEdus2.get(1).getOrder());
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    } 

    @DisplayName("학원교육 row 이동")
    @Test
    public void resume_academyedu_rowmove_test() throws Exception {
            // given
            String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());

            List<ResumeAcademyEdu> academyEdus = resumeAcademyEduRepository.findAll();

            List<OrderUpdateDto> updates = new ArrayList<>();
            OrderUpdateDto dto1 = new OrderUpdateDto(academyEdus.get(0).getId(),2);
            OrderUpdateDto dto2 = new OrderUpdateDto(academyEdus.get(1).getId(),1);
            updates.add(dto1);
            updates.add(dto2);

            String requestBody = om.writeValueAsString(updates);

            // when
            ResultActions resultActions = mvc
                .perform(post("/auth/resume/updateOrder/academyedu").content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
            String responseBody = resultActions.andReturn().getResponse().getContentAsString();
            log.info("결과 : " + responseBody);

            // then
            List<ResumeAcademyEdu> academyEdus2 = resumeAcademyEduRepository.findAll();

            resultActions.andExpect(status().isOk());
            assertEquals(2,academyEdus2.get(0).getOrder());
            assertEquals(1,academyEdus2.get(1).getOrder());
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    } 

    @DisplayName("자격증 row 이동")
    @Test
    public void resume_certificate_rowmove_test() throws Exception {
            // given
            String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());

            List<ResumeCertificate> certificates = resumeCertificateRepository.findAll();

            List<OrderUpdateDto> updates = new ArrayList<>();
            OrderUpdateDto dto1 = new OrderUpdateDto(certificates.get(0).getId(),2);
            OrderUpdateDto dto2 = new OrderUpdateDto(certificates.get(1).getId(),1);
            updates.add(dto1);
            updates.add(dto2);

            String requestBody = om.writeValueAsString(updates);

            // when
            ResultActions resultActions = mvc
                .perform(post("/auth/resume/updateOrder/certificate").content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
            String responseBody = resultActions.andReturn().getResponse().getContentAsString();
            log.info("결과 : " + responseBody);

            // then
            List<ResumeCertificate> certificates2 = resumeCertificateRepository.findAll();

            resultActions.andExpect(status().isOk());
            assertEquals(2,certificates2.get(0).getOrder());
            assertEquals(1,certificates2.get(1).getOrder());
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    } 

    @DisplayName("자기주도학습 row 이동")
    @Test
    public void resume_selfstudy_rowmove_test() throws Exception {
            // given
            String jwt = myJwtProvider.create(User.builder().id(1L).email("aozp73@naver.com").role("admin").build());

            List<ResumeSelfStudy> selfStudies = resumeSelfStudyRepository.findAll();

            List<OrderUpdateDto> updates = new ArrayList<>();
            OrderUpdateDto dto1 = new OrderUpdateDto(selfStudies.get(0).getId(),2);
            OrderUpdateDto dto2 = new OrderUpdateDto(selfStudies.get(1).getId(),1);
            updates.add(dto1);
            updates.add(dto2);

            String requestBody = om.writeValueAsString(updates);

            // when
            ResultActions resultActions = mvc
                .perform(post("/auth/resume/updateOrder/selfstudy").content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header(MyJwtProvider.HEADER, MyJwtProvider.TOKEN_PREFIX + jwt));
            String responseBody = resultActions.andReturn().getResponse().getContentAsString();
            log.info("결과 : " + responseBody);

            // then
            List<ResumeSelfStudy> selfStudies2 = resumeSelfStudyRepository.findAll();

            resultActions.andExpect(status().isOk());
            assertEquals(2,selfStudies2.get(0).getOrder());
            assertEquals(1,selfStudies2.get(1).getOrder());
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    } 


    // FindAll
    @DisplayName("이력 정보 조회")
    @Test
    public void resume_findAll_test() throws Exception {
            // given

            // when
            ResultActions resultActions = mvc
                                            .perform(get("/resume")
                                            .contentType(MediaType.APPLICATION_JSON));

            MvcResult mvcResult = resultActions.andReturn();
            ModelAndView modelAndView = mvcResult.getModelAndView();
            FindAllDTO findAllDTO = new FindAllDTO();
            if (modelAndView != null) {
                findAllDTO = (FindAllDTO) modelAndView.getModel().get("resumeAllDTO");
                
            }
            
            // then
            resultActions.andExpect(status().isOk());
            assertEquals("파이썬 대학교", findAllDTO.getResumeSchoolEdus().get(1).getSchoolName());
            assertEquals("수료", findAllDTO.getResumeAcademyEdus().get(1).getAcademyCompletionStatus());
            assertEquals("한국TOEIC위원회", findAllDTO.getResumeCertificates().get(1).getCertificateIssuingAgency());
            assertEquals("YouTube", findAllDTO.getResumeSelfStudies().get(1).getSelfStudyPlatform());
            resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }


    // ===========  각 Document 세팅  =================================================

    public void setup() {
        resumeSchoolEduRepository.save(ResumeDummy.newResumeSchoolEdu1());
        resumeSchoolEduRepository.save(ResumeDummy.newResumeSchoolEdu2());

        resumeAcademyEduRepository.save(ResumeDummy.newResumeAcademyEdu1());
        resumeAcademyEduRepository.save(ResumeDummy.newResumeAcademyEdu2());

        resumeCertificateRepository.save(ResumeDummy.newResumeCertificate1());
        resumeCertificateRepository.save(ResumeDummy.newResumeCertificate2());

        resumeSelfStudyRepository.save(ResumeDummy.newResumeSelfStudy1());
        resumeSelfStudyRepository.save(ResumeDummy.newResumeSelfStudy2());

        em.flush();
        em.clear();
    }
}
