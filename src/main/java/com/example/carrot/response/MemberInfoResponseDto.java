package com.example.carrot.response;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class MemberInfoResponseDto {
    private Long id;
    private String username;
    private String nickname;


}
