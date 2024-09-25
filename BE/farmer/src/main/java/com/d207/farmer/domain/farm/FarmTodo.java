package com.d207.farmer.domain.farm;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "farm_todo")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FarmTodo {

    @Id @GeneratedValue
    @Column(name = "farm_todo_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @Column(name = "farm_todo_type")
    @Enumerated(EnumType.STRING)
    private TodoType todoType;

    @Column(name = "farm_todo_is_completed")
    private Boolean isCompleted;

    @Column(name = "farm_todo_date")
    private LocalDateTime todoDate;

    @Column(name = "farm_todo_complete_date")
    private LocalDateTime completeDate;

    /**
     * 비즈니스 메서드
     */
    public FarmTodo (Farm farm, TodoType todoType, Boolean isCompleted, LocalDateTime todoDate, LocalDateTime completeDate) {
        this.farm = farm;
        this.todoType = todoType;
        this.isCompleted = isCompleted;
        this.todoDate = todoDate;
        this.completeDate = completeDate;
    }

    public void updateTodoComplete() {
        this.isCompleted = true;
        this.completeDate = LocalDateTime.now();
    }
}
