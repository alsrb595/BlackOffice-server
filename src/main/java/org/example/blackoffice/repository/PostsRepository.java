package org.example.blackoffice.repository;

import org.example.blackoffice.model.Posts;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {
    @EntityGraph(attributePaths = {"comments"})
    // Posts 엔티티를 조회할 떄 comments 필드도 함께 fetch하도록 설정하는 것
    List<Posts> findAllByOrderByCreatedAtDesc();

    @EntityGraph(attributePaths = {"comments"})
    Optional<Posts> findById(Long id);
}
