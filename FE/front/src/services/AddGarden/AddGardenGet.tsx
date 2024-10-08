import axios from 'axios'
import api from '../../utils/axios'

export function getFarmSelect() {
    return api 
        .get('/farm/place')
        .then((response) => {
            return Promise.resolve(response.data)
        })
        .catch((e)=> {
            return Promise.reject(e)
        })
}


export function getPlantData(){
    return api
        .get('farm/plant')
        .then((response) => {
            return Promise.resolve(response.data)
        })
        .catch((e) => {
            return Promise.reject(e)
        })
}


