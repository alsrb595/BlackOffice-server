package org.example.blackoffice.controller;

import org.example.blackoffice.dto.MyPageDto;
import org.example.blackoffice.model.MemberInfo;
import org.example.blackoffice.service.MyPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mypage")
public class MyPageController {
    private final MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<MyPageDto> getMyPage(@PathVariable Long userId){
        return ResponseEntity.ok(myPageService.getMyPage(userId));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<MemberInfo> updateMyPage(@PathVariable Long userId, @RequestBody MyPageDto myPageDto){
        return ResponseEntity.ok(myPageService.updateMypage(userId, myPageDto));
    }
}
