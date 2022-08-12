package com.example.carrot.service;

import com.example.carrot.image.S3UploaderService;
import com.example.carrot.model.Bookmark;
import com.example.carrot.model.Member;
import com.example.carrot.model.Post;
import com.example.carrot.repository.PostRepository;
import com.example.carrot.request.PostRequestDto;
import com.example.carrot.response.PostResponseDto;
import com.example.carrot.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final S3UploaderService s3UploaderService;

    public ResponseDto<?> getPostById(Long postId) {
        Post post = isPresentPost(postId);
        if (post == null) {
            ResponseDto.fail("POST_NOT_FOUND", "게시글이 존재하지 않습니다");
        }

        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .imageUrl(post.getImage_url())
                        .category(post.getCategory())
                        .price(post.getPrice())
                        .build()
        );
    }


    @Transactional
    public ResponseDto<?> createPost(MultipartFile image, PostRequestDto requestDto) throws IOException {

        // Member member = new Member();
        // 멤버를 가지고, 게시글 만들기
        String imageUrl = s3UploaderService.upload(image, "static");

        List<Bookmark> categories = requestDto.getCategory();

        Post post = Post.builder()
                .member(member)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .price(requestDto.getPrice())
                .image_url(imageUrl)
                .category(requestDto.getCategory())
                .build();

        postRepository.save(post);

        return ResponseDto.success(post);
    }

    @Transactional(readOnly = true)
    public Post isPresentPost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        return optionalPost.orElse(null);
    }

    public ResponseDto<?> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return ResponseDto.success(posts);
    }

    @Transactional
    public ResponseDto<?> updatePost(Long postId, PostRequestDto requestDto) {

        // 멤버가 다르다면, 작성자만 수정할 수 있습니다.
        Post post = isPresentPost(postId);
        if (post == null) {
            ResponseDto.fail("POST_NOT_FOUND", "게시글이 존재하지 않습니다");
        }

        post.update(requestDto);
        return ResponseDto.success(post);
    }

    @Transactional
    public ResponseDto<?> updatePost(Long postId, MultipartFile image, PostRequestDto requestDto) throws IOException {

        // 멤버가 다르다면, 작성자만 수정할 수 있습니다.
        Post post = isPresentPost(postId);
        if (post == null) {
            ResponseDto.fail("POST_NOT_FOUND", "게시글이 존재하지 않습니다");
        }

        String imageUrl = s3UploaderService.upload(image, "static");

        post.update(imageUrl, requestDto);
        return ResponseDto.success(post);
    }


    @Transactional
    public ResponseDto<?> deletePost(Long postId) {
        //멤버인지를 인식

        //Post가 있는지 인식
        Post post = isPresentPost(postId);
        if (post == null) {
            ResponseDto.fail("POST_NOT_FOUND", "게시글이 존재하지 않습니다");
        }

        postRepository.delete(post);
        return ResponseDto.success("delete success");
    }

    // 후에는 정규표현식 추가
    public ResponseDto<?> searchPost(String searchKeyword) {
        if (searchKeyword == null) {
            return ResponseDto.fail("DATA_NOT_FOUND", "글자를 입력해 주세요");
        }
        List<Post> byTitleContaining = postRepository.findByTitleContaining(searchKeyword);
        if (byTitleContaining == null) {
            return ResponseDto.fail("SEARCH_NOT_FOUND", "게시글을 찾을 수 없습니다");
        }
        return ResponseDto.success(byTitleContaining);
    }


}
