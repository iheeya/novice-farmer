import axios from 'axios'
import api from '../../utils/axios'

interface ArticlePost {
    communityTitle: string,
    communityContent: string,
    communityTagList: string[],
}

interface ArticleModifyPost{
    communityTitle: string,
    communityContent: string,
    communityImageSubtractPaths: string[],
    communityTagSubtractList: string[],
    communityTagAddList: string[]
}


export function ArticlePost(payload: ArticlePost){ 
    return api
        .post('/community', payload)
        .then((response)=>{
            return Promise.resolve(response)
        })
        .catch((e) => {
            return Promise.reject(e)
        })
}


export function ArticleImgPost(Id: number, imageFile: FormData) {
    return api
        .post(`/community/image/${Id}`, imageFile, {
            headers: {
                'Content-Type': 'multipart/form-data', // 헤더에 Content-Type 설정
            },
        })
        .then((response) => {
            return Promise.resolve(response.data); // 필요한 경우 응답 데이터 반환
        })
        .catch((e) => {
            return Promise.reject(e);
        });
}


export function ArticleModifyPost(Id: number, payload: ArticleModifyPost){
    return api 
        .post(`community/${Id}/all/modify`, payload)
        .then((response) => {
            return Promise.resolve(response)
        })
        .catch((e) => {
            return Promise.reject(e)
        })
}