import axios from 'axios'
import api from '../../utils/axios'

export function communityDetail(Id:number){
    return api
        .get(`community/${Id}`)
        .then((response) => {
            return Promise.resolve(response.data)
        })
        .catch((e) => {
            return Promise.reject(e)
        })
}

export function communityComment(Id: number){
    return api
        .get(`community/${Id}/all/comment`)
        .then((response) => {
            return Promise.resolve(response.data)
        })
        .catch((e) => {
            return Promise.reject(e)
        })
}




