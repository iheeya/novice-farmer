import React, { useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Slide from '@mui/material/Slide';

interface BottomModalProps {
  open: boolean;
  handleClose: () => void; // handleClose 함수의 타입 정의
}

const Transition = React.forwardRef(function Transition(props: any, ref: React.Ref<any>) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const BottomModal: React.FC<BottomModalProps> = ({ open, handleClose }) => {
  return (
    <Dialog
      open={open}
      TransitionComponent={Transition}
      keepMounted
      onClose={handleClose}
    >
      <DialogTitle>모달 제목</DialogTitle>
      <DialogContent>
        <p>모달 내용이 여기에 표시됩니다.</p>
      </DialogContent>
    </Dialog>
  );
};

const App: React.FC = () => {
  const [open, setOpen] = useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div>
      <Button variant="outlined" onClick={handleClickOpen}>
        모달 열기
      </Button>
      <BottomModal open={open} handleClose={handleClose} />
    </div>
  );
};

export default App;
