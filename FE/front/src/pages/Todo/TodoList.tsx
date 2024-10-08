import React, { useEffect, useState } from 'react';
import { getTodoList, Place } from '../../services/Todo/TodoListApi';
import { getImageForCrop, getImageForTodoType } from '../../utils/imageMapping';
import styles from '../../styles/Todo/Todo.module.css';
import AddGarden from './AddGarden'; // 경로 확인

const TodoList: React.FC = () => {
  const [todoData, setTodoData] = useState<Place[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getTodoList();
        setTodoData(data);
      } catch (error) {
        console.error('Failed to fetch todo list:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className={styles.todoContainer}>
      <h1 className={styles.pageTitle}>내 Todo 리스트</h1>
      <hr />
      {todoData.length === 0 ? (
        <AddGarden />
      ) : (
        todoData.map((place) => (
          <div key={place.myPlaceId} className={styles.placeContainer}>
            <h2 className={styles.placeName}>{place.myPlaceName}</h2>
            {place.plants.map((plant) => (
              <div key={plant.myPlantId} className={styles.plantContainer}>
                <div className={styles.plantHeader}>
                  <img
                    src={getImageForCrop(plant.plantName)}
                    alt={plant.plantName}
                    className={styles.plantImage}
                  />
                  <h3 className={styles.plantName}>{plant.myPlantName}</h3>
                </div>
                <div className={styles.todoList}>
                  {plant.todos.map((todo, index) => (
                    <div key={index} className={styles.todoItem}>
                      <img
                        src={getImageForTodoType(todo.todoType)}
                        alt={todo.todoType}
                        className={styles.todoIcon}
                      />
                      <p className={styles.todoText}>
                        {todo.remainDay}일 후{' '}
                        {todo.todoType === 'WATERING' ? '물을 주어야 합니다' : '비료를 주어야 합니다'}.
                      </p>
                    </div>
                  ))}
                </div>
              </div>
            ))}
          </div>
        ))
      )}
    </div>
  );
};

export default TodoList;
