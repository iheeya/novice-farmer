import { getSearchResult } from "../../services/CommunitySearch/CommunitySearchGet";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import empty from "../../assets/img/community/empty.png";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import CardActionArea from "@mui/material/CardActionArea";
import "../../styles/CommunitySearch/SearchResult.css";

function SearchResult() {
  const { search } = useParams<{ search: string }>();
  const [searchData, setSearchData] = useState<any[]>([]);
  const [page, setPage] = useState<number>(0);
  const [hasMore, setHasMore] = useState<boolean>(true); // 더 많은 데이터가 있는지 확인하는 상태

  useEffect(() => {
    const getData = async () => {
      if (!hasMore) return; // 더 이상 데이터가 없으면 요청하지 않음

      try {
        const data = await getSearchResult({
          page,
          size: 2,
          filter: "new",
          search: search || "",
        });
        console.log(data.content);
        if (data.content.length > 0) {
          setSearchData(data.content);
        } else {
          setHasMore(false); // 더 이상 데이터가 없으면 hasMore를 false로 설정
        }
      } catch (e) {
        console.log(e);
      }
    };

    getData(); // getData 호출
  }, [search, page]); // page가 변경될 때도 데이터를 가져오도록 설정

  return (
    <div className="parent-container">
      {searchData.length > 0 ? (
        searchData.map((item, idx) => (
          <Card key={idx} sx={{ width: "90%", marginTop: "5%" }}>
            <CardActionArea>
              {/* 이미지가 있으면 표시, 없으면 기본 이미지 */}
              <CardMedia
                component="img"
                height="170"
                image={
                  item.communityImagePath?.length > 0
                    ? item.communityImagePath[0]
                    : empty
                }
                alt={item.communityTitle}
              />
              <CardContent>
                <Typography
                  gutterBottom
                  component="div"
                  sx={{ color: "#5B8E55", fontSize: "1.2rem" }}
                >
                  {item.communityTitle}
                </Typography>
                <Typography
                  variant="body2"
                  color="text.secondary"
                  sx={{ color: "#5B8E55" }}
                >
                  {/* 설명을 간략히 노출 (설명이 있다면) */}
                  {item.communityContent ? item.communityContent : ""}
                </Typography>
              </CardContent>
            </CardActionArea>
          </Card>
        ))
      ) : (
        <div>검색 결과가 없습니다.</div> // 검색 결과가 없는 경우
      )}
    </div>
  );
}

export default SearchResult;
