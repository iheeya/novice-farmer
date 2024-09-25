import { useEffect, useState } from 'react';
import  '../../styles/CommunityDetail/CommunityDetailBody.css'
import Data from '../../assets/dummydata/CommunityId.json'
import { Carousel } from "@material-tailwind/react";
import { useParams } from 'react-router-dom';
import { communityDetail } from '../../services/CommunityDetail/CommuniyDetailGet';

function CommunityDetailBody(){

  const { id } = useParams<{ id: string }>(); // id를 string으로 추출 (useParams는 기본적으로 string으로 추출)
  const Id = Number(id);
  const [detailData, setDetailData] = useState(null);

  useEffect(() => {
    const getData = async () => {
      if (!id) {
        return; // 데이터 요청 중지
    }
        try {
            const data = await communityDetail(Id);
            setDetailData(data);
            console.log(detailData)
        } catch (error) {
            console.log(error)
        }
    };

    getData();
}, [id]); // id가 변경될 때마다 요청 실행

    return(
        <>
            <div className='community-detail-body-header'>
                <img src={Data.imagePath} className='detail-profile-img'/>
                <div className='detail-body-header-profile'>
                    <div style={{fontSize: '1.2rem'}}>{Data.nickname}</div>
                    <div style={{color: 'gray', fontSize: '0.8rem'}}>{Data.year}.{Data.month}.{Data.day}</div>
                </div>
            </div>

            <div className='community-detail-body-body'>
                {Data.communityImagePath.map(article => (
                    <img src={article} className='article-img'/>
                ))}
                
            </div>

            <div className='community-detail-content'>
              <div className='community-detail-title'>{Data.communityTitle}</div>
              <div>{Data.communityContent}</div>
            </div>

    {/* <Carousel className="rounded-xl">
      <img
        src="https://images.unsplash.com/photo-1497436072909-60f360e1d4b1?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2560&q=80"
        alt="image 1"
        className="h-full w-full object-cover"
      />
      <img
        src="https://images.unsplash.com/photo-1493246507139-91e8fad9978e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2940&q=80"
        alt="image 2"
        className="h-full w-full object-cover"
      />
      <img
        src="https://images.unsplash.com/photo-1518623489648-a173ef7824f3?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2762&q=80"
        alt="image 3"
        className="h-full w-full object-cover"
      />
    </Carousel> */}
        </>
    )
}

export default CommunityDetailBody;