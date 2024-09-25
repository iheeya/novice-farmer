import axios from 'axios'
import api from '../../utils/axios'


export async function getFarmSelect(){
    try{
        const response = await api.get('/farm/place')
        return response.data
    } catch (e) {
        console.error(e)
    }
}


