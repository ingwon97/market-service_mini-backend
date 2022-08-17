package com.example.carrot.response;

import com.example.carrot.model.Comment;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class MypageCommentResponseDto {
    List<Comment> comments = new ArrayList<>();
//    private String content;
//    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;
//
//    public MypageResponseCommentDto(Member member) {
//        this.content = member.getNickname();
//        this.createdAt = member.getCreatedAt();
//        this.modifiedAt = member.getModifiedAt();
//    }
}