import { useState } from 'react';
import { dummyBio } from '../dummy/mypagedummy';
import Modal from '../components/MyPage/Modal';

import {
  BusinessCategory,
  BusinessSpace,
  ButtonGrid,
  ButtonStyle,
  ImgStyle,
  MiniButtonGrid,
  MiniButtonStyle,
  MySpace,
  NicknameAccent,
  TopSpace,
  UserInfo,
  UserInfoTitle,
  BusinessCategoryTitle,
  MyPageContainer,
  MyBioContainer,
} from '../styles/MyPage/MyPage';

function MyPage() {
  const user = dummyBio[0];
  const businessCategory = user.stores[0].businessCategory;
  const businessRegi = `스포츠 및 여가관련 서비스업`;

  const [showBusinessSpace, setShowBusinessSpace] = useState(false);
  const [showModal, setShowModal] = useState(false);
  
  const handleBusinessSpaceToggle = () => {
    setShowBusinessSpace(!showBusinessSpace);
  };

  const openModal = () => {
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
  };

  return (
    <>
      <MyPageContainer>
        <MyBioContainer>
          <MySpace>
            <ButtonGrid>
              <ButtonStyle type="button" onClick={openModal}>편집</ButtonStyle>
            </ButtonGrid>
            <TopSpace>
              <ImgStyle src={user.profileImg} alt="dummy bio img" />
              <MiniButtonGrid>
                <MiniButtonStyle type="button">사진 변경</MiniButtonStyle>
                <MiniButtonStyle type="button">사진 삭제</MiniButtonStyle>
              </MiniButtonGrid>
              <NicknameAccent>{user.nickname}</NicknameAccent>
            </TopSpace>
            <MySpace>
              <UserInfo>
                <UserInfoTitle>닉네임</UserInfoTitle>
                <span>{user.nickname}</span>
              </UserInfo>
              <UserInfo>
                <UserInfoTitle>이메일</UserInfoTitle>
                <span>{user.email}</span>
              </UserInfo>
              <UserInfo>
                <UserInfoTitle>연락처</UserInfoTitle>
                <span>{user.phoneNumber}</span>
              </UserInfo>
            </MySpace>
          </MySpace>
          <MySpace>
            <ButtonGrid>
              <ButtonStyle type="button" onClick={handleBusinessSpaceToggle}>등록한 업체보기</ButtonStyle>
            </ButtonGrid>
            {showBusinessSpace && (
              <BusinessSpace>
                <BusinessCategory>
                  <BusinessCategoryTitle>업태</BusinessCategoryTitle>
                  {user.stores.map((store, index) => (
                    <span key={index}>{businessRegi}</span>
                  ))}
                </BusinessCategory>
                <BusinessCategory>
                  <BusinessCategoryTitle>업종</BusinessCategoryTitle>
                  {user.stores.map((store, index) => (
                    <span key={index}>{businessCategory}</span>
                  ))}
                </BusinessCategory>
                <BusinessCategory>
                  <BusinessCategoryTitle>업체명</BusinessCategoryTitle>
                  {user.stores.map((store, index) => (
                    <span key={index}>{store.storeName}</span>
                  ))}
                </BusinessCategory>
              </BusinessSpace>
            )}
          </MySpace>
        </MyBioContainer>
      </MyPageContainer>
      {showModal && <Modal onClick={closeModal} defaultNickname={user.nickname} defaultPhoneNumber={user.phoneNumber} />}
    </>
  );
}

export default MyPage;
