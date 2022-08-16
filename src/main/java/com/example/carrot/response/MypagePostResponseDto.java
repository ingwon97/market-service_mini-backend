package com.example.carrot.response;

import com.example.carrot.model.Post;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class MypagePostResponseDto {
    List<Post> posts = new ArrayList<>();
//    private Long id;
//    private String title;
//    private String content;
//    private String imageUrl;
//    private Long price;
//    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;
//
//    public MypageResponsePostDto(Member member) {
//        this.id = member.getMember_id();
//    }











}