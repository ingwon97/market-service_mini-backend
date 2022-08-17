package com.example.carrot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

//    @JoinColumn(name = "bookmark_id")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Bookmark bookmark;
    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Bookmark(Post post, Member member){
        this.post = post;
        this.member = member;
    }

}
