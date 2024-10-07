import { useEffect, useState } from "react";
import {
  Box,
  Typography,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  CircularProgress,
} from "@mui/material";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos"; // 오른쪽 화살표 아이콘
import { useNavigate } from "react-router-dom";
import { getInfoPlace } from "../../services/Information/InfoApi";
import BookIcon from "../../assets/icons/Book.png";

interface InfoDataProps {
  justice: { title: string; comment: string; content: string };
  purpose: { title: string; comment: string; content: string };
  effect: { title: string; comment: string; content: string };
  placeType: { title: string; comment: string; content: string };
}

export default function InfoPlace() {
  // 데이터 상태 관리
  const [infoData, setInfoData] = useState<InfoDataProps | null>(null);
  const [open, setOpen] = useState(false);
  const [selectedInfo, setSelectedInfo] = useState<InfoDataProps[keyof InfoDataProps] | null>(null);

  const navigate = useNavigate();

  // API 데이터 받아오기
  useEffect(() => {
    getInfoPlace()
      .then((response) => {
        setInfoData(response); // API 응답 데이터 설정
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }, []);

  // 모달 열기 핸들러
  const handleClickOpen = (info: InfoDataProps[keyof InfoDataProps]) => {
    if (info.title === "텃밭의 종류") {
      navigate("/info/place/type");
    } else {
      setSelectedInfo(info);
      setOpen(true);
    }
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
        paddingTop: 2,
      }}
    >
      {/* 데이터가 있을 때만 리스트 렌더링 */}
      {infoData ? (
        <List>
          {Object.keys(infoData).map((key, index) => {
            const info = infoData[key as keyof InfoDataProps];
            return (
              <ListItem
                key={index}
                sx={{
                  borderBottom: "1px solid #ddd",
                  cursor: "pointer",
                  my: 1, // 리스트 아이템 사이의 간격 추가
                  alignItems: "center", // 아이콘과 텍스트를 수직 중앙 정렬
                }}
                onClick={() => handleClickOpen(info)}
              >
                <ListItemIcon>
                  <img src={BookIcon} width="40" height="40" alt="책아이콘" />
                </ListItemIcon>
                <ListItemText
                  primary={info.title}
                  secondary={info.comment}
                  primaryTypographyProps={{ 
                    fontSize: "1.3rem", // 제목 텍스트 크기 설정
                    fontWeight: "bold", // 제목 텍스트 굵기 설정
                    color: "#333", // 제목 색상
                  }}
                  secondaryTypographyProps={{
                    fontSize: "0.98rem", // 보조 텍스트 크기 설정
                    color: "#777", // 보조 텍스트 색상 설정
                  }}
                  sx={{ mr: 2  }} // 오른쪽 화살표와 간격 조정
                />
                <ArrowForwardIosIcon sx={{ color: "#bbb" }} /> {/* 오른쪽 화살표 아이콘 */}
              </ListItem>
            );
          })}
        </List>
      ) : (
        <Typography variant="body1" sx={{ textAlign: "center", mt: 4 }}>
          <CircularProgress color="success"/>
        </Typography>
      )}

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
