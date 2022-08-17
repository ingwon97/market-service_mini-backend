package com.example.carrot.service;

import com.example.carrot.jwt.TokenProvider;
import com.example.carrot.model.Member;
import com.example.carrot.repository.MemberRepository;
import com.example.carrot.request.LoginDto;
import com.example.carrot.request.MemberRequestDto;
import com.example.carrot.request.TokenDto;
import com.example.carrot.response.MemberInfoResponseDto;
import com.example.carrot.response.MemberResponseDto;
import com.example.carrot.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

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


    public ResponseDto<?> login(LoginDto dto, HttpServletResponse response) {
        Member member = memberRepository.findByUsername(dto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        if (!member.validatePassword(passwordEncoder, dto.getPassword())) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);


        return ResponseDto.success(
                MemberInfoResponseDto.builder()
                        .id(member.getMember_id())
                        .username(member.getUsername())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );

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
                .id(member.getMember_id())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .build();

        return ResponseDto.success(memberInfoResponseDto);

    }

    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }
}
