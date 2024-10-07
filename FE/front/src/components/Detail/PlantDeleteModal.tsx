import React from 'react';
import Modal from 'react-modal';
import { CSSTransition } from 'react-transition-group';

interface PlantDeleteModalProps {
  placeName: string;
  plantName: string;
  onClose: () => void;
  onDelete: () => void; // 부모 컴포넌트에서 전달된 삭제 함수
  isOpen: boolean;  // 모달 오픈 상태
}

// 스타일 객체 정의 (상대적인 단위 사용)
const modalStyles: ReactModal.Styles = {
  overlay: {
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    zIndex: 1000,
    display: 'flex',
    justifyContent: 'center',  // 모달을 중앙에 배치
    alignItems: 'center',       // 모달을 수직으로도 중앙에 배치
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
    justifyContent: 'space-between',  // 버튼을 아래로 배치
    position: 'relative', // fixed 대신 relative로 설정
    left: '0',
    zIndex: 1001,
  },
};

const FarmDeleteModal: React.FC<PlantDeleteModalProps> = ({ placeName, plantName, onClose, onDelete, isOpen }) => {
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
            정말 이 작물을 삭제하시겠습니까?
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
              backgroundColor: '#e74c3c',
              color: 'white',
              border: 'none',
            }}
            onClick={onDelete} // 삭제 버튼 클릭 시 onDelete 호출
          >
            삭제
          </button>
        </div>
      </Modal>
    </CSSTransition>
  );
};

export default FarmDeleteModal;
