package org.example.blackoffice.service;

import jakarta.transaction.Transactional;
import org.example.blackoffice.dto.CommentCreateDto;
import org.example.blackoffice.dto.Memberdto;
import org.example.blackoffice.dto.PostCreateDto;
import org.example.blackoffice.model.Comments;
import org.example.blackoffice.model.Posts;
import org.example.blackoffice.repository.CommentsRepository;
import org.example.blackoffice.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {
    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;

    @Autowired
    public CommunityService(PostsRepository postsRepository, CommentsRepository commentsRepository) {
        this.postsRepository = postsRepository;
        this.commentsRepository = commentsRepository;
    }

    public List<Posts> getAllPosts() {
        return postsRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional
    public Posts getPostById(Long id) {
        return postsRepository.findById(id).orElse(null);
    }

    @Transactional
    public Posts savePost(PostCreateDto postData){
        Posts post = new Posts();
        post.setWriter(postData.getWriter());
        post.setContent(postData.getContent());
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

}
