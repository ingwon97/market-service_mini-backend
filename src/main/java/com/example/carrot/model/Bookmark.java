package com.example.carrot.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "bookmark_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Bookmark bookmark;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}
