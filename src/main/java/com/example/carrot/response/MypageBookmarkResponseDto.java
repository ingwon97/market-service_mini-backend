package com.example.carrot.response;

import com.example.carrot.model.Bookmark;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class MypageBookmarkResponseDto {
    List<Bookmark> bookmarks = new ArrayList<>();
}
