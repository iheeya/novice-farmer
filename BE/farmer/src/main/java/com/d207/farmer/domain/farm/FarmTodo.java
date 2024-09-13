package com.d207.farmer.domain.farm;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "farm_todo")
public class FarmTodo {

    @Id @GeneratedValue
    @Column(name = "farm_todo_id")
    private Long id;

    @Column(name = "farm_todo_type")
    @Enumerated(EnumType.STRING)
    private TodoType todoType;

    @Column(name = "farm_todo_is_completed")
    private Boolean isCompleted;

    @Column(name = "farm_todo_date")
    private LocalDateTime todoDate;

    @Column(name = "farm_todo_complete_date")
    private LocalDateTime completeDate;
}
