import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getImageForPlantGrowthStep } from '../../utils/imageMapping'; // 이미지 매핑 함수
import styles from '../../styles/Detail/myPlant.module.css'; // CSS 파일 경로
import startIcon from '../../assets/icons/start.png'; // 시작하기 버튼 아이콘
import Icons from '../../components/Detail/Icons'; // Icons 컴포넌트 임포트
import GrowthProgressBar from '../../components/Detail/GrowthProgressBar';
import TodoList from '../../components/Detail/TodoList';
import PlantManagementInfo from '../../components/Detail/PlantManageInfo';
import PestDetection from '../../components/Detail/PestDetection';

// PlantData 타입 정의
interface PlantData {
  isStarted: boolean;
  isAlreadyFirstHarvest: boolean;
  plantInfo: {
    placeName: string;
    myPlaceId: number;
    myPlaceName: string;
    plantName: string;
    myPlantName: string;
    plantImagePath: string;
    startDate: string;
    plantGrowthStep: number;
    plantDegreeRatio: number;
    threshold: {
      totalStep: number;
      step1: number;
      step2: number;
      step3: number | null; // step3 could be null
    };
    firstHarvestDate: string;
    recentWateringDate: string;
    recentFertilizingDate: string;
  };
  todos: {
    todoDate: string;
    todoType: string;
    remainDay: number;
  }[];
}

