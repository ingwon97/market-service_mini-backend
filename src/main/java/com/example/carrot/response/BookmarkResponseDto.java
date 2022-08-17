package com.example.carrot.response;

import com.example.carrot.model.Bookmark;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BookmarkResponseDto {

    private Long postId;
    private String username;
    private String state;

    public BookmarkResponseDto(Bookmark bookmark, String state){
        this.postId = bookmark.getPost().getId();
        this.username = bookmark.getMember().getUsername();
        this.state = state;
    }
}
