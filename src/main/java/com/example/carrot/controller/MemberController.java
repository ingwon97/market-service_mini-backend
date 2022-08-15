package com.example.carrot.controller;

import com.example.carrot.request.LoginDto;
import com.example.carrot.request.MemberRequestDto;
import com.example.carrot.response.ResponseDto;
import com.example.carrot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;


    // 회원가입
    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody MemberRequestDto dto) {
        try{
            return memberService.singup(dto);
        } catch (Exception e) {
            return ResponseDto.fail("",e.getMessage());
        }
    }

    // 아이디 중복 체크
    @GetMapping("/check/{username}")
    public ResponseDto<?> checkId(@PathVariable String username) {
        try {
            return memberService.checkId(username);
        } catch (Exception e) {
            return  ResponseDto.fail("Fail_Duplicate_Error", e.getMessage());
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody LoginDto dto ){
        try {
            return  memberService.login(dto);
        } catch (Exception e) {
            return ResponseDto.fail("", e.getMessage());
        }

    }

    //로그아웃
    @GetMapping("/logout")
    public ResponseDto<?> logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return ResponseDto.success(null);
    }
}

