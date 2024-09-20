import React from 'react';
import styles from '../../styles/Main/FavoritesInfo.module.css'; // CSS 경로

interface FavoritesInfoProps {
  data: {
    crops: string[];
    farms: string[];
  };
}

const FavoritesInfo: React.FC<FavoritesInfoProps> = ({ data }) => {
  return (
    <div className={styles.favoritesContainer}>
      <h2>즐겨찾는 작물 및 농장</h2>
      <div className={styles.section}>
        <h3>즐겨찾는 작물</h3>
        <ul className={styles.favoritesList}>
          {data.crops.map((crop, index) => (
            <li key={index} className={styles.favoritesItem}>
              {crop}
            </li>
          ))}
        </ul>
      </div>
      <div className={styles.section}>
        <h3>즐겨찾는 농장</h3>
        <ul className={styles.favoritesList}>
          {data.farms.map((farm, index) => (
            <li key={index} className={styles.favoritesItem}>
              {farm}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default FavoritesInfo;
