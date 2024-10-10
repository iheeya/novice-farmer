import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from '../../styles/WeekendFarm/WeekendFarmPage.module.css';
import { getWeekendFarmInfo } from '../../services/WeekendFarm/WeekendFarmApi';
import WeekendFarmList from '../../components/WeekendFarm/WeekendFarmList';

declare global {
  interface Window {
    kakao: any;
  }
}

const WeekendFarmPage: React.FC = () => {
  const [weekendFarms, setWeekendFarms] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [selectedFarm, setSelectedFarm] = useState<any>(null);
  const mapContainerRef = useRef<HTMLDivElement | null>(null);
  const farmListRef = useRef<(HTMLDivElement | null)[]>([]); // 각 농장 리스트 항목의 ref 저장
  const navigate = useNavigate();

  useEffect(() => {
    const apiKey = process.env.REACT_APP_KAKAO_MAP_API_KEY; // .env에 저장한 API 키를 불러옴

    if (!apiKey) {
      console.error('Kakao API key is missing');
      return;
    }

    const initializeMap = (data: any[]) => {
      const container = mapContainerRef.current;
      if (!container) {
        console.error('Map container is still missing');
        return;
      }

      const options = {
        center: new window.kakao.maps.LatLng(data[0].latitude, data[0].longitude),
        level: 10,
      };
      const map = new window.kakao.maps.Map(container, options);

      // 마커 추가
      data.forEach((farm: any, index: number) => {
        const markerPosition = new window.kakao.maps.LatLng(farm.latitude, farm.longitude);
        const marker = new window.kakao.maps.Marker({
          position: markerPosition,
        });
        marker.setMap(map);

        // 마커 클릭 이벤트
        window.kakao.maps.event.addListener(marker, 'click', () => {
          setSelectedFarm(farm);
          farmListRef.current[index]?.scrollIntoView({ behavior: 'smooth', block: 'center' });
        });
      });
    };

    const loadKakaoMapsScript = () => {
      const script = document.createElement('script');
      script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${apiKey}&autoload=false&libraries=services`;
      script.async = true;
      document.head.appendChild(script);

      script.onload = () => {
        window.kakao.maps.load(() => {
          getWeekendFarmInfo()
            .then((data) => {
              setWeekendFarms(data);
              setLoading(false);
              if (mapContainerRef.current) {
                initializeMap(data);
              }
            })
            .catch((error) => {
              console.error('주말농장 데이터를 불러오는 중 오류 발생:', error);
              setLoading(false);
            });
        });
      };

      script.onerror = () => {
        console.error('Failed to load Kakao Maps script');
      };
    };

    // 카카오 지도 API 스크립트가 이미 로드된 경우 처리
    if (window.kakao && window.kakao.maps) {
      window.kakao.maps.load(() => {
        getWeekendFarmInfo()
          .then((data) => {
            setWeekendFarms(data);
            setLoading(false);
            if (mapContainerRef.current) {
              initializeMap(data);
            }
          })
          .catch((error) => {
            console.error('주말농장 데이터를 불러오는 중 오류 발생:', error);
            setLoading(false);
          });
      });
    } else {
      // Kakao Maps 스크립트를 처음 로드할 때
      loadKakaoMapsScript();
    }
  }, []);

  const handleBackClick = () => {
    navigate(-1); // 뒤로가기
  };

  const handleFarmClick = (farm: any) => {
    // 클릭된 farm의 카카오 지도 URL로 이동
    window.open(`https://map.kakao.com/link/map/${farm.name},${farm.latitude},${farm.longitude}`);
  };

  if (loading) {
    return <div>로딩 중...</div>;
  }

  return (
    <div className={styles.weekendFarmContainer}>
      <div className={styles.headerContainer}>
        <img
          src={require('../../assets/icons/Back.png')}
          alt="Back"
          className={styles.backButton}
          onClick={handleBackClick}
        />
        <h2 className={styles.title}>
          # {selectedFarm ? `${selectedFarm.address.split(' ')[1]} 주말농장` : '주말농장'}
        </h2>
      </div>

      <hr className={styles.divider} />

      {/* 지도 영역 */}
      <div
        ref={mapContainerRef}
        className={styles.mapWrapper}
        style={{ height: '400px', width: '100%' }}
      ></div>

      {/* 주말농장 목록 */}
      <div className={styles.farmList}>
        {weekendFarms.map((farm, index) => (
          <div
            key={farm.id}
            ref={(el) => (farmListRef.current[index] = el)} // 각 항목을 ref에 저장
            onClick={() => handleFarmClick(farm)}
            className={`${styles.farmItem} ${
              selectedFarm && selectedFarm.id === farm.id ? styles.activeFarmItem : ''
            }`}
          >
            <WeekendFarmList
              name={farm.name}
              address={farm.address}
              desc={farm.desc}
              imagePath={farm.imagePath || undefined}
              isActive={selectedFarm && selectedFarm.id === farm.id} // 활성화 여부 전달
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default WeekendFarmPage;
