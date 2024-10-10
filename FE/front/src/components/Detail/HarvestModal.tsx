import React from 'react';
import Modal from 'react-modal';
import { CSSTransition } from 'react-transition-group';

interface HarvestModalProps {
  placeName: string;
  plantName: string;
  onClose: () => void;
  onConfirm: () => void; // 첫 수확 함수
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

const HarvestModal: React.FC<HarvestModalProps> = ({ placeName, plantName, onClose, onConfirm, isOpen }) => {
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
            {plantName}을(를) 첫 수확하시겠습니까?
          </p>
          <p style={{ fontSize: '1.6rem', textAlign: 'center', marginBottom: '2rem' }}>
            {placeName} - {plantName}
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
              backgroundColor: '#27ae60',
              color: 'white',
              border: 'none',
            }}
            onClick={onConfirm} // 첫 수확 확인 시 onConfirm 호출
          >
            첫 수확
          </button>
        </div>
      </Modal>
    </CSSTransition>
  );
};

export default HarvestModal;
