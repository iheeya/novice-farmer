import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getImageForPlantGrowthStep } from '../../utils/imageMapping'; 
import styles from '../../styles/Detail/myPlant.module.css';
import startIcon from '../../assets/icons/start.png'; 
import Icons from '../../components/Detail/Icons'; 
import GrowthProgressBar from '../../components/Detail/GrowthProgressBar';
import TodoList from '../../components/Detail/TodoList';
import PlantManagementInfo from '../../components/Detail/PlantManageInfo';
import PestDetection from '../../components/Detail/PestDetection';
import Memo from '../../components/Detail/Memo';
import { getPlantDetailInfo, updatePlantName, startPlant, PlantData } from '../../services/PlantDetail/PlantDetailPageApi';
import { deletePlant, harvestPlant, endCultivation, waterPlant, fertilizePlant } from '../../services/PlantDetail/Icons'; // API 함수 임포트
import Swal from 'sweetalert2'; // SweetAlert2 임포트

const MyPlant = () => {
  const { myPlantId } = useParams<{ myPlantId: string }>() ;
  const navigate = useNavigate();

  const [plantData, setPlantData] = useState<PlantData | null>(null);
  const [isEditing, setIsEditing] = useState(false);
  const [tempNickname, setTempNickname] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [loading, setLoading] = useState(true);

  const fetchPlantDetail = () => {
    if (myPlantId) {
      getPlantDetailInfo(Number(myPlantId))
        .then((data) => {
          setPlantData(data);
          setTempNickname(data.plantInfo.myPlantName);
          setLoading(false);
        })
        .catch((error) => {
          console.error('작물 데이터를 가져오는 데 실패했습니다.', error);
          setLoading(false);
        });
    }
  };

  useEffect(() => {
    fetchPlantDetail();
  }, [myPlantId]);

  // 닉네임 저장 함수
  const handleSaveNickname = () => {
    if (tempNickname.length > 12) {
      setErrorMessage('이름은 12자 이하로 입력해주세요.');
      return;
    }

    if (plantData) {
      updatePlantName(Number(myPlantId), tempNickname)
        .then(() => {
          setPlantData({
            ...plantData,
            plantInfo: { ...plantData.plantInfo, myPlantName: tempNickname },
          });
          setIsEditing(false);
          setErrorMessage('');
          Swal.fire('수정 완료', '작물 이름이 성공적으로 수정되었습니다.', 'success');
        })
        .catch((error) => {
          console.error('작물 이름 수정 실패', error);
          Swal.fire('수정 실패', '작물 이름을 수정하는 데 실패했습니다.', 'error');
        });
    }
  };
  
  // 작물 시작 함수
  const handleStartPlant = async () => {
    if (plantData) {
      try {
        await startPlant(plantData.plantInfo.myPlaceId); // API 요청
        Swal.fire('시작 완료', '작물이 성공적으로 시작되었습니다.', 'success');
        setPlantData({
          ...plantData,
          isStarted: true,
          plantInfo: {
            ...plantData.plantInfo,
            startDate: new Date().toISOString().split('T')[0],
          },
        }); // 상태 업데이트
      } catch (error) {
        console.error('작물 시작 실패', error);
        Swal.fire('시작 실패', '작물을 시작하는 데 실패했습니다.', 'error');
      }
    }
  };

  // 삭제 함수
  const handleDelete = async () => {
    if (plantData) {
      try {
        await deletePlant(plantData.plantInfo.myPlaceId);
        Swal.fire('삭제 완료', '작물이 성공적으로 삭제되었습니다.', 'success');
        navigate(`/myGarden/${plantData.plantInfo.myPlaceId}`);
      } catch (error) {
        console.error('삭제 실패', error);
        Swal.fire('삭제 실패', '작물을 삭제하는 데 실패했습니다.', 'error');
      }
    }
  };

  // 첫 수확 함수
  const handleHarvest = async () => {
    if (plantData) {
      try {
        await harvestPlant(plantData.plantInfo.myPlaceId);
        Swal.fire('첫 수확 완료', '첫 수확이 성공적으로 처리되었습니다.', 'success');
        fetchPlantDetail(); // 상태 업데이트
      } catch (error) {
        console.error('첫 수확 실패', error);
        Swal.fire('첫 수확 실패', '첫 수확에 실패했습니다.', 'error');
      }
    }
  };

  // 재배 종료 함수
  const handleEndCultivation = async () => {
    if (plantData) {
      try {
        await endCultivation(plantData.plantInfo.myPlaceId);
        Swal.fire('재배 종료 완료', '재배가 성공적으로 종료되었습니다.', 'success');
        navigate('/myPage'); // 재배 종료 후 /myPage 경로로 이동
      } catch (error) {
        console.error('재배 종료 실패', error);
        Swal.fire('재배 종료 실패', '재배 종료에 실패했습니다.', 'error');
      }
    }
  };

  // 물주기 함수
  const handleWater = async () => {
    if (plantData) {
      try {
        await waterPlant(plantData.plantInfo.myPlaceId);
        Swal.fire('물주기 완료', '물주기가 성공적으로 처리되었습니다.', 'success');
        fetchPlantDetail(); // 상태 업데이트
      } catch (error) {
        console.error('물주기 실패', error);
        Swal.fire('물주기 실패', '물주기에 실패했습니다.', 'error');
      }
    }
  };

  // 비료주기 함수
  const handleFertilize = async () => {
    if (plantData) {
      try {
        await fertilizePlant(plantData.plantInfo.myPlaceId);
        Swal.fire('비료주기 완료', '비료주기가 성공적으로 처리되었습니다.', 'success');
        fetchPlantDetail(); // 상태 업데이트
      } catch (error) {
        console.error('비료주기 실패', error);
        Swal.fire('비료주기 실패', '비료주기에 실패했습니다.', 'error');
      }
    }
  };

  if (loading) return <div>Loading...</div>;
  if (!plantData) return <div>작물 데이터를 불러오는 데 실패했습니다.</div>;

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
                    alert('작물 이름은 10자 이내로 입력해주세요.');
                  }
                }}
                className={styles.nicknameInput}
                placeholder="작물이름을 입력하세요"
                onKeyDown={(e) => {
                  if (e.key === 'Enter') {
                    handleSaveNickname(); // Enter키로 저장
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
      
      {plantData.isStarted ? (
        <p className={styles.startDate}>파종일: {plantData.plantInfo.startDate}</p>
      ) : (
        <div className={styles.startButtonContainer}>
          <img
            src={startIcon}
            alt="Start"
            className={styles.startButtonIcon}
            onClick={handleStartPlant} // 시작 버튼 클릭 시 호출
          />
          <span>시작하기</span>
        </div>
      )}

      {/* Icons 컴포넌트 */}
      <Icons
        onDelete={handleDelete}
        onHarvest={handleHarvest}
        onEndCultivation={handleEndCultivation}
        onWater={handleWater}
        onFertilize={handleFertilize}
        placeName={plantData.plantInfo.myPlaceName}
        plantName={plantData.plantInfo.myPlantName}
        myPlaceId={plantData.plantInfo.myPlaceId}
        isAlreadyFirstHarvest={plantData.isAlreadyFirstHarvest}
        recentWateringDate={plantData.plantInfo.recentWateringDate || ''}
        recentFertilizingDate={plantData.plantInfo.recentFertilizingDate || ''}
        firstHarvestDate={plantData.plantInfo.firstHarvestDate || ''}
        isStarted={plantData.isStarted}
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
          firstHarvestDate={plantData.plantInfo.firstHarvestDate || ''}
          recentWateringDate={plantData.plantInfo.recentWateringDate || ''}
          recentFertilizingDate={plantData.plantInfo.recentFertilizingDate || ''}
        />

        {/* 병해충 검사 박스 */}
        <PestDetection
          plantName={plantData.plantInfo.myPlantName}
          myPlaceId={plantData.plantInfo.myPlaceId}
          myPlantId={myPlantId ?? ''}
        />
      </div>

      {/* Memo 컴포넌트 */}
      <Memo
      memo={plantData.plantInfo.memo}
      farmId={plantData.plantInfo.myPlaceId}
      onMemoUpdate={(newMemo) => setPlantData({
        ...plantData,
        plantInfo: {
          ...plantData.plantInfo,
          memo: newMemo,
        },
      })}
    />
      
    </div>
  );
};

export default MyPlant;
