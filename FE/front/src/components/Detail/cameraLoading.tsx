import React from 'react';
import styles from '../../styles/Detail/cameraLoading.module.css';  // CSS 모듈을 불러옴
import loading from '../../assets/img/loading/loading.png';

function CameraLoading() {
    return (
        <div className={styles['loading-instruction']}>
            <div>병해충 분석중....</div>
            <div>잠시만 기다려주세요.</div>

            <img
                src={loading}
                className={styles['loading-image-size']}
                alt="loading"
            />
        </div>
    );
}

export default CameraLoading;
