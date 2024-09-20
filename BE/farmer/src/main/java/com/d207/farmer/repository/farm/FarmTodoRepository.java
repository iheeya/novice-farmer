package com.d207.farmer.repository.farm;

import com.d207.farmer.domain.farm.FarmTodo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmTodoRepository extends JpaRepository<FarmTodo, Long> {
}
