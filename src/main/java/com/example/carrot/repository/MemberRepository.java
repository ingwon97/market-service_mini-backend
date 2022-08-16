package com.example.carrot.repository;

<<<<<<< HEAD
import com.example.carrot.model.Comment;
import com.example.carrot.model.Member;
import com.example.carrot.model.Post;
=======
>>>>>>> origin/sunho
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String user);
    Optional<Member> findById(Long id);
    Optional<Member> findByNickname(String nickname);


}
