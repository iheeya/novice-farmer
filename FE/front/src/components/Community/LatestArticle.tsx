import { getArticle } from "../../services/Community/CommunityGet";
import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Fab from '@mui/material/Fab';
import empty from "../../assets/img/community/empty.png";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import CardActionArea from "@mui/material/CardActionArea";
import "../../styles/CommunitySearch/SearchResult.css";
import loading from '../../assets/img/loading/loading.png'
import { GetImage } from "../../services/getImage";
import { SxProps } from '@mui/system'
import EditIcon from '@mui/icons-material/Edit';
import Avatar from '@mui/material/Avatar';


interface SearchData {
    communityTitle: string;
    communityContent: string;
    communityImage: string[];
    userImagePath: string; // 추가된 필드
    communityId: number; // 게시글 ID
    communityTag: string[];
    userNickname: string;
    communityDate: string;
  }

function LatestArticle(){
    const { search } = useParams<{ search: string }>();
    const [searchData, setSearchData] = useState<SearchData[]>([]);
    const [page, setPage] = useState<number>(0);
    const [hasMore, setHasMore] = useState<boolean>(true);
    const [userImages, setUserImages] = useState<string[]>([]);
    const [communityImages, setCommunityImages] = useState<string[]>([]);
    const navigate = useNavigate();
  
    const getData = async () => {
      if (!hasMore) return; // 더 이상 데이터가 없으면 요청하지 않음
  
      try {
        const data = await getArticle({
          page,
          size: 3,
          filter: "new",
          search: "",
        });
  
        console.log(data.content);
  
        if (data.content.length > 0) {
          setSearchData((prev) => {
            const existingIds = new Set(prev.map(item => item.communityId));
            const newItems = data.content.filter((item: SearchData) => !existingIds.has(item.communityId));
            return [...prev, ...newItems];
          });

          // 유저 프로필 이미지
          const imagePromises = data.content.map((item: SearchData) => GetImage(item.userImagePath));
          const images = await Promise.all(imagePromises);
          setUserImages((prev) => [...prev, ...images]);
          console.log('유저 이미지', images)

          // 커뮤니티 이미지 처리
          // const communityImagePromises = data.content.map((item: SearchData) => {
          //   // 커뮤니티 이미지가 있을 경우 첫 번째 이미지를 가져오고 없으면 빈 문자열 저장
          //   const firstImagePath = item.communityImage.length > 0 ? item.communityImage[0] : '';
          //   return GetImage(firstImagePath);
          // });
          const communityImagePromises = data.content.map((item: SearchData) => {
            // 커뮤니티 이미지가 있을 경우 첫 번째 이미지를 가져오고 없으면 빈 문자열 저장
            const firstImagePath = item.communityImage.length > 0 ? item.communityImage[0] : '';
            return GetImage(firstImagePath);
          });
          // console.log('get 이미지 url', communityImagePromises)
          const communityImages = await Promise.all(communityImagePromises);
          console.log('커뮤니티 이미지', communityImages);
          setCommunityImages((prev) => [...prev, ...communityImages]);
          
        


        } else {
          setHasMore(false);
        }
      } catch (e) {
        console.log(e);
      }
    };

  
    // 페이지가 변경될 때 데이터 가져오기
    useEffect(() => {
      if (hasMore) {
        getData();
      }
    }, [search, page, hasMore]);
  
  
    // 스크롤 이벤트를 감지하여 페이지 증가
    useEffect(() => {
      const handleScroll = () => {
        const bottom = window.innerHeight + window.scrollY >= document.body.offsetHeight;
        if (bottom && hasMore && page === 0) { // 페이지가 0일 때만 API 호출
          setPage((prev) => prev + 1);
        }
      };
  
      window.addEventListener("scroll", handleScroll);
      return () => window.removeEventListener("scroll", handleScroll);
    }, [hasMore]);
  
  
    const handleClick = (id: number) => {
      navigate(`/community/${id}/detail`);
    };

    const handlewrite = () => {
        navigate('/community/article/write')
    }

    const fabStyle = {
        position: 'fixed',
        bottom: '10%',
        right: '5%',
        backgroundColor:'#5B8E55'
      };

    const fab =
        {
          color: 'secondary' as 'secondary',
          sx: fabStyle as SxProps,
          icon: <EditIcon />,
          label: 'Edit',
        };

    return(
         <div className="parent-container">
      {searchData.length > 0 ? (
        searchData.map((item, idx) => (
          <Card
            key={idx}
            sx={{ width: "100%", marginTop: "2%", marginBottom: '3%' }}
            onClick={() => handleClick(item.communityId)}
          >
            <CardActionArea>
            <CardMedia
                component="img"
                height="170"
                image={communityImages[idx] ? communityImages[idx] : empty} // communityImages에서 이미지 가져오기
                alt={item.communityTitle}
            />
              <CardContent>
                <Typography gutterBottom component="div" sx={{ color: "#5B8E55", fontSize: "1.2rem" }}>
                  {item.communityTitle}
                </Typography>
                <Typography variant="body2" color="text.secondary" sx={{ color: "#5B8E55" }}>
                  {item.communityContent ? item.communityContent : ""}
                </Typography>
                <div className="article-explain-bottom">
                  <Avatar src={userImages[idx]} alt="User Image"/>
                  <div className="show-nickname">{item.userNickname}</div>
      
                  <div className="date">
                    {new Date(item.communityDate).toLocaleDateString("ko-KR")}
                  </div>
                </div>
              </CardContent>
            </CardActionArea>
          </Card>
        ))
      ) : (
        <div className="error-instruction">
          <div> 존재하지 않는 페이지 입니다.</div>

          <img
              src={loading}
              className="error-image-size"
          />
        </div> // 에러 메시지 출력
      )}

    <Fab sx={fab.sx} aria-label={fab.label} color={fab.color} onClick={handlewrite}>
        {fab.icon}
    </Fab>
    </div>
    )
}

export default LatestArticle;