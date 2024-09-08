package org.example.blackoffice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CommentCreateDto {
    private Long postId;
    private String content;
    private String writer;
}
