import React, { useState } from 'react';
import dummyImg from '../dummy/mypagedummy.jpeg';
// import dummyBio from '../dummy/mypage/';
import SideBar from '../components/MyPage/SideBar';
import My from '../components/MyPage/My';
import Wish from '../components/MyPage/Wish';
import Order from '../components/MyPage/Order';
import Stores from '../components/MyPage/Stores';
import { useLocation, useNavigate } from 'react-router-dom';

function MyPage() {
    const [selectedTab, setSelectedTab] = useState('info');
    const location = useLocation();
    const navigate = useNavigate();

    const onTabSelect = (tab) => {
        setSelectedTab(tab);
        navigate(`/my/${tab}`);
    };

    React.useEffect(() => {
        const currentPath = location.pathname;
        switch (currentPath) {
            case '/my/wish':
                setSelectedTab('wish');
                break;
            case '/my/order':
                setSelectedTab('order');
                break;
            case '/my/stores':
                setSelectedTab('stores');
                break;
        }
    }, [location.pathname]);

    const renderContent = () => {
        switch (selectedTab) {
            case 'info':
                return <My />;
            case 'wish':
                return <Wish />;
            case 'order':
                return <Order />;
            case 'stores':
                return <Stores />;
            default:
                return null;
        }
    };
  return (
    <div>
        <div className='flex flex-row justify-center items-start py-24 space-x-8'>
            <SideBar onTabSelect={onTabSelect} />
            <div className='border-[1px] border-[#4771B7] space-y-32 p-20'>
                 {renderContent()}
            </div>
        </div>
    </div>
  );
}

export default MyPage;
