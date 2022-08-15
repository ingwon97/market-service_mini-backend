package com.example.carrot.response;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class MemberInfoResponseDto {
    private String username;
    private String nickname;


}
