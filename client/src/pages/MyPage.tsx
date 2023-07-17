import { useState } from 'react';
import { dummyBio } from '../dummy/mypagedummy';
import Modal from '../components/MyPage/Modal';
import profile from '../assets/profile.svg';

import {
  BusinessCategory,
  BusinessSpace,
  ButtonGrid,
  ButtonStyle,
  ImgStyle,
  MiniButtonGrid,
  MySpace,
  NicknameAccent,
  TopSpace,
  UserInfo,
  UserInfoTitle,
  BusinessCategoryTitle,
  MyPageContainer,
  MyBioContainer,
  PhotoInputStyle,
} from '../styles/MyPage/MyPage';

function MyPage() {
  // const APIURL = import.meta.env.VITE_APP_API_URL
  const user = dummyBio[0];
  const businessCategory = user.stores[0].businessCategory;
  const businessRegi = `스포츠 및 여가관련 서비스업`;

  const [showBusinessSpace, setShowBusinessSpace] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [selectedPhoto, setSelectedPhoto] = useState(null);
  // const [userData, setUserData] = useState(null);

  // useEffect(() => {
  //   fetchData();
  // }, []);

  // const fetchData = async () => {
  //   try {
  //     const ACCESS_TOKEN = sessionStorage.getItem('Authorization');
  //     const res = await fetch(`${APIURL}/mypage`, {
  //       method: 'GET',
  //       headers: {
  //         'Authorization': ACCESS_TOKEN
  //       }
  //     })
  //       .then(res => res.json())
  //       .then(data => {
  //         //응답 데이터처리
  //       })
  //   } catch (error) {
  //     console.error('Error fetching data', error);
  //   }
  // };

  // if(!userData) {
  //   return <p>Loading...</p>;
  // }
  
  const handleBusinessSpaceToggle = () => {
    setShowBusinessSpace(!showBusinessSpace);
  };

  const openModal = () => {
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
  };

  const handlePhotoChange = (e) => {
    const file = e.target.files[0];
    setSelectedPhoto(file);
  };

  const handlePhotoRemove = () => {
    setSelectedPhoto(null);
    const input = document.getElementById('photoInput') as HTMLInputElement;
    if (input) {
      input.value='';
    }
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
              <ImgStyle 
                src={selectedPhoto ? URL.createObjectURL(selectedPhoto) : profile} 
                alt="profile img" 
              />
              <MiniButtonGrid>
                <label htmlFor='photoInput'>
                  <input 
                    id='photoInput'
                    type='file'
                    accept='image/*'
                    style={{ display: 'none' }}
                    onChange={handlePhotoChange} 
                  />
                  <PhotoInputStyle>사진 변경</PhotoInputStyle>
                </label>
                <label htmlFor='photoRemoveInput'>
                  <input 
                    id='photoRemoveInput'
                    type='button'
                    style={{ display: 'none' }}
                    onClick={handlePhotoRemove}
                  />
                  <PhotoInputStyle>사진 삭제</PhotoInputStyle>
                </label>
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
                  {user.stores.map((_, index) => (
                    <span key={index}>{businessRegi}</span>
                  ))}
                </BusinessCategory>
                <BusinessCategory>
                  <BusinessCategoryTitle>업종</BusinessCategoryTitle>
                  {user.stores.map((_, index) => (
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
