package com.portfolio.portfolio_project.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.portfolio_project.core.exception.Exception400;
import com.portfolio.portfolio_project.core.exception.Exception500;
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

    // FindAll
    @Transactional(readOnly = true)
    public List<MainIntroduceDTO_Out.FindAllDTO> main_findAll(){
        List<MainIntroduce> mainIntroducesPS = mainIntroduceRepository.findAll();
        
        return MainIntroduceDTO_Out.FindAllDTO.fromEntityList(mainIntroducesPS);
    }

    // POST
    @Transactional
    public MainIntroduceDTO_Out.PostDTO main_post(MainIntroduceDTO_In.PostDTO postDTO_In){
        List<String> nameAndUrl = s3Utils.uploadImageToS3(postDTO_In.getImageData(), postDTO_In.getImageName(), postDTO_In.getContentType(), "main_introduce");
        
        MainIntroduce mainIntroduce = postDTO_In.toEntity();
        mainIntroduce.setIntroduceImgName(nameAndUrl.get(0));
        mainIntroduce.setIntroduceImgUrl(nameAndUrl.get(1));

        mainIntroduceRepository.save(mainIntroduce);
        
        return MainIntroduceDTO_Out.PostDTO.fromEntity(mainIntroduce);
    }

    // PUT
    @Transactional
    public MainIntroduceDTO_Out.PutDTO main_put(MainIntroduceDTO_In.PutDTO putDTO_In){
        MainIntroduce mainIntroducePS = mainIntroduceRepository.findById(putDTO_In.getId()).orElseThrow(() -> {
            throw new Exception400("업데이트하려는 게시물이 존재하지 않습니다.");
        });

        putDTO_In.toEntity(mainIntroducePS);

        if (putDTO_In.getImgChangeCheck()) {
            List<String> nameAndUrl = s3Utils.uploadImageToS3(putDTO_In.getImageData(), 
                                                              putDTO_In.getImageName(), 
                                                              putDTO_In.getContentType(),
                                                              "main_introduce");
            mainIntroducePS.setIntroduceImgName(nameAndUrl.get(0));
            mainIntroducePS.setIntroduceImgUrl(nameAndUrl.get(1));
        }

        return MainIntroduceDTO_Out.PutDTO.fromEntity(mainIntroducePS);
    }

    // DELETE
    @Transactional
    public void main_delete(Long postPK){
        MainIntroduce mainIntroducePS = mainIntroduceRepository.findById(postPK).orElseThrow(() -> {
            throw new Exception400("삭제하려는 게시물이 존재하지 않습니다.");
        });

        try {
            mainIntroduceRepository.delete(mainIntroducePS);
        } catch (Exception e) {
            throw new Exception500("게시물 삭제에 실패하였습니다.");
        }
    }
}
