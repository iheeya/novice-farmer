import React from 'react';
import styles from '../../styles/Main/TodoInfo.module.css';
import { getImageForWeather } from '../../utils/imageMapping';

interface TodoInfoProps {
  data: {
    isUsable: boolean;
    todoType: string;
    title: string;
    placeName: string;
    plantName: string; 
    plantImagePath: string;
    growthStep: string;
    todoDate: string;
    address: string;
    temperature: string;
  };
}

const TodoInfo: React.FC<TodoInfoProps> = ({ data }) => {
  const weatherImage = getImageForWeather(data.title);

  return (
    <div className={styles.todoContainer}>
      <div className={styles.titleContainer}>
        <h2 className={styles.title}>{data.title}!</h2>
        <img src={weatherImage} alt="Weather Icon" className={styles.weatherImage} />
      </div>
      <div className={styles.content}>
        <img src={data.plantImagePath} alt={data.plantName} className={styles.plantImage} /> 
        <div className={styles.textSection}>
          <div><strong>{data.plantName}</strong>가 있는 <br /><strong>{data.placeName}</strong>!</div> 
          <div className={styles.info}>
            <div>
              <p className={styles.date}>{new Date(data.todoDate).toLocaleDateString()}</p>
              <p className={styles.location}>{data.address}</p>
            </div>
            <p className={styles.temperature}>{data.temperature}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TodoInfo;
