import { useState, useEffect } from "react";
import SelectTab from "../../components/RegisterPlant/SelectPlantTab";
import PlantSelect from "../../components/RegisterPlant/PlantSelect";
import PlanSelectLoading from "../../components/RegisterPlant/PlantSelectLoading";
import RecommendLand from "../../components/RegisterPlant/RecommendLand";
import RegisterHeader from '../../components/RegisterGarden/RegisterHeader';

function RegisterPlant(){
  const [isLoading, setIsLoading] = useState(false)
  const [isRecommend, setIsRecommend] = useState(false)
  const [response, setResponse] = useState<any[]>([]); // 응답 데이터를 저장할 상태

  const handleModalClick = () => {
    setIsLoading(true);
  }

  const handleResponse = (data: any[]) => {
    setResponse(data); // 응답 데이터를 상태에 저장
};
  
  // isLoading 상태가 변경될 때마다 실행
  useEffect(() => {
    if (isLoading) {
      // 2초 후 로딩을 완료하고, 추천 데이터를 확인 후 상태 설정
      setTimeout(() => {
        setIsLoading(false);  // 로딩 완료
        if (response && response.length > 0) {
          setIsRecommend(true);  
        }
      }, 5000);  // 2초 지연 후 처리
    }
  }, [isLoading, response]);  // isLoading 상태가 변할 때만 실행

  
    return(
        <>
         <RegisterHeader/>
         <SelectTab />

        {isLoading? (
          <PlanSelectLoading/>
        ) : isRecommend ?(
          <RecommendLand response={response}/>
        )
        : <PlantSelect onLoading={handleModalClick} onResponse={handleResponse}/>}
        </>
    )
}

export default RegisterPlant;