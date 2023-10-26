package com.learncollab.softalk.web.service;

import com.learncollab.softalk.domain.dto.image.ImageDto;
import com.learncollab.softalk.domain.entity.Post;
import com.learncollab.softalk.domain.entity.PostImage;
import com.learncollab.softalk.web.repository.PostImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final S3UploadService s3UploadService;
    private final PostImageRepository postImageRepository;

    //이미지 등록 - S3에 업로드
    public List<PostImage> uploadPostImage(List<MultipartFile> multipartFiles, String bucketName, String bucketDirName) {
        List<PostImage> imageList = new ArrayList<>();

        multipartFiles.forEach(multipartFile -> {
            ImageDto imageDto = s3UploadService.uploadFile(multipartFile, bucketName, bucketDirName);
            PostImage image = new PostImage(imageDto);
            imageList.add(image);
        });
        return imageList;
    }

    //이미지 등록 - DB에 저장
    @Transactional
    public void savePostImage(PostImage image, Post post) {
        image = postImageRepository.save(image);
        image.setPost(post);
    }

    //이미지 삭제
    public void deletePostImage(String bucketName, List<PostImage> images, Long postId) {
        images.stream()
                .forEach(image -> s3UploadService.deleteFile(bucketName, image.getS3key()));

        postImageRepository.deleteByPostId(postId);
    }

}
