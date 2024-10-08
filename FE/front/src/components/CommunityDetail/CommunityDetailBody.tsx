import { useEffect, useState, useRef } from 'react';
import usenavigate, { useNavigate } from 'react-router-dom'
import  '../../styles/CommunityDetail/CommunityDetailBody.css'
import { useParams } from 'react-router-dom';
import { communityDetail } from '../../services/CommunityDetail/CommuniyDetailGet';
import { IsLikePost } from '../../services/CommunityDetail/CommunityDetailPost';
import { CommentPost } from '../../services/CommunityDetail/CommunityDetailPost';
import { communityComment } from '../../services/CommunityDetail/CommuniyDetailGet';
import { ContentDelete, CommnetDelete } from '../../services/CommunityDetail/CommunityDetailPost';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import ChatBubbleOutlineIcon from '@mui/icons-material/ChatBubbleOutline';
import DeleteIcon from '@mui/icons-material/Delete';
import FavoriteIcon from '@mui/icons-material/Favorite';
import CreateOutlinedIcon from '@mui/icons-material/CreateOutlined';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import Avatar from '@mui/material/Avatar';
import empty from '../../assets/img/community/empty.png'
import sprout from '../../assets/img/community/sprout.png'
import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Slide from '@mui/material/Slide';
import { TransitionProps } from '@mui/material/transitions';
import TextField  from '@mui/material/TextField';
import InputAdornment  from '@mui/material/InputAdornment';
import { Input } from '@mui/material';
import loading from '../../assets/img/loading/loading.png'
import { GetImage } from '../../services/getImage'; 
import Swal from 'sweetalert2'


// DetailData 타입 정의
interface DetailData {
  communityTagList: string[],
  userId: number,
  nickname: string,
  imagePath: string,
  communityTitle: string,
  communityContent: string,
  communityImagePath: string[],
  communityHeartcount: number,
  communityCommentcount: number,
  checkIPushHeart: boolean,
  checkMyarticle: boolean,
  year:string,
  month: string,
  day: string  
}

// 댓글 데이터 타입 정의
interface Comment {
  nickname: string;
  imagePath: string;
  commentContent: string;
  writeDatestring: string;
  myComment: boolean;
  commentId: number;
}

function CommunityDetailBody(){

  const { id } = useParams<{ id: string }>(); // id를 string으로 추출 (useParams는 기본적으로 string으로 추출)
  const Id = Number(id);
  const [detailData, setDetailData] = useState<DetailData|null>(null);
  const [commentData, setCommentData] = useState<Comment[]|null>(null);
  const [isHeart, setIsHeart] = useState<boolean>(detailData?.checkIPushHeart ?? false)
  const [isHeartCount, setIsHeartCount] = useState<number>(detailData?.communityHeartcount??0)
  const commentInputRef = useRef<HTMLInputElement>(null); 
  const [shouldSlide, setShouldSlide] = useState(true); // 슬라이드 애니메이션 여부
  const navigate = useNavigate();
  const [hasError, setHasError] = useState(false); // 에러 상태 추가
  const [imageUrl, setImageUrl] = useState<string[]>([]);
  const [profileUrl, setProfileUrl] = useState<string[]>([]);

  const Transition = React.forwardRef(function Transition(
    props: TransitionProps & {
      children: React.ReactElement<any, any>;
    },
    ref: React.Ref<unknown>,
  ) {
    return <Slide direction="up" ref={ref} {...props} />;
  });
  

  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setShouldSlide(true)
    window.location.reload(); // 페이지 새로 고침
  };


  // 케로셀 세팅
  const settings = {
    dots: true, 
    infinite: false, 
    speed: 500,
    slidesToShow: 1, 
    slidesToScroll:1
  }

  useEffect(() => {
    const getData = async () => {
      if (!id) {
        return; // 데이터 요청 중지
    }
        try {
            const data = await communityDetail(Id);
            if (!data){
              setHasError(true); // 데이터가 없을 경우 에러 상태 설정
            } else{
              console.log(data)
              setDetailData(data)
            
            }
        } catch (error) {
            console.log(error)
            setHasError(true); // 500 에러 처리
        }
    };


    getData();
    getComment();

    console.log('isHeart:', isHeart)



}, [id]); // id가 변경될 때마다 요청 실행



useEffect(() => {
  if (detailData) {
    setIsHeart(detailData.checkIPushHeart); // detailData가 업데이트될 때 isHeart를 설정
    setIsHeartCount(detailData.communityHeartcount); // detailData가 업데이트될 때 isHeartCount를 설정
  }
}, [detailData]); // detailData가 변경될 때마다 실행

