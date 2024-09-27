// components/Detail/TodoList.tsx
import React from 'react';
import { getImageForTodoType } from '../../utils/imageMapping';
import styles from '../../styles/Detail/todoList.module.css';

interface Todo {
  todoDate: string;
  todoType: string;
  remainDay: number;
}

interface TodoListProps {
  todos: Todo[];
}

const TodoList: React.FC<TodoListProps> = ({ todos }) => {
  const formatDate = (dateString: string) => {
    const [year, month, day] = dateString.split('-');
    return `${year.slice(2)}.${month}.${day}`;
  };

  return (
    <div className={styles.todosContainer}>
      {todos.map((todo, index) => (
        <div
          key={index}
          className={`${styles.todoBox} ${todo.todoType === 'WATERING' ? styles.watering : ''} 
          ${todo.todoType === 'FERTILIZERING' ? styles.fertilizing : ''} 
          ${todo.todoType === 'HARVESTING' ? styles.harvesting : ''}`}
        >
          <img
            src={getImageForTodoType(todo.todoType)}
            alt={todo.todoType}
            className={styles.todoIcon}
          />
          <p className={styles.todoDate}>{formatDate(todo.todoDate)}</p>
          <p className={styles.todoText}>
            <span className={styles.highlightedText}>{todo.remainDay}일 후에&nbsp;</span>
            {todo.todoType === 'WATERING' && '물을 줘야해요!'}
            {todo.todoType === 'FERTILIZERING' && '비료를 줘야해요!'}
            {todo.todoType === 'HARVESTING' && '수확할 수 있어요!'}
          </p>
        </div>
      ))}
    </div>
  );
};

export default TodoList;
