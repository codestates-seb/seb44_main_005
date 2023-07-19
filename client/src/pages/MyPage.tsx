import { useEffect, useState } from 'react';
import Modal from '../components/MyPage/Modal';
import BusinessSpaceSection from '../components/MyPage/BusinessSpaceSection';
import defaultProfileImg from '../assets/profile.svg';

import {
  ButtonGrid,
  ButtonStyle,
  ImgStyle,
  MiniButtonGrid,
  MySpace,
  NicknameAccent,
  TopSpace,
  MyPageContainer,
  MyBioContainer,
  PhotoInputStyle,
} from '../styles/MyPage/MyPage';
import UserInfoSection from '../components/MyPage/UserInfoSection';

function MyPage() {
  const APIURL = import.meta.env.VITE_APP_API_URL;
  const businessRegi = `스포츠 및 여가관련 서비스업`;

  const [showBusinessSpace, setShowBusinessSpace] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [userData, setUserData] = useState(null);
  const [partnerData, setPartnerData] = useState(null);
  const [_, setAccessDenied] = useState(false);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const ACCESS_TOKEN = sessionStorage.getItem('Authorization');
      const res = await fetch(`${APIURL}/mypage`, {
        method: 'GET',
        headers: {
          'Authorization': ACCESS_TOKEN
        }
      });
      
      if (res.ok) {
        const data = await res.json();
        setUserData(data); // userData 업데이트
      } else {
        console.error('Error fetching data', res.status);
      }
    } catch (error) {
      console.error('Error fetching data', error);
    }
  };

  useEffect(() => {
    fetchPartnerData();
  },[]);

  const fetchPartnerData = async () => {
    try {
      const PARTNER_ACCESS_TOKEN = sessionStorage.getItem('Authorization');
      const res = await fetch(`${APIURL}/mypage/partner`, {
        method: 'GET',
        headers: {
          'Authorization': PARTNER_ACCESS_TOKEN
        }
      });

      if (res.ok) {
        const data = await res.json();
        setPartnerData(data);
        if (data.stores.length === 0) {
          setAccessDenied(true);
        } 
      } else {
        console.error('Error fetching data', res.status);
      }
    } catch (error) {
      console.error('Error fetching data', error);
    }
  };

  if(!userData) {
    return (
      <div className='border-[1px] border-[#4771B7] w-[700px] flex justify-center items-center text-xl font-semibold'>
        <p>로그인 상태를 확인하여주시기 바랍니다.</p>
      </div>
    )
  }

  const { nickname, email, phoneNumber, profileImg } = userData;
  
  const handleBusinessSpaceToggle = () => {
    setShowBusinessSpace(!showBusinessSpace);
    if(!showBusinessSpace) {
      fetchPartnerData();
    }
  };

  const handleButtonClick = () => {
    if(partnerData.stores.length === 0) {
      alert('접근 권한이 없습니다.');
    } else {
      handleBusinessSpaceToggle();
    }
  }

  const openModal = () => {
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
  };

  const handleEditComplete = (updatedUserData) => {
    setUserData({
      ...updatedUserData,
      email: userData.email
    });
    setShowModal(false);
  };

  const profileImage = profileImg !== 'default image' ? profileImg : defaultProfileImg;

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
                src={profileImage}
                alt="profile img" 
              />
              <MiniButtonGrid>
                <label htmlFor='photoInput'>
                  <input 
                    id='photoInput'
                    type='file'
                    accept='image/*'
                    style={{ display: 'none' }}
                  />
                  <PhotoInputStyle>사진 변경</PhotoInputStyle>
                </label>
                <label htmlFor='photoRemoveInput'>
                  <input 
                    id='photoRemoveInput'
                    type='button'
                    style={{ display: 'none' }}
                  />
                  <PhotoInputStyle>사진 삭제</PhotoInputStyle>
                </label>
              </MiniButtonGrid>
              <NicknameAccent>{nickname}</NicknameAccent>
            </TopSpace>
            <UserInfoSection nickname={nickname} email={email} phoneNumber={phoneNumber} />
          </MySpace>
          <MySpace>
            <ButtonGrid>
              <ButtonStyle type="button" onClick={handleButtonClick}>등록한 업체보기</ButtonStyle>
            </ButtonGrid>
            {showBusinessSpace && (
              <BusinessSpaceSection partnerData={partnerData} businessRegi={businessRegi} />
            )}
          </MySpace>
        </MyBioContainer>
      </MyPageContainer>
      {showModal && <Modal 
        onClick={closeModal}
        defaultNickname={nickname} 
        defaultPhoneNumber={phoneNumber} 
        onEditComplete={handleEditComplete}
      />}
    </>
  );
}

export default MyPage;
