import { configureStore, createSlice } from "@reduxjs/toolkit";


interface FarmState {
  farm : string |null;
  plant: string | null;
  location: string | null
  placeId: number | null
}


interface AddressState {
  address: {
    sido: string | null,
    sigungu: string | null,
    bname1: string | null,
    bname2: string | null,
    jibun: string | null,
    zonecode: string | null,
  } 
}


let farmSelect = createSlice({
  name: 'farmSelect',
  initialState: {
    farm: null,
    plant: null,
    location: null,
    placeId: null,
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
    setPlaceIdData(state, action){
      state.placeId = action.payload;
    },
    clearFarmData() {
      return {
        farm: null,
        plant: null,
        location: null,
        placeId: null,
      }; // farm 데이터 초기화
    },
  },
});


let address = createSlice({
  name: 'address', 
  initialState: {
    address: null, 
  },
  reducers : {
    setAddressData(state,action){
      state.address = action.payload;
    }, 
    clearAddressData(state){
      state.address= null;
    }
  }

})



export const { setFarmData, setPlaceIdData, setPlantData, setLocationData, clearFarmData } = farmSelect.actions;
export const {setAddressData, clearAddressData} = address.actions;

export interface RootState{
  farmSelect: FarmState;
  address: AddressState
}


export default configureStore({
  reducer: {
    farmSelect: farmSelect.reducer,
    address: address.reducer,
  }
});
