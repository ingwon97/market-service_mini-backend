package com.example.carrot.controller;

import com.example.carrot.response.ResponseDto;
import com.example.carrot.service.MypageService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MypageComtroller {


    private final MypageService mypageService;

    public MypageComtroller(MypageService mypageService) {
        this.mypageService = mypageService;
    }


    @RequestMapping(value = "/api/auth/mypage/posts", method = RequestMethod.GET)
    public ResponseDto<?> mypagePost(HttpServletRequest request) {
        return mypageService.mypageAllPost(request);
    }


    @RequestMapping(value = "/api/auth/mypage/comments", method = RequestMethod.GET)
    public ResponseDto<?> mypageComment(HttpServletRequest request) {
        return mypageService.mypageAllComment(request);
    }

    @RequestMapping(value = "/api/auth/mypage/bookmarks", method = RequestMethod.GET)
    public ResponseDto<?> mypageBookmark(HttpServletRequest request) {
        return mypageService.mypageAllBookmark(request);
    }

}
