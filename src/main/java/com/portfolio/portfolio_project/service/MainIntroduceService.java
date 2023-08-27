package com.portfolio.portfolio_project.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.portfolio.portfolio_project.core.exception.Exception400;
import com.portfolio.portfolio_project.core.exception.Exception500;
import com.portfolio.portfolio_project.core.util.s3_utils.BASE64DecodedMultipartFile;
import com.portfolio.portfolio_project.core.util.s3_utils.S3Utils;
import com.portfolio.portfolio_project.domain.jpa.main.main_introduce.MainIntroduce;
import com.portfolio.portfolio_project.domain.jpa.main.main_introduce.MainIntroduceRepository;
import com.portfolio.portfolio_project.web.main.MainIntroduceDTO_In;
import com.portfolio.portfolio_project.web.main.MainIntroduceDTO_Out;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MainIntroduceService {
    
    private final MainIntroduceRepository mainIntroduceRepository;
    private final S3Utils s3Utils;

    @Transactional(readOnly = true)
    public List<MainIntroduceDTO_Out.PostDTO> main_findAll(){
        List<MainIntroduce> mainIntroduces = mainIntroduceRepository.findAll();
        
        return MainIntroduceDTO_Out.PostDTO.fromEntityList(mainIntroduces);
    }

    @Transactional
    public MainIntroduceDTO_Out.PostDTO main_post(MainIntroduceDTO_In.postDTO postDTO_In){
        List<String> nameAndUrl = s3Utils.uploadImageToS3(postDTO_In.getImageData(), postDTO_In.getImageName(), postDTO_In.getContentType());

        MainIntroduce mainIntroduce = postDTO_In.toEntity();
        mainIntroduce.setIntroduceImgName(nameAndUrl.get(0));
        mainIntroduce.setIntroduceImgUrl(nameAndUrl.get(1));

        mainIntroduceRepository.save(mainIntroduce);
        
        return MainIntroduceDTO_Out.PostDTO.fromEntity(mainIntroduce);
    }


    @Transactional
    public MainIntroduceDTO_Out.PutDTO main_put(MainIntroduceDTO_In.putDTO putDTO_In){
        MainIntroduce mainIntroducePS = mainIntroduceRepository.findById(putDTO_In.getId()).orElseThrow(() -> {
            throw new Exception400("업데이트 하려는 게시물이 존재하지 않습니다.");
        });

        putDTO_In.putEntity(mainIntroducePS, putDTO_In);

        if (putDTO_In.getImgChangeCheck()) {
            List<String> nameAndUrl = s3Utils.uploadImageToS3(putDTO_In.getImageData(), putDTO_In.getImageName(), putDTO_In.getContentType());
            mainIntroducePS.setIntroduceImgName(nameAndUrl.get(0));
            mainIntroducePS.setIntroduceImgUrl(nameAndUrl.get(1));
        }

        return MainIntroduceDTO_Out.PutDTO.fromEntity(mainIntroducePS);
    }

    @Transactional
    public void main_delete(Long postPK){
        MainIntroduce mainIntroducePS = mainIntroduceRepository.findById(postPK).orElseThrow(() -> {
            throw new Exception400("삭제 하려는 게시물이 존재하지 않습니다.");
        });

        try {
            mainIntroduceRepository.delete(mainIntroducePS);
        } catch (Exception e) {
            throw new Exception500("게시물 삭제에 실패하였습니다.");
        }
    }
}
