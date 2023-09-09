package com.portfolio.portfolio_project.web.myproject;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.portfolio.portfolio_project.core.dto.ResponseDTO;
import com.portfolio.portfolio_project.service.MyProjectService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MyProjectController {

    private final MyProjectService myProjectService;

    // FindAll
    @GetMapping("/project")
    public String projectpage(Model model){
        List<MyProjectDTO_Out.FindAllDTO> myProjectList = myProjectService.findAllProjectsAndRoles();
        model.addAttribute("myProjectList", myProjectList);

        return "/myproject";
    }

    // POST
    @PostMapping("/auth/myproject")
    public ResponseEntity<?> myproject_post(@ModelAttribute MyProjectDTO_In.PostDTO postDTO_In){
        MyProjectDTO_Out.PostDTO postDTO_Out = myProjectService.myProject_post(postDTO_In);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(postDTO_Out));
    }

    // PUT
    @PutMapping("/auth/myproject")
    public ResponseEntity<?> myproject_put(@RequestBody MyProjectDTO_In.PutDTO putDTO_In){
        MyProjectDTO_Out.PutDTO putDTO_Out = myProjectService.myProject_put(putDTO_In);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(putDTO_Out));
    }

    // DELETE
    @DeleteMapping("/auth/myproject")
    public ResponseEntity<?> main_delete(@RequestParam("projectPK") Long projectPK){
        myProjectService.myProject_delete(projectPK);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(""));
    }
}
