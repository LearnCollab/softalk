package com.learncollab.softalk.domain.entity;

import com.learncollab.softalk.domain.dto.image.ImageDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class PostImage {

    @Id
    @GeneratedValue
    @Column(name = "post_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String s3key;
    private String imageUrl;

    public PostImage(ImageDto imageDto) {
        this.s3key = imageDto.getS3key();
        this.imageUrl = imageDto.getImageUrl();
    }

    // 게시글 정보 저장
    public void setPost(Post post){
        this.post = post;

        // 게시글에 현재 파일이 존재하지 않는다면
        post.initializeImageList();

        if(!post.getImages().contains(this)){
            post.getImages().add(this);
        }
    }

}
