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






