import AddPlantHeader from "../../components/AddPlant/AddPlantHeader";
import AddPlantBody from "../../components/AddPlant/AddPlantBody";
import { useLocation } from 'react-router-dom';
import { useEffect } from "react";

function AddPlant(){
    const location = useLocation();
    const { plantData } = location.state || {}; // 데이터가 없을 수도 있으니 안전하게 처리
  
    useEffect(() => {
      if (plantData) {
        console.log('전달된 데이터:', plantData);
      }
    }, [plantData]);
    return(
        <>
            <AddPlantHeader/>
            <AddPlantBody plantData={plantData} />
        </>
    )
}

export default AddPlant;