const handleHeartClick = () => {
  setIsHeart((prevState) => {
    const newState = !prevState; // 하트 상태 토글
    const newCount = newState ? isHeartCount + 1 : isHeartCount - 1; // 카운트 업데이트
    setIsHeartCount(newCount); // 하트 개수 업데이트
    return newState; // 새로운 하트 상태 반환
  });
  
  const postLike = async() => {
    try{
      const data =await IsLikePost(Id);
      console.log('응답데이터: ',data)
    } catch (e){
      console.log(e)
    }
  }

  postLike();
};

const handleDeleteClick = () => {
  deleteContent()

  Swal.fire({
    icon: "success",
    title: "게시글이 삭제되었습니다.",
    showConfirmButton: false,
    timer: 1500,
    customClass: {
      title: 'custom-title' // 사용자 정의 클래스 추가
    }
  }).then(() => {
    navigate('/community', {replace: true})
  })
}




const deleteContent = async() => {
  try{
    const data = await ContentDelete(Id)
    console.log('응답 데이터', data)
  } catch (e) {
    console.log(e)
  }
}

// 댓글 삭제 api
const handleCommentDelete = async (commentId: number) => {
  const confirmation = await Swal.fire({
      // title: "댓글 삭제",
      text: "댓글을 삭제하시겠습니까?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#5B8E55",
      cancelButtonColor: "#B0D085",
      confirmButtonText: "삭제",
      cancelButtonText: "취소",
  });

  if (confirmation.isConfirmed) {
      try {
          const data = await CommnetDelete(Id, commentId);
          console.log('댓글 삭제', data);
          setShouldSlide(false);
          await getComment(); // 댓글 목록을 새로 고침

         
      } catch (e) {
          console.log(e);
      }
  }
};


const getComment = async ()=>{
  if(!id){
    return
  }

  try{
    const data = await communityComment(Id);
    console.log('댓글 데이터',data)
    setCommentData(data)
  } catch (e) {
    console.log(e)
  }
}
 
const handleCommentPost = async () => {
  const commentContent = commentInputRef.current?.value; // ref를 통해 입력값 가져오기
  if (!commentContent) return; // 빈 입력값이면 아무 작업도 하지 않음

  const payload = {
    "commentContent": commentContent,
  };

  try {
    const response = await CommentPost(Id, payload);
    commentInputRef.current.value = ''; // 댓글 작성 후 입력창 비우기
    
    setShouldSlide(false);
    await getComment(); 

  } catch (e) {
    console.log(e);
  }
};

// 이미지 저장
useEffect(() => {
  const fetchImages = async () => {
    if (detailData?.communityImagePath && detailData.communityImagePath.length > 0) {
      try {
        const urls = await Promise.all(
          detailData.communityImagePath.map((imagePath) => GetImage(imagePath))
        );
        setImageUrl(urls);
      } catch (err) {
        console.error("Failed to fetch image URLs:", err);
      }
    }
  };

  fetchImages();
}, [detailData]);



// 프로필 이미지 저장
useEffect(() => {
const fetchImages = async () => {
    if (detailData?.imagePath) { // imagePath가 문자열일 경우
        try {
            const url = await GetImage(detailData.imagePath);
            setProfileUrl([url]); // 단일 URL을 배열로 설정
        } catch (err) {
            console.error("Failed to fetch image URL:", err);
        }
}
  };

fetchImages();
}, [detailData]);



if (hasError) {
  return <div className="error-instruction">
  <div> 존재하지 않는 페이지 입니다.</div>

  <img
      src={loading}
      className="error-image-size"
  />
</div>; // 에러 메시지 출력
}

