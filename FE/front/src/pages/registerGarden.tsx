import {useState} from 'react' 
import SelectTab from "../components/RegisterGarden/selectTab";
import GardenSelect from "../components/RegisterGarden/gardenSelect";
import Loading from "../components/RegisterGarden/gardenSelectLoading";

function RegisterGarden(){

    const [isLoading, setIsLoading] = useState(false)
    
    const handleModalClick = () => {
      setIsLoading(true);
    }

    return(
        <>
          <SelectTab />
          <GardenSelect/>
        </>
        
    )
}


export default RegisterGarden;