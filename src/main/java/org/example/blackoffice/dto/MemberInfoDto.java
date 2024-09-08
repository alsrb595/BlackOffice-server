package org.example.blackoffice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class MemberInfoDto {
    private String user_name;
    private String user_email;
    private String department;
    private Double height;
    private String introduction;
    private String temperature;
    private String workTool;
}
