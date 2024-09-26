import Modal from 'react-modal'
import { useEffect, useRef, useState } from 'react';
import '../../styles/RegisterGarden/gardenModal.css'
import ButtonGroup from '@mui/material/ButtonGroup';
import Button from '@mui/material/Button';
import DaumPostcodeEmbed from 'react-daum-postcode';
import TextField from '@mui/material/TextField';
import axios from 'axios'
import {  useSelector } from 'react-redux';
import { RootState } from '../../store/AddFarm/store';
import { motion } from "framer-motion";
import { CSSTransition } from 'react-transition-group';
import { selectPlantPost } from '../../services/AddGarden/AddGardenPost';

interface GardenModalProps {
    plantId: number |null;
    onClose: () => void; // onClose는 함수 타입
    onLoading: () => void;
}

const customModalStyles: ReactModal.Styles = {
    overlay: {
      backgroundColor: "rgba(0, 0, 0, 0.4)",
      width: "100%",
      zIndex: 10,
    },
    content: {
      width: "100%",
      height: "40%",
      zIndex: 150,
      position: "absolute",
      top: "72.7%",
      left: "50%",
      transform: "translate(-50%, -50%)",
      borderRadius: "10px",
      boxShadow: "2px 2px 2px rgba(0, 0, 0, 0.25)",
      backgroundColor: "white",
      overflow: "auto",
      display: "flex",
      flexDirection: "column",
      justifyContent: "center",
      padding: "20px",
    },
};

function GardenModal({ plantId, onClose, onLoading }: GardenModalProps) {
  

  const plantData = useSelector((state: RootState) => state.farmSelect.plant)
  const [isModalOpen, setIsModalOpen] = useState(true); // 모달 오픈 상태 관리
 

  const modalVariants = {
    hidden: { opacity: 0, y: 50 },
    visible: { opacity: 1, y: 0 },
    exit: { opacity: 0, y: 50 },
  };

   const handleClose = () => {
    setIsModalOpen(false);
    // onClose(); // Call the original onClose function
  };
  

  const handleSubmit = async () => {
   
      const payload = {
        plantId: plantId,
        plantName: plantData
      };

      try {
        const response = await selectPlantPost(payload);
        console.log(response.data); // 응답 처리
        onClose(); // 모달 닫기
      } catch (error) {
        console.error("Error posting data:", error);
      }
    
  };

  return (
    <CSSTransition
    in={isModalOpen}
    timeout={{ enter: 300, exit: 300 }}
    classNames="slide"
    mountOnEnter
    unmountOnExit
    onExited={onClose} // Close modal after the exit transition
  >
    <Modal isOpen={true} style={customModalStyles} onRequestClose={handleClose}>
    <motion.div
    initial="hidden"
    animate="visible"
    exit="exit"
    variants={modalVariants}
    transition={{ duration: 0.3 }}
    style={{ display: 'flex', flexDirection: 'column', height: '100%', width: '100%' }}
  >
      <div style={{ display: 'flex', flexDirection: 'column', height: '100%', width: '100%' }}>
        <div className='instruction'>작물에 적합한 텃밭을 알려드려요</div>
        <div className='box-color'>
          <div className='box-title'>작물</div>
          <div className='box-content'>{plantData}</div>
        </div>
       

        <div style={{ marginTop: 'auto', display: 'flex', justifyContent: 'center' }}>
          <ButtonGroup sx={{ width: '100%', padding: 0, margin: 0, boxSizing: 'border-box' }}>
            <Button
              sx={{
                backgroundColor: 'white',
                border: '1px solid #E2E2E2',
                color: '#71717A',
                padding: '10px 20px',
                flexGrow: 1,
                width: '50%',
                '&:hover': {
                  backgroundColor: '#f5f5f5',
                },
              }}
              onClick={() => {
                setIsModalOpen(false);
                // onClose();
              }}
            >
              취소
            </Button>
            <Button
              sx={{
                backgroundColor: '#B0D085',
                border: '1px solid #E2E2E2',
                color: 'white',
                padding: '10px 20px',
                flexGrow: 1,
                width: '50%',
                '&:hover': {
                  backgroundColor: '#A0C075',
                },
              }}
              onClick={() => {
                // 선택완료 버튼 클릭 시 동작 추가
                // 예: 주소를 상위 컴포넌트로 전달하거나 상태 업데이트
                handleSubmit();  // post 요청 보내기
                // onClose();
                onLoading();
              }}
            >
              선택완료
            </Button>
          </ButtonGroup>
        </div>
      </div>
      </motion.div>
    </Modal>
    </CSSTransition>
  );
}

export default GardenModal;
