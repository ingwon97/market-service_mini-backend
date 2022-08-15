package com.example.carrot.controller;

import com.example.carrot.model.MemberDetailsImpl;
import com.example.carrot.request.PostRequestDto;
import com.example.carrot.response.ResponseDto;
import com.example.carrot.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/api/posts/{postId}")
    public ResponseDto<?> getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping("/api/auth/posts")
    public ResponseDto<?> createPost(@RequestParam("image") MultipartFile image,
                                     @ModelAttribute PostRequestDto requestDto,
                                     HttpServletRequest request
                                     ) throws IOException {
        return postService.createPost(image, requestDto, request);
    }

    // 게시글 조회
    @GetMapping("/api/posts")
    public ResponseDto<?> getAllPosts() {
        return postService.getAllPosts();
    }

    // 게시글 수정
    @PutMapping("/api/auth/posts/{postId}")
    public ResponseDto<?> updatePost(@PathVariable Long postId,
                                     @RequestParam("image") MultipartFile image,
                                     @ModelAttribute PostRequestDto requestDto,
                                     HttpServletRequest request
                                     ) throws IOException {
        //이미지가 없다면
        if (image.isEmpty()) {
            return postService.updatePost(postId, requestDto,request);
        }
        //이미지가 들어왔다면
        return postService.updatePost(postId, image, requestDto, request);
    }

    @DeleteMapping("/api/auth/posts/{postId}")
    public ResponseDto<?> deletePost(@PathVariable Long postId, HttpServletRequest request) {
        return postService.deletePost(postId, request);
    }

    @GetMapping("/api/posts/search")
    public ResponseDto<?> searchPost(@RequestParam String title) {
        return postService.searchPost(title);
    }

    @GetMapping("/api/posts/category")
    public ResponseDto<?> getPostsByCategory(@RequestParam("category") String category) {
        return postService.getPostsByCategory(category);
    }

    /*@PostMapping("/api/posts/category")
    public ResponseDto<?> addPostCategory(@RequestBody String category) {
        return postService.addPostCategory(category);
    }*/


}
