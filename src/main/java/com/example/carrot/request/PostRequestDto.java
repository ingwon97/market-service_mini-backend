package com.example.carrot.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PostRequestDto {

    private MultipartFile image;
    private String title;
    private String content;
    private String category;
    private Long price;

}
