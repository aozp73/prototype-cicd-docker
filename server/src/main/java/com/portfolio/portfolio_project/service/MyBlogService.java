package com.portfolio.portfolio_project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.portfolio_project.core.exception.Exception400;
import com.portfolio.portfolio_project.core.exception.Exception500;
import com.portfolio.portfolio_project.core.util.s3_utils.S3Utils;
import com.portfolio.portfolio_project.domain.jpa.myblog.my_blog.MyBlog;
import com.portfolio.portfolio_project.domain.jpa.myblog.my_blog.MyBlogRepository;
import com.portfolio.portfolio_project.web.myblog.MyBlogDTO_In;
import com.portfolio.portfolio_project.web.myblog.MyBlogDTO_Out;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyBlogService {
    
    private final MyBlogRepository myBlogRepository;
    private final S3Utils s3Utils;

    // FindAll
    @Transactional(readOnly = true)
    public List<MyBlogDTO_Out.FindAllDTO> myBlog_findAll(){
        List<MyBlog> myBlogsPS = myBlogRepository.findAll();
        
        return MyBlogDTO_Out.FindAllDTO.fromEntityList(myBlogsPS);
    }

    // POST
    @Transactional
    public MyBlogDTO_Out.PostDTO myBlog_post(MyBlogDTO_In.PostDTO postDTO_In){
        List<String> blogImg_nameAndUrl = s3Utils.uploadImageToS3(postDTO_In.getImageData(), 
                                postDTO_In.getImageName(), 
                                postDTO_In.getContentType(),
                                "my_blog");

        MyBlog myBlog = postDTO_In.toEntity();
        myBlog.setBlogImgName(blogImg_nameAndUrl.get(0));
        myBlog.setBlogImgUrl(blogImg_nameAndUrl.get(1));  

        myBlogRepository.save(myBlog);
        
        return MyBlogDTO_Out.PostDTO.fromEntity(myBlog);
    }

    // PUT
    @Transactional
    public MyBlogDTO_Out.PutDTO myBlog_put(MyBlogDTO_In.PutDTO putDTO_In){
        MyBlog myblogPS = myBlogRepository.findById(putDTO_In.getId()).orElseThrow(() -> {
            throw new Exception400("업데이트하려는 게시물이 존재하지 않습니다.");
        });

        putDTO_In.toEntity(myblogPS);

        if (putDTO_In.getImgChangeCheck()) {
            List<String> nameAndUrl = s3Utils.uploadImageToS3(putDTO_In.getImageData(), 
                                                              putDTO_In.getImageName(), 
                                                              putDTO_In.getContentType(),
                                                              "my_blog");
            myblogPS.setBlogImgName(nameAndUrl.get(0));
            myblogPS.setBlogImgUrl(nameAndUrl.get(1));
        }

        return MyBlogDTO_Out.PutDTO.fromEntity(myblogPS);
    }

    // DELETE
    @Transactional
    public void myBlog_delete(Long blogPK){
        MyBlog myBlogPS = myBlogRepository.findById(blogPK).orElseThrow(() -> {
            throw new Exception400("삭제하려는 게시물이 존재하지 않습니다.");
        });

        try {
            myBlogRepository.delete(myBlogPS);
        } catch (Exception e) {
            throw new Exception500("게시물 삭제에 실패하였습니다.");
        }
    }

}
