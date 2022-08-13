package com.example.carrot.model;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;
}
