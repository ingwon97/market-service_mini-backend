package com.example.carrot.controller;

import com.example.carrot.request.PostRequestDto;
import com.example.carrot.response.ResponseDto;
import com.example.carrot.service.PostService;
import com.example.carrot.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/api/posts/{postId}")
    public ResponseDto<?> getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping("/api/posts")
    public ResponseDto<?> createPost(@RequestParam("image")MultipartFile image,
                                     @RequestParam("dto") PostRequestDto requestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails
                                     ) throws IOException {
        Long memberId = userDetails.getMember().getId();
        return postService.createPost(memberId, image, requestDto);
    }

    // 게시글 조회
    @GetMapping("/api/posts")
    public ResponseDto<?> getAllPosts() {
        return postService.getAllPosts();
    }

    // 게시글 수정
    @PutMapping("/api/posts/{postId}")
    public ResponseDto<?> updatePost(@PathVariable Long id,
                                     @RequestParam("image") MultipartFile image,
                                     @RequestParam("dto") PostRequestDto requestDto) throws IOException {
        //이미지가 없다면
        if (image.isEmpty()) {
            return postService.updatePost(id, requestDto);
        }
        //이미지가 들어왔다면
        return postService.updatePost(id, image, requestDto);
    }

    @DeleteMapping("/api/posts/{postId}")
    public ResponseDto<?> deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }

    @GetMapping("/api/posts/search")
    public void searchPost(@RequestParam String title) {
        postService.searchPost(title);
    }

    /*@GetMapping("/api/posts/category")
    public ResponseDto<?> getPostsByCategory() {
        return postService.getPostsByCategory();
    }

    @PostMapping("/api/posts/category")
    public ResponseDto<?> addPostCategory() {
        return postService.addPostCategory();
    }*/


}
