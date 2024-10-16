package org.example.blackoffice.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.blackoffice.model.Comments;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private Long commentId;
    private String writer;
    private String content;
    private LocalDateTime createdAt;

    // Comments 엔티티를 받아서 DTO로 변환하는 생성자
    public CommentDto(Comments comment) {
        this.commentId = comment.getCommentId();
        this.writer = comment.getWriter();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
