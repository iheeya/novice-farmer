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


export function getSearchResult(page=0, size= 10, filter='new', search ='') {
    return api
    .get('comminity', {
        params: {
            page, size, filter, search
        }
    })
    .then((response) => {
        return Promise.resolve(response.data)
    })
    .catch((e) => {
        return Promise.reject(e)
    })
}