import React, { useState } from "react";
import { Box, Typography, List, ListItem, ListItemIcon, ListItemText, Button, Dialog, DialogTitle, DialogContent, DialogActions } from "@mui/material";
import BookIcon from "@mui/icons-material/Book";

type InfoDataType = {
  justice: { title: string; comment: string; content: string };
  purpose: { title: string; comment: string; content: string };
  effect: { title: string; comment: string; content: string };
  placeType: { title: string; comment: string; content: string };
};

export default function InfoPlace() {
  const dummy: InfoDataType = {
    justice: {
      title: "텃밭이란?",
      comment: "텃밭은 무엇을 말하는 걸까요?",
      content: "작은 공간에서 채소와 허브 등 식물을 키울 수 있는 소규모 농장 형태의 재배 공간",
    },
    purpose: {
      title: "텃밭의 목적",
      comment: "텃밭을 왜 가꿔야 하나요?",
      content: "자연과 가까워지고, 건강한 먹거리를 스스로 재배하는 것이 주요 목적",
    },
    effect: {
      title: "텃밭의 효과",
      comment: "텃밭을 가꿨을 때의 효과는?",
      content: "스트레스 해소, 정서 안정, 건강한 식생활",
    },
    placeType: {
      title: "텃밭의 종류",
      comment: "텃밭의 종류를 알아보세요",
      content: "텃밭의 종류는 학교 텃밭, 도시 텃밭, 가정 텃밭 등이 있습니다.",
    },
  };

  // 명시적으로 dummy의 키 타입 정의
  const infoList = (Object.keys(dummy) as Array<keyof InfoDataType>).map((key) => dummy[key]);

  // 모달 상태 관리
  const [open, setOpen] = useState(false);
  const [selectedInfo, setSelectedInfo] = useState<InfoDataType[keyof InfoDataType] | null>(null);

  // 모달 열기 핸들러
  const handleClickOpen = (info: InfoDataType[keyof InfoDataType]) => {
    setSelectedInfo(info);
    setOpen(true);
  };

  // 모달 닫기 핸들러
  const handleClose = () => {
    setOpen(false);
    setSelectedInfo(null);
  };

  return (
    <Box
      sx={{
        width: "100%",
        maxWidth: 360,
        bgcolor: "background.paper",
        margin: "0 auto", // 가운데 정렬
        padding: 2,
      }}
    >
      <Typography variant="h5" sx={{ textAlign: "center", mb: 2 }}>
        정보
      </Typography>
      <List>
        {infoList.map((info, index) => (
          <ListItem
            key={index}
            sx={{ borderBottom: "1px solid #ddd", cursor: "pointer" }}
            onClick={() => handleClickOpen(info)}
          >
            <ListItemIcon>
              <BookIcon color="primary" />
            </ListItemIcon>
            <ListItemText primary={info.title} secondary={info.comment} />
          </ListItem>
        ))}
      </List>

      {/* 모달 컴포넌트 */}
      <Dialog open={open} onClose={handleClose} fullWidth maxWidth="sm">
        <DialogTitle>{selectedInfo?.title}</DialogTitle>
        <DialogContent>
          <Typography variant="body1">{selectedInfo?.content}</Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} variant="contained" color="success">
            닫기
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}
