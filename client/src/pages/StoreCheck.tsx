import { storecheckdummy } from '../dummy/storecheckdummy';
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
  const { stores } = storecheckdummy;

  const handleDeleteClick = () => {
    alert('정말 삭제하시겠습니가?');
  };

  return (
    <StoreCheckContainer>
      <StoreCheckTitle>판매 서비스 관리</StoreCheckTitle>
      {stores.map((store) => (
        <StoreCards key={store.storeId}>
          <ImgContainer>
            <ImgInnerContainer>
              <ImgStyle src={store.storeImage} alt="store image" />
            </ImgInnerContainer>
          </ImgContainer>
          <StoreInfoContainer>
            <div className="pt-5">
              <StoreName>{store.storeName}</StoreName>
            </div>
            <ButtonsContainer>
              <StoreButtonStyle type="button">업체 수정</StoreButtonStyle>
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
