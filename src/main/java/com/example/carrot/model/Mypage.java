package com.example.carrot.model;

import com.example.carrot.request.MypageRequestDto;
import com.example.carrot.request.PostRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mypage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String image_url;

    @JsonIgnore
    @JoinColumn(name = "member_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    public void update(String image_url, MypageRequestDto requestDto) {
        this.image_url = image_url;
    }


    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }


}
