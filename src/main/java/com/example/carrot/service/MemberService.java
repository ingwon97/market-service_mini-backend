package com.example.carrot.service;

import com.example.carrot.model.Member;
import com.example.carrot.repository.MemberRepository;
import com.example.carrot.request.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();

        Optional<Member> found = memberRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID가 존재합니다");
        }

        // 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());

        Member member = new Member(requestDto.getNickname(), requestDto.getUsername(), password);
        memberRepository.save(member);
    }
}