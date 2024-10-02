import React, { useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from '../../styles/Detail/cameraPage.module.css';

const CameraPage: React.FC = () => {
  const [capturedImage, setCapturedImage] = useState<string | null>(null);
  const videoRef = useRef<HTMLVideoElement>(null);
  const streamRef = useRef<MediaStream | null>(null); // 카메라 스트림을 저장할 ref
  const navigate = useNavigate();

  // 카메라 시작
  const startCamera = async () => {
    try {
      const stream = await navigator.mediaDevices.getUserMedia({
        video: { facingMode: 'environment' }, // 후면 카메라로 설정
      });
      streamRef.current = stream;
      if (videoRef.current) {
        videoRef.current.srcObject = stream;
      }
    } catch (err) {
      console.error('카메라를 시작할 수 없습니다.', err);
    }
  };
  

  // 이미지 캡처
  const captureImage = () => {
    const canvas = document.createElement('canvas');
    const context = canvas.getContext('2d');
    if (videoRef.current && context) {
      canvas.width = videoRef.current.videoWidth;
      canvas.height = videoRef.current.videoHeight;
      context.drawImage(videoRef.current, 0, 0, canvas.width, canvas.height);
      const imageDataUrl = canvas.toDataURL('image/png');
      setCapturedImage(imageDataUrl);
    }
  };

  // 다시 찍기 버튼 처리
  const retakeImage = () => {
    setCapturedImage(null);
    startCamera(); // 카메라 재시작
  };

  // 이미지 전송 처리
  const sendImageToBackend = () => {
    console.log('이미지를 백엔드로 전송:', capturedImage);
  };

  // 카메라 스트림 중지 함수
  const stopCamera = () => {
    if (streamRef.current) {
      streamRef.current.getTracks().forEach((track) => {
        if (track.readyState === 'live') {
          track.stop(); // 모든 트랙을 중지하여 카메라 종료
        }
      });
      streamRef.current = null; // 스트림 해제
      if (videoRef.current) {
        videoRef.current.srcObject = null; // 비디오 엘리먼트의 srcObject를 해제
      }
    }
  };

  // 페이지가 렌더링될 때 카메라 시작
  React.useEffect(() => {
    startCamera();

    // 페이지를 벗어날 때 카메라 중지
    return () => {
      stopCamera();
    };
  }, []);

  return (
    <div className={styles.cameraPage}>
      {capturedImage ? (
        <div className={styles.capturedImageContainer}>
        <img src={capturedImage} alt="Captured" className={styles.capturedImage} />
        <div className={styles.buttonsContainer}>
          {/* 분석하기 버튼: 가운데 큰 동그란 버튼 */}
          <button onClick={sendImageToBackend} className={styles.analyzeButton}>분석하기</button>
      
          {/* 다시 찍기 버튼: Restart.png 아이콘 사용 */}
          <button onClick={retakeImage} className={styles.retakeButton}>
            <img src={require('../../assets/icons/Restart.png')} alt="다시 찍기" />
          </button>
      
        </div>
      </div>
      ) : (
        <div className={styles.cameraContainer}>
          <video ref={videoRef} autoPlay className={styles.cameraFeed} />
          <button onClick={captureImage} className={styles.captureButton}>촬영하기</button>
          <p className={styles.instructions}>작물을 화면 안에  <br /> 인식시켜 주세요.</p>
          <button
            onClick={() => {
              stopCamera(); // 취소 시 카메라 스트림 중지
              navigate(-1); // 이전 페이지로 이동
            }}
            className={styles.cancelButton}
          >
            취소
          </button>
        </div>
      )}
    </div>
  );
};

export default CameraPage;
