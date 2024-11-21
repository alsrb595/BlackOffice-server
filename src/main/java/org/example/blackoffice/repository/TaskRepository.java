package org.example.blackoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.blackoffice.model.Task;
import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByMemberId(Long memberId);
}
