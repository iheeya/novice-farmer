import axios from 'axios'
import api from '../../utils/axios'

export function IsLikePost(Id:number){
    return api
        .post(`community/${Id}`)
        .then((response) => {
            return Promise.resolve()
        })
        .catch((e) => {
            return Promise.reject(e)
        })
}


interface CommentPost {
    commentContent: string
}

export function CommentPost(Id:number, payload: CommentPost){ 
    return api
        .post(`community/${Id}/all/comment`, payload)
        .then((response)=>{
            return Promise.resolve()
        })
        .catch((e) => {
            return Promise.reject(e)
        })
}


export function ContentDelete(Id:number){
    return api
        .delete(`/community/${Id}`)
        .then((response) => {
            return Promise.resolve()
        })
        .catch((e) => {
            return Promise.reject(e)
        })
}


export function CommnetDelete(Id: number, commentId: number){
    return api
        .delete(`community/${Id}/all/comment/${commentId}`)
        .then((response) => {
            return Promise.resolve(response)
        })
        .catch((e) => {
            return Promise.reject(e)
        })
}
