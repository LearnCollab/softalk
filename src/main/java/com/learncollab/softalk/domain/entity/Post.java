package com.learncollab.softalk.domain.entity;

import com.learncollab.softalk.domain.dto.post.PostReqDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "posts")
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false)
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member writer;

    private String title;

    @Column(nullable = false)
    private String content;


    // 게시글 제목 및 내용 수정 메소드
    public void updatePost(PostReqDto request){
        String title = request.getTitle();
        this.title = (title != null && !title.trim().isEmpty()) ? title.trim() : "제목없음";
        this.content = request.getContent();
    }

}
