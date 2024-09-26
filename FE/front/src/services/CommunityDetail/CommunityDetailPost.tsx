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


export function CommentPost(Id:number){
    return api
        .post(`community/${Id}/all/comment`)
        .then((response)=>{
            return Promise.resolve()
        })
        .catch((e) => {
            return Promise.reject(e)
        })
}




