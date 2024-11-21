package org.example.blackoffice.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Taskdto {
    private Long id;
    private String taskName;
    private String dueDate;  // 마감 날짜
    private String status;   // 상태 (완료, 미완료, 마감)

}
