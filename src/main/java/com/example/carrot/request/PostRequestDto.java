package com.example.carrot.request;

import com.example.carrot.model.Bookmark;
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
//    private List<Bookmark> category = new ArrayList<>();
    private Long price;
}