const MyPlant = () => {
  const { myPlantId } = useParams<{ myPlantId: string }>(); // useParams로 myPlantId 가져오기
  const navigate = useNavigate();

  // 더미 데이터 2개 정의
  const dummyData1: PlantData = {
    isStarted: false,
    isAlreadyFirstHarvest: false,
    plantInfo: {
      placeName: '베란다',
      myPlaceId: 1,
      myPlaceName: '우리집베란다',
      plantName: '토마토',
      myPlantName: '토순이',
      plantImagePath: 'test path',
      startDate: '',
      plantGrowthStep: 3,
      plantDegreeRatio: 88,
      threshold: {
        totalStep: 4,
        step1: 10,
        step2: 40,
        step3: 100,
      },
      firstHarvestDate: '',
      recentWateringDate: '',
      recentFertilizingDate: '',
    },
    todos: [
      { todoDate: '2024-09-26', todoType: 'WATERING', remainDay: 1 },
      { todoDate: '2024-09-30', todoType: 'FERTILIZERING', remainDay: 5 },
    ],
  };

  const dummyData2: PlantData = {
    isStarted: true,
    isAlreadyFirstHarvest: true,
    plantInfo: {
      placeName: '주말농장',
      myPlaceId: 2,
      myPlaceName: '구미농장',
      plantName: '상추',
      myPlantName: '상춘이',
      plantImagePath: 'test path',
      startDate: '2024-09-25',
      plantGrowthStep: 2,
      plantDegreeRatio: 92,
      threshold: {
        totalStep: 3,
        step1: 40,
        step2: 100,
        step3: null // 마지막 스텝은 항상 100%
      },
      firstHarvestDate: '2024-09-25',
      recentWateringDate: '2024-09-25',
      recentFertilizingDate: '2024-09-25',
    },
    todos: [
      { todoDate: '2024-09-27', todoType: 'WATERING', remainDay: 2 },
      { todoDate: '2024-10-01', todoType: 'FERTILIZERING', remainDay: 6 },
    ],
  };

  // 초기 상태를 null로 시작
  const [plantData, setPlantData] = useState<PlantData | null>(null);
  const [isEditing, setIsEditing] = useState(false); // 수정 모드 여부
  const [tempNickname, setTempNickname] = useState(''); // 수정 중인 이름
  const [errorMessage, setErrorMessage] = useState(''); // 오류 메시지

  // myPlantId에 따라 더미 데이터를 설정하는 useEffect
  useEffect(() => {
    if (myPlantId === '1') {
      setPlantData(dummyData1);
      setTempNickname(dummyData1.plantInfo.myPlantName); // 초기값 설정
    } else if (myPlantId === '2') {
      setPlantData(dummyData2);
      setTempNickname(dummyData2.plantInfo.myPlantName); // 초기값 설정
    }
  }, [myPlantId]);

 

const handleSaveNickname = () => {
  if (tempNickname.length > 12) {
    setErrorMessage("이름은 12자 이하로 입력해주세요.");
    return;
  }
  if (plantData) {
    setPlantData({
      ...plantData,
      plantInfo: { ...plantData.plantInfo, myPlantName: tempNickname },
    });
    setIsEditing(false);
    setErrorMessage('');
  }
};



const handleDelete = () => {
  alert(`해당 ${plantData?.plantInfo.myPlantName} 작물을 삭제하였습니다.`);
  // 삭제 로직 추가
};

 // 첫수확 확인 함수
 const confirmHarvest = () => {
  if (plantData) {
    setPlantData({
      ...plantData,
      isAlreadyFirstHarvest: true, // 첫 수확 완료 후 상태 변경
    });
  }
};

const handleWater = () => {
  alert(`해당 ${plantData?.plantInfo.myPlantName}에 물을 주었습니다.`);
  // 물주기 로직 추가
};

const handleFertilize = () => {
  alert(`해당 ${plantData?.plantInfo.myPlantName}에 비료를 주었습니다.`);
  // 비료주기 로직 추가
};


  // 로딩 상태 처리
  if (!plantData) return <div>Loading...</div>;

  return (
    <div className={styles.myPlantContainer}>
      {/* Header */}
      <div className={styles.header}>
        <img
          src={require('../../assets/icons/Back.png')}
          alt="Back"
          className={styles.backButton}
          onClick={() => navigate(-1)}
        />
        <div className={styles.title}>
          <h1>{plantData.plantInfo.myPlaceName}</h1>
        </div>
      </div>

      {/* Plant Image & Name Container */}
      <div className={styles.plantImageContainer}>
        <div className={styles.nameAndIconContainer}>
          {isEditing ? (
            <div>
              <input
                value={tempNickname}
                onChange={(e) => {
                  if (e.target.value.length <= 10) {
                    setTempNickname(e.target.value);
                  } else {
                    alert("작물 이름은 10자 이내로 입력해주세요.");
                  }
                }}
                className={styles.nicknameInput}
                placeholder="작물이름을 입력하세요"
                // 엔터키를 누르면 저장하도록 처리
                onKeyDown={(e) => {
                  if (e.key === "Enter") {
                    handleSaveNickname();
                  }
                }}
              />
            </div>
          ) : (
            <h2 className={styles.plantName}>{plantData.plantInfo.myPlantName}</h2>
          )}
          <img
            src={require(`../../assets/icons/${isEditing ? 'Check.png' : 'Write.png'}`)}
            alt={isEditing ? 'Save' : 'Edit'}
            className={styles.editIcon}
            onClick={isEditing ? handleSaveNickname : () => setIsEditing(true)}
          />
        </div>

        <img
          src={getImageForPlantGrowthStep(plantData.plantInfo.plantName, plantData.plantInfo.plantGrowthStep)}
          alt="Plant Illustration"
          className={styles.plantImage}
        />
      </div>

      {/* 에러 메시지 표시 */}
      {errorMessage && <p className={styles.errorMessage}>{errorMessage}</p>}

      {/* Start Button or Plant Start Date */}
      {plantData.isStarted ? (
        <p className={styles.startDate}>파종일: {plantData.plantInfo.startDate}</p>
      ) : (
        <div className={styles.startButtonContainer}>
          <img
            src={startIcon}
            alt="Start"
            className={styles.startButtonIcon}
            onClick={() =>
              setPlantData({
                ...plantData,
                isStarted: true,
                plantInfo: {
                  ...plantData.plantInfo,
                  startDate: new Date().toISOString().split('T')[0],
                },
              })
            }
          />
          <span>시작하기</span>
        </div>
      )}

      {/* Icons 컴포넌트 */}
      <Icons
        onDelete={handleDelete}
        onHarvest={confirmHarvest}
        onWater={handleWater}
        onFertilize={handleFertilize}
        placeName={plantData.plantInfo.myPlaceName}
        plantName={plantData.plantInfo.myPlantName}
        myPlaceId={plantData.plantInfo.myPlaceId}
        isAlreadyFirstHarvest={plantData.isAlreadyFirstHarvest} // 첫 수확 여부 전달
        recentWateringDate={plantData.plantInfo.recentWateringDate}  // 물 준 날짜 전달
        recentFertilizingDate={plantData.plantInfo.recentFertilizingDate}  // 비료 준 날짜 전달
        firstHarvestDate={plantData.plantInfo.firstHarvestDate}  // 첫 수확일 전달
      />

      {/* GrowthProgressBar 컴포넌트 */}
      <GrowthProgressBar
        plantGrowthStep={plantData.plantInfo.plantGrowthStep}
        plantDegreeRatio={plantData.plantInfo.plantDegreeRatio}
        threshold={plantData.plantInfo.threshold}
      />

      {/* Todos 컴포넌트 */}
      <TodoList todos={plantData.todos} />

      <div className={styles.managementAndPestContainer}>
        {/* 작물 관리 정보 컴포넌트 */}
        <PlantManagementInfo
          firstHarvestDate={plantData.plantInfo.firstHarvestDate}
          recentWateringDate={plantData.plantInfo.recentWateringDate}
          recentFertilizingDate={plantData.plantInfo.recentFertilizingDate}
        />

        {/* 병해충 검사 박스 */}
        <PestDetection
          plantName={plantData.plantInfo.myPlantName}
          myPlaceId={plantData.plantInfo.myPlaceId}  // placeId 전달
          myPlantId={myPlantId ?? ''}  // plantId가 없을 경우 빈 문자열로 전달
        />
      </div>

    </div>

  );
};

export default MyPlant;
