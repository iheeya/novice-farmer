import axios from 'axios'
import api from '../../utils/axios'

export function ArticleGet(Id: number){
    return api
        .get(`/community/${Id}/all/modify`)
        .then((response) => {
            return Promise.resolve(response.data)
        })
        .catch((e) => {
            return Promise.reject(e)
        })
}
