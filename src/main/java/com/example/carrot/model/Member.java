package com.example.carrot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String profile;

    public Member(String nickname, String username, String password) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
    }


}
