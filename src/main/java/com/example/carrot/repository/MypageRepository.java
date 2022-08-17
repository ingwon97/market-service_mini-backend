package com.example.carrot.repository;


import com.example.carrot.model.Mypage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MypageRepository extends JpaRepository<Mypage, Long> {
}
