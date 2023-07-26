import { useRecoilState, useRecoilValue } from 'recoil';
import { isProfile, Role } from '../store/userInfoAtom';
import { useEffect, useState } from 'react';
import Modal from '../components/MyPage/Modal';
import BusinessSpaceSection from '../components/MyPage/BusinessSpaceSection';
import UserInfoSection from '../components/MyPage/UserInfoSection';
import ProfileImageSection from '../components/MyPage/ProfileImageSection';
import LoadingComponent from '../components/Loading/LoadingComponent';
import defaultProfileImg from '../assets/profile.svg';
import 'react-toastify/dist/ReactToastify.css';
import {
  ButtonGrid,
  ButtonStyle,
  MySpace,
  NicknameAccent,
  TopSpace,
  MyPageContainer,
  MyBioContainer,
  ButtonGridEdit,
  LoadingContainer,
} from '../styles/MyPage/MyPage';
function MyPage() {
  const APIURL = import.meta.env.VITE_APP_API_URL;
  const businessRegi = `스포츠 및 여가관련 서비스업`;
  const role = useRecoilValue(Role);
  const [showBusinessSpace, setShowBusinessSpace] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [userData, setUserData] = useState(null);
  const [partnerData, setPartnerData] = useState(null);
  const [profileImageUrl, setProfileImageUrl] = useRecoilState(isProfile);
  useEffect(() => {
    fetchData();
  }, []);
  const fetchData = async () => {
    try {
      const ACCESS_TOKEN = sessionStorage.getItem('Authorization');
      const res = await fetch(`${APIURL}/mypage`, {
        method: 'GET',
        headers: {
          Authorization: ACCESS_TOKEN,
        },
      });
      if (res.ok) {
        const data = await res.json();
        setUserData(data); // userData 업데이트
      } 
    } catch (error) {
      console.error('Error fetching data', error);
    }
  };
  const fetchPartnerData = async () => {
    try {
      const PARTNER_ACCESS_TOKEN = sessionStorage.getItem('Authorization');
      const res = await fetch(`${APIURL}/mypage/partner`, {
        method: 'GET',
        headers: {
          Authorization: PARTNER_ACCESS_TOKEN,
        },
      });
      if (res.ok) {
        const data = await res.json();
        setPartnerData(data);
      } 
    } catch (error) {
      console.error('Error fetching data', error);
    }
  };
  
  if (!userData) {
    return (
      <LoadingContainer>
        <LoadingComponent />
      </LoadingContainer>
    );
  }
  const { nickname, email, phoneNumber } = userData;
  const handleBusinessSpaceToggle = () => {
    setShowBusinessSpace(!showBusinessSpace);
    if (!showBusinessSpace) {
      fetchPartnerData();
    }
  };
  const handleButtonClick = () => {
    if (role !== 'PARTNER') {
      alert('접근 권한이 없습니다.');
    } else {
      handleBusinessSpaceToggle();
    }
  };
  const openModal = () => {
    setShowModal(true);
  };
  const closeModal = () => {
    setShowModal(false);
  };
  const handlePhotoChange = async (e) => {
    const file = e.target.files[0];
    try {
      const ACCESS_TOKEN = sessionStorage.getItem('Authorization');
      const formData = new FormData();
      formData.append('image', file);

      const res = await fetch(`${APIURL}/mypage/profile`, {
        method: 'PUT',
        headers: {
          Authorization: ACCESS_TOKEN,
        },
        body: formData,
      });

      if (res.ok) {
        const reader = new FileReader();
        reader.onloadend = () => {
          const imageUrl = reader.result;
          sessionStorage.setItem('selectedPhoto', JSON.stringify(imageUrl));
          setProfileImageUrl(imageUrl);
        }
        reader.readAsDataURL(file)
      // if (res.ok) {
      //   const data = await res.json();
      //   if(data && data.imageUrl) {
      //     sessionStorage.setItem('selectedPhoto', JSON.stringify(data.imageUrl));
      //     setProfileImageUrl(data.imageUrl);
      //   }
      } else {
        console.error('프로필 업데이트 실패', res.status);
      }
    } catch (error) {
      console.error('프로필 업데이트 에러', error);
    }
  };
  const handlePhotoRemove = async () => {
    alert('사진을 삭제하시겠습니까?');
    try {
      const ACCESS_TOKEN = sessionStorage.getItem('Authorization');
      const res = await fetch(`${APIURL}/mypage/profile`, {
        method: 'DELETE',
        headers: {
          Authorization: ACCESS_TOKEN,
        },
      });
       if (res.ok) {
        const imageUrl = defaultProfileImg;
        sessionStorage.setItem('selectedPhoto', JSON.stringify(imageUrl));
        setProfileImageUrl(imageUrl);
        fetchData();
        alert('프로필 사진이 삭제되었습니다.')
      } else {
        console.error('프로필 사진 삭제 실패', res.status);
        alert('프로필 사진 삭제에 실패했습니다.');
      }
    } catch (error) {
      console.error('프로필 사진 삭제 에러', error);
    } 
  };
  const handleEditComplete = (updatedUserData) => {
    setUserData({
      ...updatedUserData,
      email: userData.email,
    });
    setShowModal(false);
  };
  return (
    <>
      <MyPageContainer>
        <MyBioContainer>
          <MySpace>
            <ButtonGridEdit>
              <ButtonStyle type="button" onClick={openModal}>
                편집
              </ButtonStyle>
            </ButtonGridEdit>
            <TopSpace>
              <ProfileImageSection
                profileImageUrl={profileImageUrl}
                handlePhotoChange={handlePhotoChange}
                handlePhotoRemove={handlePhotoRemove}
              />
              <NicknameAccent>{nickname}</NicknameAccent>
            </TopSpace>
            <UserInfoSection
              nickname={nickname}
              email={email}
              phoneNumber={phoneNumber}
            />
          </MySpace>
          <MySpace>
            <ButtonGrid>
              <ButtonStyle type="button" onClick={handleButtonClick}>
                등록한 업체보기
              </ButtonStyle>
            </ButtonGrid>
            {showBusinessSpace && (
              <BusinessSpaceSection
                partnerData={partnerData}
                businessRegi={businessRegi}
              />
            )}
          </MySpace>
        </MyBioContainer>
      </MyPageContainer>
      {showModal && (
        <Modal
          onClick={closeModal}
          defaultNickname={nickname}
          defaultPhoneNumber={phoneNumber}
          onEditComplete={handleEditComplete}
        />
      )}
    </>
  );
}
export default MyPage;