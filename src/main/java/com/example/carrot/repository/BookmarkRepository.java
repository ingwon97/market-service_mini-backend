package com.example.carrot.repository;

import com.example.carrot.model.Bookmark;

import com.example.carrot.model.Member;
import com.example.carrot.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {

    Optional<Bookmark> findByPostAndMember(Post post, Member member);

    void deleteByPostAndMember(Post post, Member member);

    List<Bookmark> findAllByMember(Member member);
}
