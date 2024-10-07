import React, { useEffect, useState } from "react";
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
import { getInfoPlant } from "../../services/Information/InfoApi";
import BookIcon from "../../assets/icons/Book.png";

export default function InfoPlant() {
  useEffect(()=>{
    getInfoPlant()
    .then((res)=>{
      console.log(res)
    })
  },[])
  return <Box>
    <h1>Plant</h1>
  </Box>;
}
