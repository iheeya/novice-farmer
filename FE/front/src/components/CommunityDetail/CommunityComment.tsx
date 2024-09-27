import React from "react"
import { useEffect, useState } from "react";
import { communityComment } from "../../services/CommunityDetail/CommuniyDetailGet";
import { CommentPost } from "../../services/CommunityDetail/CommunityDetailPost";
import { useParams } from "react-router-dom";
import '../../styles/CommunityDetail/CommunityComment.css'
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Slide from '@mui/material/Slide';
import { TransitionProps } from '@mui/material/transitions';


interface CommunityCommentProps {
    isOpen: boolean; // 모달의 열림/닫힘 상태를 제어하는 값
    onClose: () => void; // 모달을 닫는 함수
  }


function CommunityComment({isOpen, onClose}: CommunityCommentProps){
    const {id} = useParams<{id:string}>();
    const Id = Number(id);
    const [commentList, setCommentList] = useState<any>(null);

    useEffect(() => {
        const getCommentList = async () => {
            if(!id){
                return;
            }

            try {
                const commentData = await communityComment(Id);
                console.log(commentData)
                setCommentList(commentData)
            } catch(e){
                console.log(e)
            }
        };

        getCommentList();
    },[id]);

    const Transition = React.forwardRef(function Transition(
        props: TransitionProps & {
          children: React.ReactElement<any, any>;
        },
        ref: React.Ref<unknown>,
      ) {
        return <Slide direction="up" ref={ref} {...props} />;
      });

    

    return(
     <Dialog
        open={isOpen}
        TransitionComponent={Transition}
        keepMounted
        onClose={onClose}
        aria-describedby="alert-dialog-slide-description"
      >
        <DialogTitle>{"Use Google's location service?"}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-slide-description">
            Let Google help apps determine location. This means sending anonymous
            location data to Google, even when no apps are running.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          {/* <Button onClick={handleClose}>Disagree</Button>
          <Button onClick={handleClose}>Agree</Button> */}
        </DialogActions>
      </Dialog>
    )
}

export default CommunityComment;
