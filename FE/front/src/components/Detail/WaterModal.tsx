import React from 'react';
import Modal from 'react-modal';
import { CSSTransition } from 'react-transition-group';

interface WaterModalProps {
  isOpen: boolean;
  onClose: () => void;
  onConfirm: () => void;
  placeName: string;
  plantName: string;
  recentWateringDate: string;
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
    padding: '1.5rem',
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

const WaterModal: React.FC<WaterModalProps> = ({ placeName, plantName, onClose, onConfirm, isOpen, recentWateringDate }) => {
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
          <p style={{ fontSize: '1.3rem', fontWeight: 'bold', textAlign: 'center', marginBottom: '1rem' }}>
            {plantName}에 물을 주시겠습니까?
          </p>
          <p style={{ fontSize: '1.6rem', textAlign: 'center', marginBottom: '2rem' }}>
            {placeName} - {plantName}
          </p>
          <p style={{ fontSize: '1.6rem', color: 'blue', textAlign: 'center', marginBottom: '2rem' }}>
            최근 물 준 날짜 {recentWateringDate}
          </p>
        </div>
        <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: 'auto' }}>
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
              backgroundColor: '#3498db',
              color: 'white',
              border: 'none',
            }}
            onClick={onConfirm} // 물 주기 확인 시 onConfirm 호출
          >
            물 주기
          </button>
        </div>
      </Modal>
    </CSSTransition>
  );
};

export default WaterModal;
