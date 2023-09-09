package com.portfolio.portfolio_project.web.myskills;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.Gson;
import com.portfolio.portfolio_project.core.dto.ResponseDTO;
import com.portfolio.portfolio_project.service.MySkillsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MySkillController {

    private final MySkillsService mySkillsService;

    // FindAll
    @GetMapping("/skills")
    public String myskillspage(Model model){
        MySkillDTO_Out.FindAllDTO mySkillsAllDTO = mySkillsService.findAllSkills();

        // JavaScript로 오브젝트를 바로 받을 수 있게 JSON 변환 후 응답
        Gson gson = new Gson();
        String jsonSkills = gson.toJson(mySkillsAllDTO.getSkillsMap());
        model.addAttribute("allSkills", jsonSkills);
        
        return "/myskills";
    }

    // POST
    @PostMapping("/auth/skills")
    public ResponseEntity<?> skills_post(@RequestBody MySkillDTO_In.PostDTO postDTO_In){
        mySkillsService.mySkills_post(postDTO_In);
        
        return ResponseEntity.ok().body(new ResponseDTO<>().data(""));
    }
}
