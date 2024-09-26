import axios from 'axios'
import api from '../../utils/axios'

interface Address {
    [key: string]: any; // 어떤 속성도 가질 수 있도록 설정
}

interface GardenPayload {
    placeId: Number | null; 
    address: Address; // Address 인터페이스 사용
}

export function selectGardenPost(payload:GardenPayload){
    return api
        .post('farm/plant/recommend', payload)
        .then((response) => {
            return Promise.resolve(response.data)
        })
        .catch((e)=> {
            return Promise.reject(e)
        })
}