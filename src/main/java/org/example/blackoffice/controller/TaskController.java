package org.example.blackoffice.controller;

import org.example.blackoffice.dto.Taskdto;
import org.example.blackoffice.exception.ResourceNotFoundException;
import org.example.blackoffice.model.Member;
import org.example.blackoffice.model.Task;
import org.example.blackoffice.service.MemberService;
import org.example.blackoffice.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final MemberService memberService;

    public TaskController(TaskService taskService, MemberService memberService) {
        this.taskService = taskService;
        this.memberService = memberService;
    }



    // 모든 Task 조회
    @GetMapping("/{memberId}")
    public List<Taskdto> getTasks(@PathVariable Long memberId) {
        return taskService.getAllTasks(memberId);
    }

    // Task 생성 (토큰을 통해 인증된 사용자로 생성)
    @PostMapping
    public Taskdto createTask(@RequestBody Task task) {
        // 현재 인증된 사용자를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // 이메일 추출 (이메일이 인증 정보에 있는 경우)

        // 이메일을 통해 Member를 찾음
        Member member = memberService.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        // Task와 Member 연결
        task.setMember(member);  // Task에 해당 사용자 정보 설정 (member_id는 자동으로 처리됨)

        return taskService.createTask(task);
    }

    // Task 업데이트
    @PutMapping("/{id}")
    public Taskdto updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    // Task 삭제
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
