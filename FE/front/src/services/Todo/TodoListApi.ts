import api from '../../utils/axios';

export interface Todo {
  todoDate: string;
  todoType: 'WATERING' | 'FERTILIZERING' | 'HARVESTING';
  remainDay: number;
}

export interface Plant {
  plantId: number;
  plantName: string;
  myPlantId: number;
  myPlantName: string;
  growthStep: number;
  plantDegreeRatio: number;
  todos: Todo[];
}

export interface Place {
  placeId: number;
  placeName: string;
  myPlaceId: number;
  myPlaceName: string;
  weather: string;
  plants: Plant[];
}

export const getTodoList = async (): Promise<Place[]> => {
  try {
    const response = await api.get('/todo'); 
    return response.data;
  } catch (error) {
    console.error('Error fetching todo list', error);
    throw error;
  }
};
