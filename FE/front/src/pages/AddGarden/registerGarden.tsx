import {useState, useEffect} from 'react' 
import SelectTab from "../../components/RegisterGarden/selectTab";
import GardenSelect from "../../components/RegisterGarden/gardenSelect";
import Loading from "../../components/RegisterGarden/gardenSelectLoading";
import RecommendPlant from "../../components/RegisterGarden/recommendPlant";
import RegisterHeader from '../../components/RegisterGarden/RegisterHeader';

function RegisterGarden(){
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
      // console.log('메인 페이지 resonse 확인', response)
      setTimeout(() => {
        setIsLoading(false);  // 로딩 완료
        if (response && response.length > 0) {
          setIsRecommend(true);  
        }
      }, 2000);  // 2초 지연 후 처리
    }
  }, [isLoading, response]);  // isLoading 상태가 변할 때만 실행

    

    return(
        <>
          <RegisterHeader/>
          <SelectTab />

          {isLoading? (
            <Loading/>) : isRecommend ? (
              <RecommendPlant response={response}/>
            ) :
            (
              <GardenSelect onLoading={handleModalClick} onResponse={handleResponse}/>
            )
          }


     
        </>
        
    )
}


export default RegisterGarden;