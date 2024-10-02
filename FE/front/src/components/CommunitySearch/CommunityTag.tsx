import { useState, useEffect } from 'react';
import '../../styles/CommunitySearch/CommunityTag.css';
import Button from '@mui/material/Button';
import { ChangeTags } from '../../services/CommunitySearch/CommunitySearchPost';
import { getTags } from '../../services/CommunitySearch/CommunitySearchGet';


function CommunityTag() {
    // 태그 데이터를 상태로 저장
    const [tags, setTags] = useState<any[]>([]);  // 태그 데이터를 상태로 저장
    const [initialTags, setInitialTags] = useState<any[]>([]);  // 초기 태그 데이터 상태
    const [addTags, setAddTags] = useState<number[]>([]);  // 추가된 태그 데이터
    const [removeTags, setRemoveTags] = useState<number[]>([]);  // 삭제된 태그 데이터
    const [tagsData, setTagsData] = useState<any>(null)

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

    // 컴포넌트 마운트 시 태그 데이터 가져오기
    useEffect(() => {
        const fetchTags = async () => {
            try {
                const tagData = await getTags();  // API에서 태그 데이터 가져오기
                setTags(tagData);  // 상태에 태그 데이터 설정
                setInitialTags(tagData);  // 초기 태그 데이터도 설정
            } catch (e) {
                console.log(e);
            }
        };

        fetchTags();
    }, []); // 빈 배열을 사용하여 컴포넌트가 처음 마운트될 때만 호출

    useEffect(()=> {
        const Tags = async() => {
            try{
                const tagData = await getTags();
                console.log(tagData)
            } catch(e) {
                console.log(e)
            }
        }

        Tags();
        // console.log(tagData)
    }, []);

     // 태그 상태가 변경될 때마다 변경된 태그들을 추적하는 함수
     useEffect(() => {
        // tags: 현재 상태를 나타내는 배열, t: initialTags 배열의 각 요소 (초기 상태의 태그)
        const newAddedTags = tags.filter(tag => !initialTags.find(t => t.id === tag.id && t.selected) && tag.selected);
        const newRemovedTags = tags.filter(tag => initialTags.find(t => t.id === tag.id && t.selected) && !tag.selected);

        const addedIds = newAddedTags.map(tag => tag.id);
        const removedIds = newRemovedTags.map(tag => tag.id);
    
        setAddTags(addedIds); // 새롭게 선택된 태그 ID 업데이트
        setRemoveTags(removedIds); // 선택이 해제된 태그 ID 업데이트
    
        // 새로운 상태를 직접 출력
        console.log('addTags', addedIds); // 업데이트된 addTags
        console.log('removeTags', removedIds); // 업데이트된 removeTags
    }, [tags, initialTags]);


      // 태그 변경하기 버튼 클릭 시 POST 요청을 보내는 함수
      const handleSubmit = () => {
        const payload = {
            plustags: addTags.map(id => ({ id })),  // id를 객체 형태로 변환
            deltags: removeTags.map(id => ({ id })), // id를 객체 형태로 변환
        };
    
        console.log("POST 데이터:", payload); // 디버깅을 위해 데이터 확인

        const postTag = async() => {
            try{
                const data = await ChangeTags(payload);
                setTagsData(data)
            } catch (e){
                console.log(e)
            }
        }


        postTag();
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
                onClick={handleSubmit}
                >
                태그 변경하기
            </Button>
        </div>
        </>
    );
}

export default CommunityTag;
