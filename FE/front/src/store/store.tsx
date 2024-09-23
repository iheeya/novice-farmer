import { configureStore, createSlice } from "@reduxjs/toolkit";


interface FarmState {
  farm : string |null;
  plant: string | null;
  location: string | null
}


let farmSelect = createSlice({
  name: 'farmSelect',
  initialState: {
    farm: null,
    plant: null,
    location: null
  },
  reducers: {
    setFarmData(state, action) {
      state.farm = action.payload; // farm 데이터 설정
    },
    setPlantData(state, action) {
      state.plant = action.payload; // plant 데이터 설정
    },
    setLocationData(state, action) {
      state.location = action.payload; // location 데이터 설정
    },
    clearFarmData() {
      return {
        farm: null,
        plant: null,
        location: null
      }; // farm 데이터 초기화
    },
  },
});

export const { setFarmData, setPlantData, setLocationData, clearFarmData } = farmSelect.actions;


export interface RootState{
  farmSelect: FarmState;
}


export default configureStore({
  reducer: {
    farmSelect: farmSelect.reducer,
  }
});
