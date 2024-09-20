import Modal from 'react-modal'
import '../../styles/RegisterGarden/gardenModal.css'
import ButtonGroup from '@mui/material/ButtonGroup';
import Button from '@mui/material/Button';

interface GardenModalProps {
    placeName: string | null; // placeName은 string 또는 null
    onClose: () => void; // onClose는 함수 타입
}

const customModalStyles: ReactModal.Styles = {
    overlay: {
      backgroundColor: " rgba(0, 0, 0, 0.4)",
      width: "100%",
    //   height: "100vh",
      zIndex: "10",
    //   position: "fixed",
    //   top: "0",
    //   left: "0",
    },
    content: {
      width: "100%",
      height: "40%",
      zIndex: "150",
      position: "absolute",
      top: "80%",
      // bottom: 0,
      left: "50%",
      transform: "translate(-50%, -50%)",
      borderRadius: "10px",
      boxShadow: "2px 2px 2px rgba(0, 0, 0, 0.25)",
      backgroundColor: "white",
      overflow: "auto",
      justifyContent: "center",
    },
  };

function gardenModal({placeName, onClose}:GardenModalProps){
    return(
       <Modal isOpen={true}
       style={customModalStyles}
       onRequestClose={onClose} 
       >
         <>

           <div 
                  style={{ 
                    display: 'flex', 
                    flexDirection: 'column', 
                    height: '100%', 
                    width:'100%'
                  }} // 모달 내용의 flex 레이아웃 설정
                >
                    <div className='instruction'>텃밭에 적합한 작물을 알려드려요</div>
                    <div className='box-color'>
                        <div className='box-title'>텃밭</div>
                        <div className='box-content'>{placeName}</div>
                    </div>
                    <div className='box-color'>
                        <div className='box-title'>위치</div>
                        <div className='box-content'>위치 정보</div>
                    </div>

                    <div style={{ marginTop: 'auto', display:'felx', justifyContent:'center' }}> {/* 이 div가 하단으로 이동 */}
                        <ButtonGroup sx={{ width: '100%', padding:0, margin:0, boxSizing:'border-box'}}>
                            <Button
                                sx={{ backgroundColor: 'white', border: '#E2E2E2', color: '#71717A', padding: '10px 20px', flexGrow: 1, width: '50%' }}
                                onClick={onClose}
                            >
                                취소
                            </Button>
                            <Button
                                sx={{ backgroundColor: '#B0D085', border: '#E2E2E2', color: 'white', padding: '10px 20px', flexGrow: 1, width: '50%' }}
                            >
                                선택완료
                            </Button>
                        </ButtonGroup>
                    </div>
                </div>
        </>
       </Modal>
    )
}


export default gardenModal;