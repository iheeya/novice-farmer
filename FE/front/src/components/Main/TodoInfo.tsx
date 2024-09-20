import React from 'react';
import styles from '../../styles/Main/TodoInfo.module.css'; // CSS 경로
import { getImageForWeather } from '../../utils/imageMapping';

interface TodoInfoProps {
  data: {
    title: string;
    type: string;
    cropName: string;
    farmLocation: string;
    plant_imgURL: string;
    alertDate: string;
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
        <img src={data.plant_imgURL} alt={data.cropName} className={styles.plantImage} />
        <div className={styles.textSection}>
          <div><strong>{data.cropName}</strong>가 있는 텃밭에 <br /> <strong>{data.type}</strong> 주의!</div>
          <div className={styles.info}>
            <div>
              <p className={styles.date}>{data.alertDate}</p>
              <p className={styles.location}>{data.farmLocation}</p>
            </div>
            <p className={styles.temperature}>{data.temperature}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TodoInfo;
