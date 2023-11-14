import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { MdOutlineAddHome } from 'react-icons/md';
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
  NoStores,
} from '../styles/MyPage/StoreCheck';
import NothingComponent from '../components/MyPage/NothingComponent';
// import Loading from '../components/Loading/Loading';

function StoreCheck() {
  const APIURL = import.meta.env.VITE_APP_API_URL;
  const [storesData, setStoresData] = useState([]);
  const [loading, setLoading] = useState(true);

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
      } 
    } catch (error) {
      console.error('에러가 발생했습니다.', error);
    } finally {
      setLoading(false);
    }
  };

  // const deleteStore = async (storeId) => {
  //   try {
  //     const ACCESS_TOKEN = sessionStorage.getItem('Authorization');
  //     const res = await fetch(`${APIURL}/stores/${storeId}`, {
  //       method: 'DELETE',
  //       headers: {
  //         'Authorization': ACCESS_TOKEN
  //       }
  //     });

  //     if(res.ok) {
  //       console.log('업체 삭제 완료');
  //       setStoresData((prevStoresData) => 
  //         prevStoresData.filter((store) => store.storeId !== storeId)
  //       );
  //     } else {
  //       console.error('업체 삭제 실패', res.status);
  //     }
  //   } catch (error) {
  //     console.error('에러가 발생했습니다.', error);
  //   }
  // };

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
    alert('업체 삭제는 파트너 센터에 문의주시기 바랍니다.');
    // if (window.confirm('정말 삭제하시겠습니까?')) {
    //   deleteStore(storeId);
    // }
  };

  return (
    <StoreCheckContainer>
      <div className='flex flex-row justify-between'>
        <StoreCheckTitle>판매 서비스 관리</StoreCheckTitle>
        <button className='mr-6 text-[18px] font-semibold bg-[#4771B7] text-white p-[7px] rounded-lg' type='button' onClick={handleAddClick}>업체 등록</button>
      </div>
      {loading ? (
        <div>로딩중입니다....</div>
      ) : storesData.length === 0 ? (
        <NoStores>
          <MdOutlineAddHome style={{ fontSize: '100px', color: '#4771B7'}} />
          <NothingComponent 
            title='아직 등록된 업체가 없네요!'
            description='업체를 차곡차곡 쌓아볼까요?'
          />
        </NoStores>
      ) : (
        storesData.map((store) => (
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
                <StoreButtonStyle type="button" onClick={() => handleDeleteClick()}>
                  업체 삭제
                </StoreButtonStyle>
              </ButtonsContainer>
            </StoreInfoContainer>
          </StoreCards>
        ))
      )}
    </StoreCheckContainer>
  );
}

export default StoreCheck;
