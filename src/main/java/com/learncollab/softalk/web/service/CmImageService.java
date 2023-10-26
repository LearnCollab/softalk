package com.learncollab.softalk.web.service;

import com.learncollab.softalk.domain.dto.image.ImageDto;
import com.learncollab.softalk.domain.entity.CmImage;
import com.learncollab.softalk.domain.entity.Community;
import com.learncollab.softalk.web.repository.CmImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CmImageService {
    private final CmImageRepository cmImageRepository;
    private final S3UploadService s3UploadService;

    /*이미지 파일을 S3에 저장*/
    public List<CmImage> uploadCmImage(List<MultipartFile> multipartFiles, String bucketName, String bucketDirName){
        List<CmImage> imageList = new ArrayList<>();

        /*
         * S3에 이미지 저장 후 ImageDto 반환 받고
         * ImageDto 이용해서 PostImage 객체 생성
         * PostImage 객체 리스트 반환
         * */
        multipartFiles.forEach(multipartFile -> {
            ImageDto imageDto = s3UploadService.uploadFile(multipartFile, bucketName, bucketDirName);
            CmImage image = new CmImage(imageDto);
            imageList.add(image);
        });
        return imageList;
    }


    /*이미지 객체와 게시글 객체 양방향 연결 후
     * 이미지 객체 저장
     * */
    public void saveCmImage(CmImage image, Community commuiity){
        image = cmImageRepository.save(image);
        image.setCommunity(commuiity);
    }

    public void deleteCmImage(String bucketName, String keyName){
        s3UploadService.deleteFile(bucketName, keyName);
    }

}