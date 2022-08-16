package com.example.carrot.service;

import com.example.carrot.model.Comment;
import com.example.carrot.model.Member;
import com.example.carrot.model.Post;
import com.example.carrot.repository.CommentRepository;
import com.example.carrot.repository.MemberRepository;
import com.example.carrot.repository.PostRepository;
import com.example.carrot.response.CommentResponseDto;
import com.example.carrot.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseDto<?> createComment(Long postId, UserDetails details, String content) {
        Post post = getPost(postId);
        Member member = getMember(details);

        Comment comment = new Comment(post, member, content);
        Comment savedComment = commentRepository.save(comment);
        return ResponseDto.success(new CommentResponseDto(savedComment));
    }

    public ResponseDto<?> findAllComment(Long postId) {
        Post post = getPost(postId);
        List<CommentResponseDto> responseDtoList =
                commentRepository.findAllByPost(post).stream().map(CommentResponseDto::new).collect(Collectors.toList());
        return ResponseDto.success(responseDtoList);
    }

    @Transactional
    public ResponseDto<?> updateComment(Long commentId, UserDetails details, String content) {
        Member member = getMember(details);
        Comment findComment = getComment(commentId);
        validateAuthor(member, findComment);

        findComment.update(content);
        return ResponseDto.success(null);
    }

    @Transactional
    public ResponseDto<?> deleteComment(Long commentId, UserDetails details) {
        Member member = getMember(details);
        Comment findComment = getComment(commentId);
        validateAuthor(member, findComment);

        commentRepository.delete(findComment);
        return ResponseDto.success(null);
    }

    private static void validateAuthor(Member member, Comment findComment) {
        if (!Objects.equals(findComment.getMember().getMember_id(), member.getMember_id())) {
            throw new IllegalArgumentException("다른 사람의 댓글은 삭제할 수 없습니다");
        }
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시글 입니다"));
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("없는 댓글 입니다"));
    }

    private Member getMember(UserDetails details) {
        if (details == null) {
            throw new IllegalArgumentException("로그인 정보가 없습니다");
        }
        return memberRepository.findByUsername(details.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("없는 회원 입니다"));
    }
}
