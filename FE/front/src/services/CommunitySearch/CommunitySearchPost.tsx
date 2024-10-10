import axios from 'axios'
import api from '../../utils/axios'

interface Tag{
    id: number
}

interface Tags {
    plustags: Tag[];
    deltags: Tag[];
}

export function ChangeTags(payload: Tags){
    return api
        .post('community/tags/all', payload)
        .then((response) => {
            return Promise.resolve()
        })
        .catch((e) => {
            return Promise.reject(e)
        })
}