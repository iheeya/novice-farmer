import { useParams } from "react-router-dom";
import { ArticleGet } from "../../services/CommunityArticle/ArticleGet";
import { useEffect, useState, useRef } from "react";
import { useNavigate } from 'react-router-dom';
import TextField from '@mui/material/TextField';
import '../../styles/WriteArticle/InputArticle.css'
import Button from '@mui/material/Button';
import Chip from '@mui/material/Chip';
import Alert from '@mui/material/Alert';
import CloseIcon from '@mui/icons-material/Close'; 
import CameraAltOutlinedIcon from '@mui/icons-material/CameraAltOutlined';
import InsertPhotoOutlinedIcon from '@mui/icons-material/InsertPhotoOutlined';
import Swal from 'sweetalert2'
import CircularProgress from '@mui/material/CircularProgress';
import { GetImage } from "../../services/getImage";

interface ArticleModify{
    communityTitle: string,
    communityContent: string,
    communityImagePath: string[],
    communityTagList: string[]
}

function ReWriteArticle(){
    const {id} = useParams<{id: string}>();
    const Id = Number(id);
    const [modifyData, setModifyData] = useState<ArticleModify>();
    const [articleInfo, setArticleInfo] = useState<ArticleModify| null>(null);
    const [inputValue, setInputValue] = useState<string>('');
    const [tags, setTags] = useState<string[]>([]);
    const [hasError, setHasError] = useState<boolean>(false)
    const [title, setTitle] = useState<string>('')
    const [content, setContent] = useState<string>('')
    const [isTag, setIsTag] = useState<boolean>(false)
    const [lenError, setLenError] = useState<boolean>(false)
    const [imageUrl, setImageUrl] = useState<string[]>([]);

    const galleryInputRef = useRef<HTMLInputElement | null>(null); // 갤러리 input 참조
  
    const [imageFiles, setImageFiles] = useState<File[]>([]);  // 여러 이미지 파일을 저장하는 상태
    const [imagePreviewUrls, setImagePreviewUrls] = useState<string[]>([]);  // 미리보기 URL 배열
    const navigate = useNavigate();

    // 입력 값 변경 시 호출
  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;

    // 해시태그(#)가 포함되지 않도록 필터링
    if (!value.includes('#') && value.length <= 7) {
      setInputValue(value);
      setHasError(false)
      setLenError(false)
      setIsTag(false)
    } else if(value.length > 7){
        setLenError(true)
    } 
    else{
      setHasError(true)
    }
  };

    // 스페이스바 + 엔터키를 눌렀을 때 태그
    const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key === ' ' || event.key === 'Enter') {
          event.preventDefault(); // 스페이스바로 인한 기본 입력 막음
    
          const newTag = `#${inputValue.trim()}`;
          // 입력값이 존재하고 중복되지 않을 때만 태그 추가
          if (inputValue.trim() && inputValue.length <= 7 && !tags.includes(newTag)) {
            setTags([...tags, newTag]); // 태그에 '#' 추가
            setInputValue(''); // 입력 필드 비우기
          } else if (tags.includes(newTag)) {
            // 중복 시 SweetAlert 표시
            setIsTag(true)
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

    // 이미지 삭제 처리 함수
    const handleDeleteImage = (index: number) => {
        // 선택한 이미지와 미리보기 URL을 배열에서 제거
        setImageFiles(prevFiles => prevFiles.filter((_, i) => i !== index));
        setImagePreviewUrls(prevUrls => prevUrls.filter((_, i) => i !== index));
      };

      const hadleGallertClick = () => {
        if (galleryInputRef.current) {
          galleryInputRef.current.click(); // 파일 input 클릭하여 갤러리 접근
        }
      }
    
      

    
    //  내가 쓴글 가져오기
    useEffect(() => {
        const getData = async() => {
            try{
                const data = await ArticleGet(Id);
                setModifyData(data)
                const formattedTags = data.communityTagList.map((tag:string) => `#${tag}`);
                setTags(formattedTags || []);
                // console.log(data)
            } catch (e) {
                console.log(e)
            }
        }

        if (Id){
            getData();
        }
    }, [Id])


    // 이미지 저장
    useEffect(() => {
        const fetchImages = async () => {
        if (modifyData?.communityImagePath && modifyData.communityImagePath.length > 0) {
            try {
            const urls = await Promise.all(
                modifyData.communityImagePath.map((imagePath) => GetImage(imagePath))
            );
            setImageUrl(urls);
            } catch (err) {
            console.error("Failed to fetch image URLs:", err);
            }
        }
        };
    
        fetchImages();
    }, [modifyData]);

    return(
            <div className="input-container">
            {modifyData ? (
                <>
                <TextField
                    required
                    id="outlined-required"
                    label="제목"
                     className='article-title'
                    defaultValue={modifyData.communityTitle} // modifyData가 있을 때만 값 설정
                    onChange={handleTitleChange}  // 제목 변경 시 상태 업데이트
                    sx={{ backgroundColor: "#F8FAF8", marginTop: '10%' }}
                />

                <TextField
                    required
                    id="outlined-required"
                    label="내용"
                    className='article-title'
                    multiline
                    rows={6}
                    defaultValue={modifyData.communityContent} // modifyData가 있을 때만 값 설정
                    onChange={handleTitleChange}  // 제목 변경 시 상태 업데이트
                    sx={{ backgroundColor: "#F8FAF8", marginTop: '10%' }}
                />


                {/* 이미지 미리보기 */}
                {imagePreviewUrls.length > 0 && (
                    <div style={{ marginTop: '5%', display: 'flex', flexWrap: 'wrap', justifyContent: 'center', marginLeft: '5%' }}>
                    {imagePreviewUrls.map((url, index) => (
                        <div key={index} style={{ display: 'inline-block', margin: '5px', position: 'relative' }}>
                        <img 
                            src={url} 
                            alt={`미리보기 ${index + 1}`} 
                            style={{ objectFit: 'contain', width:'100px' }} // img에 position: relative 추가
                        />
                        <CloseIcon 
                            onClick={() => handleDeleteImage(index)} 
                            style={{
                            position: 'absolute',
                            width: '15px',
                            height: '15px',
                            cursor: 'pointer',
                            color: 'red',
                            right: '4px',
                            top: '5px',
                            zIndex: 10 // 아이콘을 이미지 위로 올리기 위해 z-index 추가
                            }}
                        />
                        </div>
                    ))}
                    </div>
                )}
      


                {/* 카메라, 이미지 접근 */}
                <div className='input-camera'>
                    <div className='input-box'>
                    <InsertPhotoOutlinedIcon sx={{fontSize: '3rem', color: 'gray'}} onClick={hadleGallertClick}/>
                    </div>
                </div>


                 {/*  갤러리 접근을 위한 숨겨진 INPUT */}
                <input
                    type="file"
                    accept="image/*"
                    multiple // 여러 이미지를 선택할 수 있도록 허용
                    ref={galleryInputRef}
                    style={{ display: 'none' }} // 숨김
                    onChange={(event) => {
                    const files = event.target.files;
                    if (files) {
                        const selectedFiles = Array.from(files); // 파일들을 배열로 변환

                        // 현재 이미지 파일 수와 선택된 파일 수를 합쳐서 11장 이하인지 확인
                        if (imageFiles.length + selectedFiles.length <= 11) {
                        // 새로운 이미지 파일들을 배열에 추가
                        setImageFiles((prevFiles) => [...prevFiles, ...selectedFiles]);

                        // 새로운 미리보기 URL들을 배열에 추가
                        const newImageUrls = selectedFiles.map((file) => URL.createObjectURL(file));
                        setImagePreviewUrls((prevUrls) => [...prevUrls, ...newImageUrls]);
                        } else {
                        Swal.fire({
                            icon: "error",
                            title: "이미지는 최대 11장까지 첨부할 수 있습니다.",
                            showConfirmButton: false,
                            timer: 1500,
                                });
                                }
                            }
                            }}
                        />

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
          
          {isTag && (
                  <Alert className='input-alert' severity="error">
                    이미 존재하는 태그입니다.
                  </Alert>
                )}

          {lenError && (
                  <Alert className='input-alert' severity="error">
                    태그 길이는 7자를 넘길 수 없습니다.
                  </Alert>
                )}
                      





                </>
                
            ) : (
                <CircularProgress /> 
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
            // onClick={handleSubmit}
            >
            수정
        </Button>
            </div>
    )
}

export default ReWriteArticle;