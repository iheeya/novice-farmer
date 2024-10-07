import { Box, Tab, Tabs, Button } from "@mui/material";
import { useState } from "react";
import CommunityDetailHeader from "../../components/CommunityDetail/CommunityDetailHeader";
import LatestArticle from "../../components/Community/LatestArticle";
import PopularArticle from "../../components/Community/PopularArticle";

function Community(){


    const [value, setValue] = useState("one");
    const handleChange = (event: React.SyntheticEvent, newValue: string) => {
      setValue(newValue);
    };


    return(    
        <> 
         <CommunityDetailHeader/>

        <Box
            sx={{
                width: "100%", // 화면 전체 너비
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
            }}
            >
            {/* Tabs 컴포넌트 */}
            <Tabs
                value={value}
                onChange={handleChange}
                centered
                TabIndicatorProps={{
                style: {
                    backgroundColor: "#5B8E55", // 선택된 탭 아래의 indicator 색상
                },
                }}
                textColor="inherit" // 기본 탭 텍스트 색상
                sx={{
                "& .Mui-selected": {
                    color: "#5B8E55", // 선택된 탭의 텍스트 색상
                },
                margin: "5%", // 탭과 아래 내용 간격
                justifyContent: "space-around"
                }}
            >
                <Tab value="one" label="최신순" sx={{ fontSize: "1.5rem" }} />
                <Tab value="two" label="인기순" sx={{ fontSize: "1.5rem" }} />
            </Tabs>

            {/* 탭 내용 */}
            <Box sx={{ width: "90%" }}>
                {value === "one" && <LatestArticle />}
                {value === "two" && <PopularArticle />}
            </Box>
      </Box>

    </>   
    
    )
}

export default Community;