const handleRewrite = () => {
  navigate(`/community/${Id}/modify`)
}


    return(
        <>
            <div className='community-detail-body-header'>
                {detailData?.imagePath ? <Avatar src={profileUrl[0]} alt={detailData?.nickname}/>: <Avatar sx={{ bgcolor: '#5B8E55'}}>{ detailData?.nickname.charAt(0)}</Avatar>}
                <div className='detail-body-header-profile'>
                    <div style={{fontSize: '1.2rem'}}>{detailData?.nickname}</div>
                    <div style={{color: 'gray', fontSize: '0.8rem'}}>{detailData?.year}.{detailData?.month}.{detailData?.day}</div>
                </div>
                {detailData?.checkMyarticle &&<CreateOutlinedIcon className='article-pencil' onClick={handleRewrite}/>}
            </div>

            <div className='community-detail-body-body'>
              {/* 이미지 케로셀 */}
                {imageUrl.length > 0 ? (
                    <Slider {...settings} className='carousel'>
                      {imageUrl.map((url: string, index: number) => (
                        <img key={index} src={url} className='article-img' alt={`Article ${index}`} />
                      ))}
                    </Slider>
                  ) : (
                    <img src={empty} className='article-img' alt="이미지 없음" />
                  )}
            </div>

              {/* 커뮤니티 게시글 내용 */}
            <div className='community-detail-content'>
              <div className='community-detail-title'>{detailData?.communityTitle}</div>
              <div>{detailData?.communityContent}</div>
              <div>
                {detailData?.communityTagList.map((tag: string, index: number) => (
                  <div key={index}>#{tag}</div> // key 속성 추가
                ))}
            </div>
            </div>


            <div className='community-detail-body-footer'>
              {/* 좋아요 아이콘 */}
              <div className='community-detail-count' onClick={handleHeartClick}>
                {isHeart? <FavoriteIcon/>:<FavoriteBorderIcon/>}
                <div className='count-position'>{isHeartCount}</div>
              </div>

                {/* 댓글 아이콘 */}
              <div  className='community-detail-count'>
              
              {/* 댓글 모달 */}
              <React.Fragment>
                <ChatBubbleOutlineIcon onClick={handleClickOpen}/>
                <div className='count-position'>{detailData?.communityCommentcount}</div>
                <Dialog
                  open={open}
                  TransitionComponent={shouldSlide ?Transition : undefined}
                  keepMounted
                  onClose={handleClose}
                  className='modal-size'
                  aria-describedby="alert-dialog-slide-description"
                  PaperProps={{
                    style: {
                      position: 'fixed',
                      bottom: 0,
                      margin: 0,
                      width: '100%',
                      height: '80%',
                      maxWidth: '100%',
                    },
                  }}
                >
                  <DialogTitle>{"댓글"}</DialogTitle>
                  <DialogContent>
                    <DialogContentText id="alert-dialog-slide-description">
                      {/* 댓글 목록 로딩 */}
                      {commentData?.map((comment, index) => (
                        <div className='comment'>
                          <div key={index} className="comment-item">
                            {/* 유저 프로필 이미지 */}
                            {comment.imagePath ? <Avatar src={comment.imagePath} alt={comment.nickname} sx={{ marginRight: '0.5rem' }} />: <Avatar sx={{ bgcolor: '#5B8E55', marginRight: '0.5rem'}}>{ comment.nickname.charAt(0)}</Avatar>}
                          <div>
                              <div style={{ fontWeight: 'bold', display:'flex', alignItems:'center'}}>
                                {comment.nickname}
                                {comment.myComment ? (
                                <DeleteIcon onClick={()=>handleCommentDelete(comment.commentId)}/>
                              ) : (
                                <div style={{ width: '24px', height: '24px' }} />  // DeleteIcon과 동일한 크기의 빈 공간
                              )}
                              </div>
                              <div style={{ color: 'gray', fontSize: '0.8rem' }}>{comment.writeDatestring}</div>
                            </div>
                          </div>
                          <div className='comment-content'>{comment.commentContent}</div>
                          </div>
                        ))}
                    </DialogContentText>
                   
                  </DialogContent>

                  {/* 댓글 입력 창 */}
                  <div className='comment-input'>
                  <DialogActions>
                       {/* 댓글 입력 창 옆 프로필 본인 프로필 사진으로 바꾸기 */}
                    <Avatar sx={{ bgcolor: "#D2EABD"}}> {/* 간격 조정 */}
                      <img src={sprout} alt="Sprout" className='avatar-img' />
                    </Avatar>
                          
                    <TextField
                      inputRef={commentInputRef} // ref 연결
                      sx={{paddingLeft: '0.2rem', paddingY: '0.5rem'}}
                      placeholder="댓글을 입력해주세요."
                      fullWidth
                      variant='outlined'
                      onKeyPress={(e) => {
                        if (e.key === 'Enter') {
                          handleCommentPost();
                        }
                      }}
                      InputProps={{
                        endAdornment : (
                          <InputAdornment position='end'>
                            <Button sx={{fontWeight:'bold', color:'#5B8E55'}} onClick={handleCommentPost}>작성</Button>
                          </InputAdornment>
                        )
                      }} 
                      />
                    
                  </DialogActions>
                  </div>
                </Dialog>
              </React.Fragment>
              </div>

              {/* 삭제 아이콘 */}
              {detailData?.checkMyarticle ? (
                    <DeleteIcon  onClick={handleDeleteClick}/>
                  ) : (
                    <div style={{ width: '24px', height: '24px' }} />  // DeleteIcon과 동일한 크기의 빈 공간
                  )}
            </div>
        </>
    )
}

export default CommunityDetailBody;