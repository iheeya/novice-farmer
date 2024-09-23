import { useState, useEffect } from "react";
import SelectTab from "../components/RegisterPlant/SelectPlantTab";
import PlantSelect from "../components/RegisterPlant/PlantSelect";
import farmRecommend from "../assets/dummydata/farmRecommend.json"
import PlanSelectLoading from "../components/RegisterPlant/PlantSelectLoading";

function RegisterPlant(){
  const [isLoading, setIsLoading] = useState(false)
  const [isRecommend, setIsRecommend] = useState(false)

  const handleModalClick = () => {
    setIsLoading(true);
  }

  
  // isLoading 상태가 변경될 때마다 실행
  useEffect(() => {
    if (isLoading) {
      // 2초 후 로딩을 완료하고, 추천 데이터를 확인 후 상태 설정
      setTimeout(() => {
        setIsLoading(false);  // 로딩 완료
        if (farmRecommend && farmRecommend.length > 0) {
          setIsRecommend(true);  
        }
      }, 2000);  // 2초 지연 후 처리
    }
  }, [isLoading]);  // isLoading 상태가 변할 때만 실행

  
    return(
        <>
         <SelectTab />

        {isLoading? (
          <PlanSelectLoading/>
        ): <PlantSelect onLoading={handleModalClick}/>}
        </>
    )
}

export default RegisterPlant;