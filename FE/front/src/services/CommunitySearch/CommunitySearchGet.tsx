import axios from 'axios'
import api from '../../utils/axios'

export function getTags(){
    return api
    .get('/community/tags/all')
    .then((response) => {
        return Promise.resolve(response.data)
    })
    .catch((e)=> {
        return Promise.reject(e)
    })
}