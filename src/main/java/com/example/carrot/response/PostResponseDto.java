package com.example.carrot.response;

import com.example.carrot.model.Bookmark;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
// 게시글 조회시
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
//    private List<Bookmark> category = new ArrayList<>();
    private Long price;
    // 작성자 추가해줘야 되는거 아닌가?
     private String nickname;

    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;


}
