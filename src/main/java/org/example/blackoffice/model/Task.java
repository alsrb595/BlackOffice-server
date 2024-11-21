package org.example.blackoffice.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Entity
@NoArgsConstructor(access= AccessLevel.PUBLIC)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Task name cannot be blank")
    private String taskName;

    @NotNull(message = "Due date cannot be null")
    private LocalDate dueDate;

    @NotNull(message = "Status cannot be null")
    private String status;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
