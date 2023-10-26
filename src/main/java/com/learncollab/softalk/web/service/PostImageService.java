package com.learncollab.softalk.web.service;

import com.learncollab.softalk.domain.dto.image.ImageDto;
import com.learncollab.softalk.domain.entity.CmImage;
import com.learncollab.softalk.domain.entity.Post;
import com.learncollab.softalk.domain.entity.PostImage;
import com.learncollab.softalk.web.repository.PostImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final S3UploadService s3UploadService;
    private final PostImageRepository postImageRepository;

    //S3에 업로드
    public List<PostImage> uploadPostImage(List<MultipartFile> multipartFiles, String bucketName, String bucketDirName) {
        List<PostImage> imageList = new ArrayList<>();

        multipartFiles.forEach(multipartFile -> {
            ImageDto imageDto = s3UploadService.uploadFile(multipartFile, bucketName, bucketDirName);
            PostImage image = new PostImage(imageDto);
            imageList.add(image);
        });
        return imageList;
    }

    //DB에 저장
    public void savePostImage(PostImage image, Post post) {
        image = postImageRepository.save(image);
        image.setPost(post);
    }

}
