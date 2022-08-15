package com.example.carrot.controller;

import com.example.carrot.request.LoginDto;
import com.example.carrot.request.LoginRequestDto;
import com.example.carrot.request.MemberRequestDto;
import com.example.carrot.response.ResponseDto;
import com.example.carrot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
//@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;


    //Valid는 유효성 검사
    @RequestMapping(value = "/api/members/signup", method = RequestMethod.POST)
    public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto requestDto) {
        return memberService.createMember(requestDto);
    }

    @RequestMapping(value = "/api/members/login", method = RequestMethod.POST)
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto,
                                HttpServletResponse response
    ) {
        return memberService.login(requestDto, response);
    }

//  @RequestMapping(value = "/api/auth/member/reissue", method = RequestMethod.POST)
//  public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
//    return memberService.reissue(request, response);
//  }

    @RequestMapping(value = "/api/auth/members/logout", method = RequestMethod.POST)
    public ResponseDto<?> logout(HttpServletRequest request) {
        return memberService.logout(request);
    }

    /*@RequestMapping(value = "/api/auth/member/mypage", method = RequestMethod.GET)
    public ResponseDto<?> mypage(HttpServletRequest request) {
        return memberService.mypage(request);
    }


    // 회원가입
    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody MemberRequestDto dto) {
        try{
            return memberService.createMember(dto);
        } catch (Exception e) {
            return ResponseDto.fail("FAIL_SIGNUP_ERROR",e.getMessage());
        }
    }

    // 아이디 중복 체크
    @GetMapping("/check/{username}")
    public ResponseDto<?> checkId(@PathVariable String username) {
        try {
            return memberService.checkId(username);
        } catch (Exception e) {
            return  ResponseDto.fail("FAIL_DUPLICATE_ERROR", e.getMessage());
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody LoginDto dto, HttpServletResponse response ){
        try {
            return  memberService.login(dto, response);
        } catch (Exception e) {
            return ResponseDto.fail("FAIL_LOGININFO_ERROR", e.getMessage());
        }

    }

    //로그아웃
    @GetMapping("/logout")
    public ResponseDto<?> logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return ResponseDto.success(null);
    }

    //회원 정보 조회
    @GetMapping("/info")
    public ResponseDto<?> LoginInfo(@AuthenticationPrincipal UserDetails userInfo) {
        try {
            return  memberService.LoginInfo(userInfo);
        } catch (Exception e) {
            return  ResponseDto.fail("NOT_STATE_LOGIN", e.getMessage());
        }


    }*/

}

