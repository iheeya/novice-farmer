import '../../styles/WriteArticle/InputArticle.css'
import { useState } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Chip from '@mui/material/Chip';
import Alert from '@mui/material/Alert';
import CameraAltOutlinedIcon from '@mui/icons-material/CameraAltOutlined';
import InsertPhotoOutlinedIcon from '@mui/icons-material/InsertPhotoOutlined';
import Swal from 'sweetalert2'

function InputArticle(){
  const [inputValue, setInputValue] = useState<string>('');
  const [tags, setTags] = useState<string[]>([]);
  const [hasError, setHasError] = useState<boolean>(false)
  const [title, setTitle] = useState<string>('')
  const [content, setContent] = useState<string>('')

  // 입력 값 변경 시 호출
  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;

    // 해시태그(#)가 포함되지 않도록 필터링
    if (!value.includes('#')) {
      setInputValue(value);
    } else{
      setHasError(false)
    }
  };

  // 스페이스바 + 엔터키를 눌렀을 때 태그로 변환
  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === ' ' || event.key==='Enter') {
      event.preventDefault(); // 스페이스바로 인한 기본 입력 막음

      // 입력값이 존재할 때만 태그 추가
      if (inputValue.trim()) {
        setTags([...tags, `#${inputValue.trim()}`]); // 태그에 '#' 추가
        setInputValue(''); // 입력 필드 비우기
      }
    }
  };

  // 태그 삭제 처리
  const handleDeleteTag = (tagToDelete:string) => {
    setTags(tags.filter((tag) => tag !== tagToDelete));
  };



  // 제목 입력 처리
  const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
  };

  // 내용 입력 처리
  const handleContentChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setContent(event.target.value);
  };

  // 등록 버튼 클릭 시 payload 생성
  const handleSubmit = () => {
    const formattedTags = tags.map((tag) => tag.replace('#', ''));  // 태그에서 # 제거
    const payload = {
      communityTitle: title,
      communityContent: content,
      imagePath: "imagePath",  // 여기에 이미지 경로가 들어가야 함
      communityTagList: formattedTags
    };

    setInputValue('');
    setTags([]);
    setTitle('');
    setContent('');
    setHasError(false); // 에러 상태 초기화

    console.log(payload)

     // SweetAlert 표시
     Swal.fire({
      icon: "success",
      title: "게시글이 등록되었습니다.",
      showConfirmButton: false,
      timer: 1500,
      customClass: {
        title: 'custom-title' // 사용자 정의 클래스 추가
      }
    });
  }



    return(
         <div className="input-container">
            
            <TextField
            id="outlined-textarea"
            label="제목"
            placeholder="제목을 입력해주세요."
            multiline
            className='article-title'
            value={title} // 제목 필드에 상태 연결
            onChange={handleTitleChange}  // 제목 입력값 변화 감지
            sx = {{backgroundColor: "#F8FAF8", marginTop: '10%'}}
            />

            <TextField
            id="outlined-textarea"
            label="내용"
            placeholder="나누고 싶은 작물 이야기를 적어보세요."
            multiline
            rows={6}
            value={content}
            className='article-content'
            onChange={handleContentChange}
            sx = {{backgroundColor: "#F8FAF8", marginTop: '5%'}}
            />

          {/* 카메라, 이미지 접근 */}
          <div className='input-camera'>
            <div className='input-box'>
              <CameraAltOutlinedIcon sx={{fontSize: '3rem', color: 'gray'}}/>
            </div>
            <div className='input-box'>
              <InsertPhotoOutlinedIcon sx={{fontSize: '3rem', color: 'gray'}}/>
            </div>
          </div>

          {/* tag 출력 */}
          <div style={{ justifyContent:'center', marginTop: '10px', display: 'flex', flexWrap: 'wrap', gap: '10px'}}>
              {tags.map((tag, index) => (
                <Chip
                  key={index}
                  label={tag}
                  onDelete={() => handleDeleteTag(tag)}  // 태그 삭제 처리
                  color="primary"
                  sx={{ borderRadius: '20px', backgroundColor: "#B0D085" }}  // 원통 모양
                />
              ))}
            </div>

          <TextField
              id="outlined-textarea"
              label="태그"
              placeholder="태그를 입력해주세요."
              multiline
              className='article-tag'
              value={inputValue}
              onChange={handleInputChange}  // 입력 값 변화 감지
              onKeyDown={handleKeyDown}  // 스페이스바 이벤트 감지
              sx={{ backgroundColor: "#F8FAF8" , marginTop: '5%'}}
            />

          {hasError && (
                  <Alert className='input-alert' severity="error">
                    '#'은 입력할 수 없습니다.
                  </Alert>
                )}
                      
         

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
            등록
        </Button>
      </div>
   
    )
}

export default InputArticle;