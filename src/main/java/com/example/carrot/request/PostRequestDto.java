package com.example.carrot.request;

import com.example.carrot.model.Bookmark;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostRequestDto {

    private String title;
    private String content;
    private List<Bookmark> category = new ArrayList<>();
    private String price;
}
