package org.example.blackoffice.controller;

import org.example.blackoffice.dto.CommentCreateDto;
import org.example.blackoffice.dto.Memberdto;
import org.example.blackoffice.dto.PostCreateDto;
import org.example.blackoffice.model.Comments;
import org.example.blackoffice.model.Posts;
import org.example.blackoffice.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Posts>> getAllPosts() { // ResponseEntity<List<Posts>>는 스프링 프레임워크에서 HTTP 응답(Response)을 캡슐화하는 클래스인 ResponseEntity를 사용하여, HTTP 상태 코드와 응답 본문(Body)을 함께 전송하는 역할
        List<Posts> posts = communityService.getAllPosts(); // 엔티티 객체들의 List임
        return ResponseEntity.ok(posts); // 200 ok를 보내고 posts의 내용도 json으로 변환하여 client 사이드로 전달
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Posts> getPostById(@PathVariable Long id) {
        Posts post = communityService.getPostById(id);
        if (post == null) {
            return ResponseEntity.notFound().build();  // 404 Not Found 반환
        }
        return ResponseEntity.ok(post);
    }

    @PostMapping("/post/save")
    public ResponseEntity<Posts> savePost(@RequestBody PostCreateDto postData) {
        Posts post = communityService.savePost(postData);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/comment/save")
    public ResponseEntity<Comments> saveComment(@RequestBody CommentCreateDto commentData) {
        return ResponseEntity.ok(communityService.saveComment(commentData));
    }

}
