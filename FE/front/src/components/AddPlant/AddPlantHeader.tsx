import '../../styles/RegisterGarden/selectTab.css'

function AddPlantHeader(){
    type MenuItem = {
        name: string;
        status: boolean;
    };

    const menus: MenuItem[] = [
        {name: '텃밭부터 선택', status: true}, 
        {name: '작물부터 선택',  status: false}
    ];


    return (
        <div className='menu'
        >
            {menus.map((menu, index) => (
                <div
                    key={menu.name}
                    // onClick={() => handleMenuClick(index)} 
                    className={menu.status ? 'active' : 'inactive' }
                >
                    {menu.name}
                </div>
            ))}
        </div>
    );
}

export default AddPlantHeader;



