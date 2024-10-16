package org.example.blackoffice.service;

import jakarta.transaction.Transactional;
import org.example.blackoffice.dto.CommentCreateDto;
import org.example.blackoffice.dto.Memberdto;
import org.example.blackoffice.dto.PostCreateDto;
import org.example.blackoffice.dto.PostGetDto;
import org.example.blackoffice.model.Comments;
import org.example.blackoffice.model.Member;
import org.example.blackoffice.model.Posts;
import org.example.blackoffice.repository.CommentsRepository;
import org.example.blackoffice.repository.MemberRepository;
import org.example.blackoffice.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityService {
    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public CommunityService(PostsRepository postsRepository, CommentsRepository commentsRepository, MemberRepository memberRepository) {
        this.postsRepository = postsRepository;
        this.commentsRepository = commentsRepository;
        this.memberRepository = memberRepository;
    }

    public List<PostGetDto> getAllPosts() {
        List<Posts> posts = postsRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream()
                .map(PostGetDto::new)  // Posts 엔티티를 PostGetDto로 변환
                .collect(Collectors.toList());
    }

    @Transactional
    public Posts getPostById(Long id) {

        Posts post = postsRepository.findById(id).orElse(null);
        String email = post.getMember().getEmail();
        System.out.println(email);
        return post;
    }
    @Transactional
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Transactional
    public Posts savePost(PostCreateDto postData){
        Posts post = new Posts();
        Member member = getMemberById(postData.getUserId());
        post.setWriter(postData.getWriter());
        post.setContent(postData.getContent());
        post.setMember(member);
        return postsRepository.save(post);
    }

    public Comments saveComment(CommentCreateDto commentData){
        Posts post = getPostById(commentData.getPostId());
        Comments comment = new Comments();
        comment.setContent(commentData.getContent());
        comment.setWriter(commentData.getWriter());
        comment.setPost(post);
        return commentsRepository.save(comment);
    }

//    public List<PostGetDto> getAll(){
//        List<Posts> postsList = postsRepository.findAll();  // 한 번의 DB 조회로 모든 데이터를 가져옴
//
//        // Posts 엔티티를 PostGetDto로 변환
//        List<PostGetDto> postGetDtoList = postsList.stream().map(post -> {
//            // Lazy Loading으로 인해 null일 수 있으므로 null 체크
//            Long userId = (post.getMember() != null) ? post.getMember().getId() : null;
//
//            // Lazy Loading으로 인해 comments 필드도 초기화 필요
//            List<Comments> comments = (post.getComments() != null) ? post.getComments() : new ArrayList<>();
//
//            return new PostGetDto(
//                    post.getPostId(),
//                    post.getWriter(),
//                    userId,  // Member ID 설정
//                    post.getContent(),
//                    post.getCreatedAt(),
//                    comments  // Comments 필드 전달
//            );
//        }).collect(Collectors.toList());
//
//        return postGetDtoList;
//    }

}
