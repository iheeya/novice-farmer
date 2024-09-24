import Modal from 'react-modal'
import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../../styles/RegisterGarden/gardenModal.css'
import ButtonGroup from '@mui/material/ButtonGroup';
import Button from '@mui/material/Button';
import DaumPostcodeEmbed from 'react-daum-postcode';
import TextField from '@mui/material/TextField';
import axios from 'axios'
import { useDispatch, useSelector } from 'react-redux';
import { setLocationData, setPlantData } from '../../store/AddFarm/store';
import { RootState } from '../../store/AddFarm/store';
import { motion } from "framer-motion";
import { CSSTransition } from 'react-transition-group';

interface GardenModalProps {
  plantId: number| null;
  plantName: string | null;
  onClose: () => void; // onClose는 함수 타입
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

function GardenFinalModal({  onClose, plantId, plantName }: GardenModalProps) {
  const dispatch = useDispatch();
  const farmData = useSelector((state: RootState) => state.farmSelect.farm);
  const locationData = useSelector((state:RootState) => state.farmSelect.location);
  const addressData = useSelector((state: RootState) => state.address.address);
  const placeId = useSelector((state: RootState) => state.farmSelect.placeId)
  const [memo, setMemo] = useState(''); // 메모 상태 추가
  const navigate = useNavigate();
  const [isModalOpen, setIsModalOpen] = useState(true); // Modal open state

  if (addressData) {
    console.log(`addressData: ${addressData}`);
  }
  
  const handleSubmit = async () => {
    if (addressData) {
      const payload = {
        palce : {
        placeId: {placeId},
        address: {
          sido: addressData.sido || null,
          sigungu: addressData.sigungu|| null,
          bname1: addressData.bname1|| null,
          bname2: addressData.bname2|| null,
          jibun: addressData.jibun|| null,
          zonecode: addressData.zonecode|| null,
        }
      }, 
      plant: {
        plantId: {plantId},
        myPlantName: {plantName},
        memo: {memo}
      }
      };

      try {
        const response = await axios.post('/your-endpoint', payload);
        console.log(response.data); // 응답 처리
        onClose(); // 모달 닫기
      } catch (error) {
        console.error("Error posting data:", error);
      }
    }
  };

  const modalVariants = {
    hidden: { opacity: 0, y: 50 },
    visible: { opacity: 1, y: 0 },
    exit: { opacity: 0, y: 50 },
  };

  const handleClose = () => {
    setIsModalOpen(false);
    // onClose(); // Call the original onClose function
  };

  const handleMain = () => {
    navigate('/');
  }

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
        <div className='instruction'>텃밭과 작물을 등록해주세요.</div>
        <div className='box-color'>
          <div className='box-title'>텃밭</div>
          <div className='box-content'>{farmData}</div>
        </div>

        <div className='box-color'>
          <div className='box-title'>작물</div>
          <div className='box-content'>{plantName}</div>
        </div>

        <div className='box-color'>
          <div className='box-title'>위치</div>
          <div className='box-content'>{locationData}</div>
        </div>

        <div className='box-color'>
          <div className='box-title'>메모</div>
          <div className='box-content'>
          <TextField
              id="sample2_address"
              variant="standard"
              inputProps={{ maxLength: 10 }}
              onChange={(e) => setMemo(e.target.value)} // 상태 업데이트
              sx={{
                width: '40%',
              '& .MuiInput-underline:before': {
                borderBottom: 'none', // 비활성화 상태에서의 밑줄 제거
              },
              '& .MuiInput-underline:after': {
                borderBottom: 'none', // 활성화 상태에서의 밑줄 제거
              },
            }}
              style={{ flex: 1, position: 'absolute', left: '45%' }}
            />
          </div>
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
              onClick={async () => {
                await handleSubmit(); // post 요청 보내기
                onClose(); // 모달 닫기
                handleMain(); // 메인 페이지로 이동
              }}
            >
              등록
            </Button>
          </ButtonGroup>
        </div>
      </div>
      </motion.div>
      </Modal>
      </CSSTransition>
  
  );
}

export default GardenFinalModal;
