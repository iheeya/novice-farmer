package com.d207.farmer.repository.farm;

import com.d207.farmer.domain.farm.FarmTodo;
import com.d207.farmer.domain.farm.TodoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FarmTodoRepository extends JpaRepository<FarmTodo, Long> {
    @Query("select ft from FarmTodo ft join fetch ft.farm join fetch ft.farm.plant where ft.farm.id in :farmIds and ft.isCompleted = false order by ft.todoDate")
    List<FarmTodo> findByFarmIdInAndIsCompletedFalseOrderByTodoDate(List<Long> farmIds);

    @Query("select ft from FarmTodo ft join fetch ft.farm join fetch ft.farm.plant where ft.farm.id = :farmId and ft.todoType = :todoType and ft.isCompleted = false")
    List<FarmTodo> findByFarmIdAndIsCompletedFalseAndTodoType(@Param("farmId") Long farmId, @Param("todoType") TodoType todoType);
}
