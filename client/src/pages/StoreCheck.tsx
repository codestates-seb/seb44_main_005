import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  StoreButtonStyle,
  ButtonsContainer,
  ImgContainer,
  ImgInnerContainer,
  ImgStyle,
  StoreCards,
  StoreCheckContainer,
  StoreCheckTitle,
  StoreInfoContainer,
  StoreName,
} from '../styles/MyPage/StoreCheck';

function StoreCheck() {
  const APIURL = import.meta.env.VITE_APP_API_URL;
  const [storesData, setStoresData] = useState([]);

  useEffect(() => {
    fetchStores();
  },[]);

  const fetchStores = async () => {
    try {
      const ACCESS_TOKEN = sessionStorage.getItem('Authorization');
      const res = await fetch(`${APIURL}/mystores`, {
        method: 'GET',
        headers: {
          'Authorization': ACCESS_TOKEN
        }
      });

      if(res.ok) {
        const data = await res.json();
        setStoresData(data.stores);
      } else {
        console.error('데이터가 없습니다.', res.status);
      }
    } catch (error) {
      console.error('에러가 발생했습니다.', error);
    }
  };

  const navigate = useNavigate();

  const handleEditClick = (storeId) => {
    navigate(`/store/edit?store_id=${storeId}`);
  };

  const handleAddClick = () => {
    navigate(`/store/add`);
  };

  const handleStoreNameClick = (storeId) => {
    navigate(`/category/${storeId}`);
  };

  const handleDeleteClick = () => {
    alert('정말 삭제하시겠습니가?');
  };

  return (
    <StoreCheckContainer>
      <div className='flex flex-row justify-between'>
        <StoreCheckTitle>판매 서비스 관리</StoreCheckTitle>
        <button className='text-[18px] font-semibold bg-[#4771B7] text-white p-[7px] rounded-lg' type='button' onClick={handleAddClick}>업체 등록</button>
      </div>
      {storesData.map((store) => (
        <StoreCards key={store.storeId}>
          <ImgContainer>
            <ImgInnerContainer>
              <ImgStyle src={store.storeImage} alt="store image" />
            </ImgInnerContainer>
          </ImgContainer>
          <StoreInfoContainer>
            <div className="pt-5">
              <StoreName onClick={() => handleStoreNameClick(store.storeId)}>{store.storeName}</StoreName>
            </div>
            <ButtonsContainer>
              <StoreButtonStyle type="button" onClick={() => handleEditClick(store.storeId)}>업체 수정</StoreButtonStyle>
              <StoreButtonStyle type="button" onClick={handleDeleteClick}>
                업체 삭제
              </StoreButtonStyle>
            </ButtonsContainer>
          </StoreInfoContainer>
        </StoreCards>
      ))}
    </StoreCheckContainer>
  );
}

export default StoreCheck;
