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






