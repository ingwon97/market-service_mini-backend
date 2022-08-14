package com.example.carrot.request;

import com.example.carrot.model.Bookmark;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostRequestDto {

    private String title;
    private String content;
    private String category;
    private Long price;

    public PostRequestDto(String title, String content, Long price) {
        this.title = title;
        this.content = content;
        this.price = price;
    }
}
