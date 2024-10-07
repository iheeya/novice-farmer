import React from 'react';
import styles from '../../styles/Main/TodoInfo.module.css';
import { getImageForWeather } from '../../utils/imageMapping';
import { getImageForPlantGrowthStep } from '../../utils/imageMapping';

interface TodoInfoProps {
  data: {
    isUsable: boolean;
    todoType: string;
    title: string;
    myPlaceName: string;
    myplantName: string; 
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
  const plantGrowthImage = getImageForPlantGrowthStep(data.plantName, Number(data.growthStep));

  return (
    <div className={styles.todoContainer}>
      <div className={styles.titleContainer}>
        <h2 className={styles.title}>{data.title}!</h2>
        <img src={weatherImage} alt="Weather Icon" className={styles.weatherImage} />
      </div>
      <div className={styles.content}>
        <img src={plantGrowthImage} alt={data.plantName} className={styles.plantImage} /> 
        <div className={styles.textSection}>
          <div><strong>{data.plantName}</strong>가 있는 <br /><strong>{data.myPlaceName}</strong>!</div> 
          <div className={styles.info}>
            <div>
              <p className={styles.date}>{new Date(data.todoDate).toLocaleDateString()} | {data.temperature}</p>
              <p className={styles.location}>{data.address}</p>
            </div>
            
          </div>
        </div>
      </div>
    </div>
  );
};

export default TodoInfo;
