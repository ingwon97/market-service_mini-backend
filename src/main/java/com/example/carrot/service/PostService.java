package com.example.carrot.service;

import com.example.carrot.image.S3UploaderService;
import com.example.carrot.model.Member;
import com.example.carrot.model.Post;
import com.example.carrot.repository.MemberRepository;
import com.example.carrot.repository.PostRepository;
import com.example.carrot.request.PostRequestDto;
import com.example.carrot.response.PostResponseDto;
import com.example.carrot.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final S3UploaderService s3UploaderService;
    private final MemberRepository memberRepository;

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
                        .nickname(post.getNickname())
                        .imageUrl(post.getImage_url())
                        .category(post.getCategory())
                        .price(post.getPrice())
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> createPost(Long memberId, MultipartFile image, PostRequestDto requestDto) throws IOException {


        // 멤버를 가지고, 게시글 만들기
        Member member = memberRepository.findById(memberId).get();
        String imageUrl = s3UploaderService.upload(image, "static");

        Post post = Post.builder()
                .member(member)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .price(requestDto.getPrice())
                .nickname(member.getNickname())
                .image_url(imageUrl)
                .category(requestDto.getCategory())
                .build();

        postRepository.save(post);

        return ResponseDto.success(post);
    }

    public ResponseDto<?> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return ResponseDto.success(posts);
    }

    @Transactional
    public ResponseDto<?> updatePost(Long memberId, Long postId, PostRequestDto requestDto) {

        // 멤버를 가지고 오기
        Member member = memberRepository.findById(memberId).get();

        // 멤버가 다르다면, 작성자만 수정할 수 있습니다.
        Post post = isPresentPost(postId);
        if (post == null) {
            return ResponseDto.fail("POST_NOT_FOUND", "게시글이 존재하지 않습니다");
        }
        Post postByMemberAndId = postRepository.findByMemberAndId(member, postId).orElse(null);

        if (postByMemberAndId == null) {
            return ResponseDto.fail("POST_BY_MEMBER_NOT_FOUND", "해당 사용자의 게시글이 존재하지 않습니다");
        }

        postByMemberAndId.update(requestDto);
        return ResponseDto.success(postByMemberAndId);
    }

    @Transactional
    public ResponseDto<?> updatePost(Long memberId, Long postId, MultipartFile image, PostRequestDto requestDto) throws IOException {

        // 멤버를 가지고 오기
        Member member = memberRepository.findById(memberId).get();
        // 멤버가 다르다면, 작성자만 수정할 수 있습니다.
        Post post = isPresentPost(postId);

        if (post == null) {
            return ResponseDto.fail("POST_NOT_FOUND", "게시글이 존재하지 않습니다");
        }
        Post postByMemberAndId = postRepository.findByMemberAndId(member, postId).orElse(null);

        if (postByMemberAndId == null) {
            return ResponseDto.fail("POST_BY_MEMBER_NOT_FOUND", "해당 사용자의 게시글이 존재하지 않습니다");
        }

//        String imageUrl = s3UploaderService.upload(image, "static");
        String imageUrl = null;

        postByMemberAndId.update(imageUrl, requestDto);
        return ResponseDto.success(postByMemberAndId);
    }

    @Transactional
    public ResponseDto<?> deletePost(Long memberId, Long postId) {

        Member member = memberRepository.findById(memberId).get();
        // 멤버와 PostId를 가지고 게시글을 가지고오기
        Post post = isPresentPost(postId);
        if (post == null) {
            return ResponseDto.fail("POST_NOT_FOUND", "게시글이 존재하지 않습니다");
        }

        // 멤버가 다르다면, 작성자만 삭제할 수 있습니다.
        Post postByMemberAndId = postRepository.findByMemberAndId(member, postId).orElse(null);
        if (postByMemberAndId == null) {
            return ResponseDto.fail("POST_BY_MEMBER_NOT_FOUND", "해당 사용자의 게시글이 존재하지 않습니다");
        }

        String imageUrl = postByMemberAndId.getImage_url();
        String deleteUrl = imageUrl.substring(imageUrl.indexOf("static"));

        s3UploaderService.deleteImage(deleteUrl);
//        s3UploaderService.deleteImage("test.png");
        postRepository.delete(postByMemberAndId);
        return ResponseDto.success("delete success");
    }


    // 후에는 정규표현식 추가
    public ResponseDto<?> searchPost(String searchKeyword) {
        if (searchKeyword == null) {
            return ResponseDto.fail("DATA_NOT_FOUND", "글자를 입력해 주세요");
        }
        List<Post> findPosts = postRepository.findAllByTitleContaining(searchKeyword);
        if (findPosts == null) {
            return ResponseDto.fail("SEARCH_NOT_FOUND", "게시글을 찾을 수 없습니다");
        }
        return ResponseDto.success(findPosts);
    }

    @Transactional(readOnly = true)
    public Post isPresentPost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        return optionalPost.orElse(null);
    }

    public ResponseDto<?> getPostsByCategory(String category) {

        List<Post> allPostsByCategory = postRepository.findAllByCategory(category);
        return ResponseDto.success(allPostsByCategory);
    }
}
