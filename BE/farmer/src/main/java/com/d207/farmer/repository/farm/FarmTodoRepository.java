package com.d207.farmer.repository.farm;

import com.d207.farmer.domain.farm.FarmTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FarmTodoRepository extends JpaRepository<FarmTodo, Long> {
    @Query("select ft from FarmTodo ft join fetch ft.farm where ft.farm.id in :farmIds and ft.isCompleted = false order by ft.todoDate")
    List<FarmTodo> findByFarmIdInAndIsCompletedFalseOrderByTodoDate(List<Long> farmIds);
}
