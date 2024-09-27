import axios from 'axios'
import api from '../../utils/axios'

interface CommentPost {
    commentTitle: string,
    commentContent: string,
    imagePath: string[],
    communityTagList: string[],
}


export function CommentPost(Id:number, payload: CommentPost){ 
    return api
        .post('/community', payload)
        .then((response)=>{
            return Promise.resolve()
        })
        .catch((e) => {
            return Promise.reject(e)
        })
}
