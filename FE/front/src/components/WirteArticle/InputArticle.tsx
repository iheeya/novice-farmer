import '../../styles/WriteArticle/InputArticle.css'
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

function InputArticle(){
    return(
         <div className="input-container">

            <TextField
            id="outlined-textarea"
            label="제목"
            placeholder="제목을 입력해주세요."
            multiline
            className='article-title'
            sx = {{backgroundColor: "#F8FAF8"}}
            />

            <TextField
            id="outlined-textarea"
            label="내용"
            placeholder="나누고 싶은 작물 이야기를 적어보세요."
            multiline
            rows={6}
            className='article-content'
            sx = {{backgroundColor: "#F8FAF8"}}
            />

            <TextField
            id="outlined-textarea"
            label="태그"
            placeholder="태그를 입력해주세요."
            multiline
            className='article-tag'
            sx = {{backgroundColor: "#F8FAF8"}}
            />
            

            <Button
            variant="contained"
            className='write-button'
            sx={{
            backgroundColor: "#5b8e55",
            color: "white",
            padding: "10px 30px",
            borderRadius: "20px",
            }}>
            등록
        </Button>
      </div>
   
    )
}

export default InputArticle;