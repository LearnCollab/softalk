package com.learncollab.softalk.domain.entity;

import com.learncollab.softalk.domain.dto.image.ImageDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CmImage {
    @Id
    @GeneratedValue
    @Column(name = "cmImage_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    private String s3key;
    private String imageUrl;

    public CmImage(ImageDto imageDto) {
        this.s3key = imageDto.getS3key();
        this.imageUrl = imageDto.getImageUrl();
    }

    // 커뮤니티 정보 저장
    public void setCommunity(Community community){
        this.community = community;

        // 커뮤니티에 현재 파일이 존재하지 않는다면
        if(!community.getImage().contains(this)){
            // 파일 추가
            community.getImage().add(this);
        }
    }

}
