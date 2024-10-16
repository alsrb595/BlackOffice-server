package org.example.blackoffice.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.blackoffice.model.Comments;
import org.example.blackoffice.model.Member;
import org.example.blackoffice.model.Posts;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostGetDto {
    private Long postId;
    private String writer;
    private Long memberId;
    private String content;
    private LocalDateTime createdAt;
    private List<CommentDto> comments;

    // 생성자, getter/setter 생략

    public PostGetDto(Posts post) {
        this.postId = post.getPostId();
        this.writer = post.getWriter();
        this.memberId = post.getMember().getId();  // Member 엔티티의 id 필드 가져오기
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.comments = post.getComments().stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());
    }
}
