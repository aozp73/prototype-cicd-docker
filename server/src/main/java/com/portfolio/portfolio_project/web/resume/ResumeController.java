package com.portfolio.portfolio_project.web.resume;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.portfolio.portfolio_project.core.dto.ResponseDTO;
import com.portfolio.portfolio_project.service.ResumeService;
import com.portfolio.portfolio_project.web.resume.ResumeDTO_Out.FindAllDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ResumeController {

    private final ResumeService resumeService;

    // FindAll
    @GetMapping("/resume")
    public String resume_findAll(Model model){
        FindAllDTO resumeAllDTO = resumeService.resume_findAll();
        model.addAttribute("resumeAllDTO", resumeAllDTO);
        
        return "/resume";
    }

    // POST
    @PostMapping("/auth/resume/schooledu")
    public ResponseEntity<?> resume_schooledu_post(@RequestBody ResumeDTO_In.Schooledu_postDTO postDTO_In){
        ResumeDTO_Out.SchoolEdu_postDTO postDTO_Out = resumeService.resume_schooledu_post(postDTO_In);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(postDTO_Out));
    }
    @PostMapping("/auth/resume/academyedu")
    public ResponseEntity<?> resume_academyedu_post(@RequestBody ResumeDTO_In.Academyedu_postDTO postDTO_In){
        ResumeDTO_Out.AcademyEdu_postDTO postDTO_Out = resumeService.resume_academyedu_post(postDTO_In);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(postDTO_Out));
    }
    @PostMapping("/auth/resume/certificate")
    public ResponseEntity<?> resume_certificate_post(@RequestBody ResumeDTO_In.Certificate_postDTO postDTO_In){
        ResumeDTO_Out.Certificate_postDTO postDTO_Out = resumeService.resume_certificate_post(postDTO_In);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(postDTO_Out));
    }
    @PostMapping("/auth/resume/selfstudy")
    public ResponseEntity<?> resume_selfstudy_post(@RequestBody ResumeDTO_In.Selfstudy_postDTO postDTO_In){
        ResumeDTO_Out.SelfStudy_postDTO postDTO_Out = resumeService.resume_selfstudy_post(postDTO_In);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(postDTO_Out));
    }

    // DELETE
    @DeleteMapping("/auth/resume/schooledu")
    public ResponseEntity<?> resume_schooledu_delete(@RequestParam("resumeID") String resumeID){
        resumeService.resume_schooledu_delete(resumeID);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(""));
    }
    @DeleteMapping("/auth/resume/academyedu")
    public ResponseEntity<?> resume_academyedu_delete(@RequestParam("resumeID") String resumeID){
        resumeService.resume_academyedu_delete(resumeID);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(""));
    }
    @DeleteMapping("/auth/resume/certificate")
    public ResponseEntity<?> resume_certificate_delete(@RequestParam("resumeID") String resumeID){
        resumeService.resume_certificate_delete(resumeID);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(""));
    }
    @DeleteMapping("/auth/resume/selfstudy")
    public ResponseEntity<?> resume_selfstudy_delete(@RequestParam("resumeID") String resumeID){
        resumeService.resume_selfstudy_delete(resumeID);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(""));
    }

    // Row Move
    @PostMapping("/auth/resume/updateOrder/schooledu")
    public ResponseEntity<?> resume_schooledu_updateOrder(@RequestBody List<ResumeDTO_In.OrderUpdateDto> updates){
        resumeService.resume_schoolEdu_updateOrder(updates);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(""));
    }
    @PostMapping("/auth/resume/updateOrder/academyedu")
    public ResponseEntity<?> resume_academyedu_updateOrder(@RequestBody List<ResumeDTO_In.OrderUpdateDto> updates){
        resumeService.resume_academyedu_updateOrder(updates);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(""));
    }
    @PostMapping("/auth/resume/updateOrder/certificate")
    public ResponseEntity<?> resume_certificate_updateOrder(@RequestBody List<ResumeDTO_In.OrderUpdateDto> updates){
        resumeService.resume_certificate_updateOrder(updates);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(""));
    }
    @PostMapping("/auth/resume/updateOrder/selfstudy")
    public ResponseEntity<?> resume_selfstudy_updateOrder(@RequestBody List<ResumeDTO_In.OrderUpdateDto> updates){
        resumeService.resume_selfstudy_updateOrder(updates);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(""));
    }

}
