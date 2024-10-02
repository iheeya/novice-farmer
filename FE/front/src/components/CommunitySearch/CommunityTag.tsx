import { useState } from 'react';
import '../../styles/CommunitySearch/CommunityTag.css';
import Data from '../../assets/dummydata/CommunityTag.json';
import Button from '@mui/material/Button';

function CommunityTag() {
    // 태그 데이터를 상태로 저장
    const [tags, setTags] = useState(Data);

    // 버튼 클릭 시 selected 값을 토글하는 함수
    const toggleSelected = (id: number) => {
        setTags(prevTags =>
            prevTags.map(tag =>
                tag.id === id
                    ? { ...tag, selected: !tag.selected } // selected 값을 반전
                    : tag
            )
        );
    };

    return (
        <>
            {/* 나의 태그 출력 */}
            <div className='tag-name'>
                나의 태그
            </div>

            <div className='tag'>
                {tags.filter(tag => tag.selected).map(tag => (
                    <Button
                        key={tag.id}
                        variant="contained"
                        onClick={() => toggleSelected(tag.id)} // 클릭 시 selected 값 반전
                        sx={{ marginRight: '2%', backgroundColor: '#B0D085', marginBottom: '3%' }}
                    >
                        # {tag.tagName}
                    </Button>
                ))}
            </div>

            {/* 인기 태그 출력 */}
            <div className='tag-name'>
               인기 태그 
            </div>
            
            <div className='tag'>
                {tags.filter(tag => tag.popular).map(tag => (
                    <Button
                        key={tag.id}
                        variant={tag.selected ? "contained" : "outlined"} // selected에 따른 스타일 변경
                        onClick={() => toggleSelected(tag.id)} // 클릭 시 selected 값 반전
                        sx={{ marginRight: '2%',marginBottom: '3%',borderColor: '#B0D085', color: tag.selected ? 'white' : '#5B8E55',  backgroundColor: tag.selected? '#B0D085': 'white'}}
                    >
                        # {tag.tagName}
                    </Button>
                ))}
            </div>

            {/* 모든 태그 출력 */}
            <div className='tag-name'>
                모든 태그
            </div>
            
            <div className='tag'>
                {tags.map(tag => (
                    <Button
                        key={tag.id}
                        variant={tag.selected ? "contained" : "outlined"} // selected에 따른 스타일 변경
                        onClick={() => toggleSelected(tag.id)} // 클릭 시 selected 값 반전
                        sx={{ marginRight: '2%', marginTop: '3%', justifyContent: 'center',color: tag.selected ? 'white' : '#5B8E55', borderColor: '#B0D085',backgroundColor: tag.selected? '#B0D085': 'white' }}
                    >
                        # {tag.tagName}
                    </Button>
                ))}
            </div>

            
            {/* 태그 변경 버튼 */}
            <div className='tag-button'>
                <Button
                variant="contained"
                // className='write-button'
                sx={{
                backgroundColor: "#5b8e55",
                color: "white",
                padding: "10px 30px",
                borderRadius: "20px",
                marginTop: '5%'
                }}
                // onClick={handleSubmit}
                >
                태그 변경하기
            </Button>
        </div>
        </>
    );
}

export default CommunityTag;
