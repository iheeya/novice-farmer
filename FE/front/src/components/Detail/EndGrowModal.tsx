import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; // 페이지 이동을 위한 useNavigate 훅 추가
import Modal from 'react-modal';
import { CSSTransition } from 'react-transition-group';

interface EndGrowModalProps {
  placeName: string;
  plantName: string;
  myPlaceId: number;
  firstHarvestDate: string; // 첫 수확 날짜 전달
  onClose: () => void;
  onConfirm: () => void; // 재배 종료 함수
  isOpen: boolean;  // 모달 오픈 상태
}

// 스타일 객체 정의
const modalStyles: ReactModal.Styles = {
  overlay: {
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    zIndex: 1000,
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
  content: {
    width: '80%',
    maxWidth: '400px',
    height: 'auto',
    padding: '1rem',
    borderRadius: '0.5rem',
    backgroundColor: 'white',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'space-between',
    position: 'relative',
    left: '0',
    zIndex: 1001,
  },
};

// 박스 스타일 정의
const boxStyle = {
    display: 'flex',
    justifyContent: 'center', 
    alignItems: 'center',
  padding: '0.5rem',
  borderRadius: '0.25rem',
  backgroundColor: '#f9f9f9',
  marginBottom: '1rem',
  border: '1px solid #ddd',
};

const EndGrowModal: React.FC<EndGrowModalProps> = ({ placeName, plantName, firstHarvestDate, onClose, onConfirm, isOpen, myPlaceId }) => {
  const [endGrowDate, setEndGrowDate] = useState<string>(''); // 재배 종료 날짜 상태
  const navigate = useNavigate(); // useNavigate 훅 사용

  // 모달이 열릴 때 현재 날짜를 설정
  useEffect(() => {
    if (isOpen) {
      const currentDate = new Date().toISOString().split('T')[0]; // 'YYYY-MM-DD' 형식
      setEndGrowDate(currentDate);
    }
  }, [isOpen]);

  const handleEndGrow = () => {
    onConfirm(); // 재배 종료 로직 실행
    navigate(`/myGarden/${myPlaceId}`); // /myGarden/{myPlaceId} 페이지로 이동
  };

  return (
    <CSSTransition
      in={isOpen}
      timeout={{ enter: 300, exit: 300 }}
      classNames="slide"
      unmountOnExit
    >
      <Modal
        isOpen={isOpen}
        onRequestClose={onClose}
        style={modalStyles}  // 스타일 객체 적용
      >
        <div style={{ flexGrow: 1 }}>
          {/* 상단 문구 */}
          <p style={{ fontSize: '1.3rem', fontWeight: 'bold', textAlign: 'center', marginBottom: '2rem' }}>
            {plantName}의 재배를 종료하시겠어요?
          </p>
          
          {/* 박스 형태로 정보 표시 */}
          <div style={boxStyle}>
            <strong>텃밭&nbsp;:&nbsp;</strong> {placeName}
          </div>
          <div style={boxStyle}>
            <strong>위치&nbsp;:&nbsp;</strong> {plantName}
          </div>
          <div style={boxStyle}>
            <strong>첫 수확 날짜&nbsp;:&nbsp;</strong> {firstHarvestDate}
          </div>
          <div style={boxStyle}>
            <strong>재배 종료 날짜&nbsp;:&nbsp;</strong> {endGrowDate}
          </div>
        </div>

        {/* 버튼들 */}
        <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: '2rem' }}>
          <button
            style={{
              padding: '0.75rem 1.5rem',
              fontSize: '1rem',
              cursor: 'pointer',
              borderRadius: '0.25rem',
              width: '48%',
              backgroundColor: '#f0f0f0',
              border: 'none',
            }}
            onClick={onClose}
          >
            취소
          </button>
          <button
            style={{
              padding: '0.75rem 1.5rem',
              fontSize: '1rem',
              cursor: 'pointer',
              borderRadius: '0.25rem',
              width: '48%',
              backgroundColor: '#e74c3c',
              color: 'white',
              border: 'none',
            }}
            onClick={handleEndGrow} // 재배 종료 및 페이지 이동
          >
            재배 종료
          </button>
        </div>
      </Modal>
    </CSSTransition>
  );
};

export default EndGrowModal;
