package com.example.carrot.response;


import com.example.carrot.model.Member;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
public class MemberResponseDto {


    private String username;
    private String nickname;
    private String profile;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MemberResponseDto(Member member){
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.profile = member.getProfile();
        this.createdAt = member.getCreatedAt();
        this.modifiedAt = member.getModifiedAt();
    }

}
