package com.example.carrot.controller;

import com.example.carrot.request.CommentRequestDto;
import com.example.carrot.response.ResponseDto;
import com.example.carrot.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/auth/comments/{postId}")
    public ResponseDto<?> createComment(@PathVariable Long postId,
                                        @RequestBody CommentRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.createComment(postId, requestDto, userDetails);
    }

    @GetMapping("/api/comments/{postId}")
    public ResponseDto<?> findAllComment(@PathVariable Long postId) {
        return commentService.findAllComment(postId);
    }

    @PutMapping("/api/auth/comments/{commentId}")
    public ResponseDto<?> updateComment(@PathVariable Long commentId,
                                        @RequestBody CommentRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.updateComment(commentId, requestDto, userDetails);
    }

    @DeleteMapping("/api/auth/comments/{commentId}")
    public ResponseDto<?> deleteComment(@PathVariable Long commentId,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.deleteComment(commentId, userDetails);
    }
}
