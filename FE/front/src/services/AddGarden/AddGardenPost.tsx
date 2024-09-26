import axios from 'axios'
import api from '../../utils/axios'

interface Address {
    [key: string]: any; // 어떤 속성도 가질 수 있도록 설정
}

interface GardenPayload {
    placeId: Number | null; 
    address: Address; // Address 인터페이스 사용
}

interface Final{
    place: {
        placeId: number | null;
        address: Address;
    };
    plant: {
        plantId: number | null; // 또는 필요에 따라 다른 타입
        myPlantName: string;
        memo?: string; // 선택적으로 memo가 있을 수 있음
    };
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


export function gardenFinishPost(payload:Final){
    return api
        .post('farm', payload)
        .then((response) => {
            return Promise.resolve(response.data)
        })
        .catch((e) => {
            return Promise.reject(e)
        })
}