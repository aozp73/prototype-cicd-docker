package com.portfolio.portfolio_project.web.myblog;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.portfolio.portfolio_project.core.dto.ResponseDTO;
import com.portfolio.portfolio_project.service.MyBlogService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MyBlogController {
    
    private final MyBlogService myBlogService;

    // FindAll
    @GetMapping("/blog")
    public String myblogpage(Model model){
        List<MyBlogDTO_Out.FindAllDTO> myBlogList = myBlogService.myBlog_findAll();
         model.addAttribute("myBlogList", myBlogList);

        return "/myblog";
    }

    // POST
    @PostMapping("/auth/blog")
    public ResponseEntity<?> myBlog_post(@RequestBody MyBlogDTO_In.PostDTO postDTO_In){
        MyBlogDTO_Out.PostDTO postDTO_Out = myBlogService.myBlog_post(postDTO_In);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(postDTO_Out));
    }

    // PUT
    @PutMapping("/auth/blog")
    public ResponseEntity<?> myBlog_put(@RequestBody MyBlogDTO_In.PutDTO putDTO_In){
        MyBlogDTO_Out.PutDTO putDTO_Out = myBlogService.myBlog_put(putDTO_In);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(putDTO_Out));
    }

    // DELETE
    @DeleteMapping("/auth/blog")
    public ResponseEntity<?> myBlog_delete(@RequestParam("blogPK") Long blogPK){
        myBlogService.myBlog_delete(blogPK);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(""));
    }
}
