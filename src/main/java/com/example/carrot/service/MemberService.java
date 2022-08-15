package com.example.carrot.service;

import com.example.carrot.model.Member;
import com.example.carrot.model.MemberDetailsImpl;
import com.example.carrot.repository.MemberRepository;
import com.example.carrot.request.LoginDto;
import com.example.carrot.request.MemberRequestDto;
import com.example.carrot.response.MemberInfoResponseDto;
import com.example.carrot.response.MemberResponseDto;
import com.example.carrot.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
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
            return ResponseDto.fail("Fail_Login_Error", "로그인 정보를 확인해주세요");
        }

        return ResponseDto.success(dto);

    }

    public ResponseDto<?> checkId(String username) {
        Member member = memberRepository.findByUsername(username).orElse(null);

        if(member != null) {
            throw new DuplicateKeyException("아이디가 중복됩니다.");
        }

        return ResponseDto.success(username);
    }

    public ResponseDto<?> LoginInfo(UserDetails userInfo) {
        Member member = memberRepository.findByUsername(userInfo.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("로그인 상태가 아닙니다.")
        );

        MemberInfoResponseDto memberInfoResponseDto = MemberInfoResponseDto.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .build();

        return ResponseDto.success(memberInfoResponseDto);

    }
}
