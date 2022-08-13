package com.example.carrot.service;

import com.example.carrot.model.Member;
import com.example.carrot.model.MemberDetailsImpl;
import com.example.carrot.repository.MemberRepository;
import com.example.carrot.request.LoginDto;
import com.example.carrot.request.MemberRequestDto;
import com.example.carrot.response.MemberResponseDto;
import com.example.carrot.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public ResponseDto<?> singup(MemberRequestDto memberRequestDto) {

        Member member = Member.builder()
                .username(memberRequestDto.getUsername())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .nickname(memberRequestDto.getNickname())
                .build();


        member = memberRepository.save(member);
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
        System.out.println(memberResponseDto);

        return ResponseDto.success(memberResponseDto);
    }


    public ResponseDto<?> login(LoginDto dto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());

        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            return ResponseDto.fail("Fail_LOGIN_ERROR", "로그인 정보를 확인해주세요");
        }

        return ResponseDto.success(dto);

    }

}
