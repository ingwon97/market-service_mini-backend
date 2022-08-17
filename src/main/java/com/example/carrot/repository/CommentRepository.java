package com.example.carrot.repository;

import com.example.carrot.model.Comment;
import com.example.carrot.model.Member;
import com.example.carrot.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);

    List<Comment> findAllByMember(Member member);
}
