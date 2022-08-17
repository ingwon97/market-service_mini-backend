package com.example.carrot.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class MemberInfoResponseDto {
    private Long id;
    private String username;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;



}
