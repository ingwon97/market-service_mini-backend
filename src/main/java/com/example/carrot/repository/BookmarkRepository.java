package com.example.carrot.repository;

import com.example.carrot.model.Bookmark;
import com.example.carrot.model.Comment;
import com.example.carrot.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findAllByMember(Member member);
}
