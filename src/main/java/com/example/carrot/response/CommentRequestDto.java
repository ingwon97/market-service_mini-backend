package com.example.carrot.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CommentRequestDto {

    private String content;
}
