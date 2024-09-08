package org.example.blackoffice.service;

import org.example.blackoffice.dto.MemberInfoDto;
import org.example.blackoffice.model.Member;
import org.example.blackoffice.model.MemberInfo;
import org.example.blackoffice.repository.MemberInfoRepository;
import org.example.blackoffice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberInfoService {
    private final MemberInfoRepository memberInfoRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public MemberInfoService(MemberInfoRepository memberInfoRepository, MemberRepository memberRepository) {
        this.memberInfoRepository = memberInfoRepository;
        this.memberRepository = memberRepository;
    }

    public MemberInfo saveMemberInfo(MemberInfoDto memberInfoData, Long id) {
        Member member = memberRepository.findById(String.format("%d", id)).orElseThrow(() -> new RuntimeException("Member not found"));
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMember(member);

        memberInfo.setId(id);
        memberInfo.setDepartment(memberInfoData.getDepartment());
        memberInfo.setHeight(memberInfoData.getHeight());
        memberInfo.setTemperature(memberInfoData.getTemperature());
        memberInfo.setIntroduction(memberInfoData.getIntroduction());
        memberInfo.setUser_email(memberInfoData.getUser_email());
        memberInfo.setUser_name(memberInfoData.getUser_name());
        memberInfo.setWorkTool(memberInfoData.getWorkTool());

        return memberInfoRepository.save(memberInfo);
    }
}
