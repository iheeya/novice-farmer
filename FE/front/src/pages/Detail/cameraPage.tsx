import React, { useRef, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styles from '../../styles/Detail/cameraPage.module.css';
import CameraLoading from '../../components/Detail/cameraLoading'; // 새로운 로딩 컴포넌트 임포트

const CameraPage: React.FC = () => {
  const [capturedImage, setCapturedImage] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false); // 로딩 상태 추가
  const videoRef = useRef<HTMLVideoElement>(null);
  const streamRef = useRef<MediaStream | null>(null); // 카메라 스트림을 저장할 ref
  const navigate = useNavigate();
  
  // URL에서 myPlaceId와 myPlantId를 받아옴
  const { myPlaceId, myPlantId } = useParams<{ myPlaceId: string; myPlantId: string }>();

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

  /// 이미지 전송 처리
// 이미지 전송 처리
const sendImageToBackend = async () => {
  if (!capturedImage) return;

  try {
    // 로딩 상태 설정
    setIsLoading(true);

    // 아직 백엔드 로직이 완성되지 않았으므로 주석 처리
    /*
    const response = await fakeApiCallToAnalyzeImage(capturedImage);

    setTimeout(() => {
      navigate(`/myGarden/${myPlaceId}/${myPlantId}/diagnosis`, {
        state: {
          plantName: '토마토',  // 예시 데이터
          capturedImage,
          pestDetected: response.pestDetected,
          pestImage: response.pestImage,
          pestDescription: response.pestDescription,
          treatmentMethod: response.treatmentMethod,
        },
      });
      setIsLoading(false);  // 페이지 이동 후 로딩 해제
    }, 2000);
    */

    setTimeout(() => {
      navigate(`/myGarden/${myPlaceId}/${myPlantId}/diagnosis`, {
        state: {
          plantName: '토마토',  // 임시 데이터
          capturedImage,
          pestDetected: false, // 임시로 병해충 감지된 것으로 가정
        },
      });
      setIsLoading(false);  // 페이지 이동 후 로딩 해제
    }, 2000); 
  } catch (error) {
    console.error('이미지 분석 중 오류 발생:', error);
    setIsLoading(false);  // 오류 발생 시에도 로딩 해제
  }
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
      {isLoading ? (
        <CameraLoading />  // 로딩 중일 때 새로운 로딩 컴포넌트 보여주기
      ) : capturedImage ? (
        <div className={styles.capturedImageContainer}>
          <img src={capturedImage} alt="Captured" className={styles.capturedImage} />
          <div className={styles.buttonsContainer}>
            {/* 분석하기 버튼: 가운데 큰 동그란 버튼 */}
            <button onClick={sendImageToBackend} className={styles.analyzeButton}>
              병충해 <br /> 분석하기
            </button>

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
          <p className={styles.instructions}>
            작물을 화면 안에 <br /> 인식시켜 주세요.
          </p>
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
