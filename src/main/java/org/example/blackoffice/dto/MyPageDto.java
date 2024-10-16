package org.example.blackoffice.dto;

import lombok.*;
import org.example.blackoffice.model.MemberInfo;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageDto {
    private String name;
    private String email;
    private Double height;
    private String temperature;
    private String workTool;

    public MyPageDto(MemberInfo memberInfo) {
        this.name = memberInfo.getUser_name();
        this.email = memberInfo.getUser_email();
        this.height = memberInfo.getHeight();
        this.temperature = memberInfo.getTemperature();
        this.workTool = memberInfo.getWorkTool();
    }
}
