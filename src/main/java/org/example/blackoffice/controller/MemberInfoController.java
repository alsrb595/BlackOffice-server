package org.example.blackoffice.controller;


import org.example.blackoffice.dto.MemberInfoDto;
import org.example.blackoffice.model.MemberInfo;
import org.example.blackoffice.service.MemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member_info")
public class MemberInfoController {

    private final MemberInfoService memberInfoService;

    @Autowired
    public MemberInfoController(MemberInfoService memberInfoService) {
        this.memberInfoService = memberInfoService;
    }

    @PostMapping("/save/{id}")
    public MemberInfo saveMemberInfo(@RequestBody MemberInfoDto memberInfo, @PathVariable Long id) {
        return memberInfoService.saveMemberInfo(memberInfo, id);
    }
}