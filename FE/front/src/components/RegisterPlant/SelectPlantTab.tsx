import { useState } from "react";
import { useNavigate } from "react-router-dom"; // useNavigate import
import '../../styles/RegisterPlant/selectPlantTab.css'

function SelectTab() {
    type MenuItem = {
        name: string;
        address: string;
        status: boolean;
    };

    const menus: MenuItem[] = [
        {name: '텃밭부터 선택', address: '/register/garden', status: false}, 
        {name: '작물부터 선택', address: '/register/plant', status: true}
    ];

    const [toggle, setToggle] = useState<MenuItem[]>(menus);
    const navigate = useNavigate();

    // 메뉴 클릭 시 해당 주소로 이동
    const handleMenuClick = (index: number) => {
        const updatedMenus = menus.map((menu, idx) => (
            {
                ...menu, // 기존 menu 객체의 값을 그대로 복사
                status: idx===index  // 클릭된 메뉴만 true
            }  
        ));

        setToggle(updatedMenus)
        navigate(updatedMenus[index].address)
    };

    return (
        <div className='menu'>
            {menus.map((menu, index) => (
                <div
                    key={menu.name}
                    onClick={() => handleMenuClick(index)} 
                    className={menu.status ? 'plant-active' : 'plant-inactive' }
                >
                    {menu.name}
                </div>
            ))}
        </div>
    );
}

export default SelectTab;

