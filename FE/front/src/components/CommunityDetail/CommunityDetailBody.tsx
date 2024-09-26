import { useEffect, useState } from 'react';
import  '../../styles/CommunityDetail/CommunityDetailBody.css'
import { useParams } from 'react-router-dom';
import { communityDetail } from '../../services/CommunityDetail/CommuniyDetailGet';
import { IsLikePost } from '../../services/CommunityDetail/CommunityDetailPost';
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
import CommunityComment from './CommunityComment';

// DetailData 타입 정의
interface DetailData {
  communityTagList: string[]; 
  // 다른 필드들 추가...
}

function CommunityDetailBody(){

  const { id } = useParams<{ id: string }>(); // id를 string으로 추출 (useParams는 기본적으로 string으로 추출)
  const Id = Number(id);
  const [detailData, setDetailData] = useState<any>(null);
  const [isHeart, setIsHeart] = useState<Boolean>(detailData?.checkIPushHeart)
  const [isHeartCount, setIsHeartCount] = useState<number>(detailData?.communityHeartcount)
  const [isComment, setIsComment] = useState(false)

  const [isOpen, setIsOpen] = useState(false);
  const [startY, setStartY] = useState(0);
  const [translateY, setTranslateY] = useState(0);

  // 댓글 아이콘 클릭 시 모달 열기
  const openModal = () => {
    setIsOpen(true);
  };

  // 모달 닫기
  const closeModal = () => {
    setIsOpen(false);
    setTranslateY(0); // 위치 초기화
  };

  // 터치 시작
  const handleTouchStart = (e: React.TouchEvent) => {
    setStartY(e.touches[0].clientY);
  };

  // 터치 이동
  const handleTouchMove = (e: React.TouchEvent) => {
    const currentY = e.touches[0].clientY;
    const deltaY = currentY - startY;
    if (deltaY > 0) {
      setTranslateY(deltaY); // 스와이프 시 댓글창이 따라 내려옴
    }
  };

  // 터치 종료 (스와이프 내려가면 닫기)
  const handleTouchEnd = () => {
    if (translateY > 100) {
      closeModal(); // 스와이프 거리가 충분히 크면 모달 닫기
    } else {
      setTranslateY(0); // 스와이프가 작으면 원래 위치로 복구
    }
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
            console.log(data)
            setDetailData(data)
        } catch (error) {
            console.log(error)
        }
    };

    getData();

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


const handleCommentClick =() => {
  setIsComment((prevState) => {
    const commentState = !prevState; // 댓글 클릭 상태 토글
    return commentState
  });
}

    return(
        <>
            <div className='community-detail-body-header'>
                {detailData?.imagePath ? <img src={detailData?.imagePath} className='detail-profile-img'/>: <Avatar sx={{ bgcolor: '#5B8E55'}} className='detail-profile-img'>{ detailData?.nickname.charAt(0)}</Avatar>}
                <div className='detail-body-header-profile'>
                    <div style={{fontSize: '1.2rem'}}>{detailData?.nickname}</div>
                    <div style={{color: 'gray', fontSize: '0.8rem'}}>{detailData?.year}.{detailData?.month}.{detailData?.day}</div>
                </div>
                {detailData?.checkMyarticle &&<CreateOutlinedIcon className='article-pencil'/>}
            </div>

            <div className='community-detail-body-body'>
            {detailData?.communityImagePath && detailData.communityImagePath.length > 0 ? (
                 <Slider {...settings} className='carousel'>
                 {detailData?.communityImagePath.map((article:string, index:number) => (
                   <img key={index} src={article} className='article-img' alt={`Article ${index}`} />
                 ))}
               </Slider>
              ) : (
                <img src={empty} className='article-img' alt="이미지 없음"/>
              )}
            </div>

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
              <div className='community-detail-count' onClick={handleHeartClick}>
                {isHeart? <FavoriteIcon/>:<FavoriteBorderIcon/>}
                <div className='count-position'>{isHeartCount}</div>
              </div>

              <div  className='community-detail-count'>
                <ChatBubbleOutlineIcon onClick={handleCommentClick}/>
                <div className='count-position'>{detailData?.communityCommentcount}</div>
              </div>
              {detailData?.checkMyarticle ? (
                    <DeleteIcon />
                  ) : (
                    <div style={{ width: '24px', height: '24px' }} />  // DeleteIcon과 동일한 크기의 빈 공간
                  )}
            </div>


            <div className='comment-modal-size'>
              {isComment? <CommunityComment /> : ''}
            </div>







        </>
    )
}

export default CommunityDetailBody;