package org.example.blackoffice.service;

import org.example.blackoffice.dto.MemberInfoDto;
import org.example.blackoffice.dto.MyPageDto;
import org.example.blackoffice.model.Member;
import org.example.blackoffice.model.MemberInfo;
import org.example.blackoffice.repository.MemberInfoRepository;
import org.example.blackoffice.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {
    private final MemberRepository memberRepository;
    private final MemberInfoRepository memberInfoRepository;

    public MyPageService(MemberRepository memberRepository, MemberInfoRepository memberInfoRepository) {
        this.memberInfoRepository = memberInfoRepository;
        this.memberRepository = memberRepository;
    }

    public MyPageDto getMyPage(Long userId) {
        MemberInfo memberInfo = memberInfoRepository.findByMemberId(userId).orElse(null);

        MyPageDto myPageDto = new MyPageDto(memberInfo);

        return  myPageDto;
    }

    public MemberInfo updateMypage(Long userId, MyPageDto myPageDto) {
        MemberInfo memberInfo = memberInfoRepository.findByMemberId(userId).orElse(null);

        memberInfo.setUser_name(myPageDto.getName());
        memberInfo.setUser_email(myPageDto.getEmail());
        memberInfo.setHeight(myPageDto.getHeight());
        memberInfo.setTemperature(myPageDto.getTemperature());
        memberInfo.setWorkTool(myPageDto.getWorkTool());

        // 변경된 memberInfo를 저장
        return memberInfoRepository.save(memberInfo);
    }
}
