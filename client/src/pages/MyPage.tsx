import { useEffect, useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import Modal from '../components/MyPage/Modal';
import BusinessSpaceSection from '../components/MyPage/BusinessSpaceSection';
import UserInfoSection from '../components/MyPage/UserInfoSection';
import ConfirmationModal from '../components/MyPage/ConfirmationModal';
import defaultProfileImg from '../assets/profile.svg';
import 'react-toastify/dist/ReactToastify.css';

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

function MyPage() {
  const APIURL = import.meta.env.VITE_APP_API_URL;
  const businessRegi = `스포츠 및 여가관련 서비스업`;

  const [showBusinessSpace, setShowBusinessSpace] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [selectedPhoto, setSelectedPhoto] = useState(null);
  const [userData, setUserData] = useState(null);
  const [partnerData, setPartnerData] = useState(null);
  const [accessDenied, setAccessDenied] = useState(false);
  const [isDeletingPhoto, setIsDeletingPhoto] = useState(false);

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

  const handlePhotoChange = async (e) => {
    const file = e.target.files[0];
    setSelectedPhoto(file);

    try  {
      const ACCESS_TOKEN = sessionStorage.getItem('Authorization');
      const formData = new FormData();
      formData.append('image', file);

      const res = await fetch(`${APIURL}/mypage/profile`, {
        method: 'PUT',
        headers: {
          'Authorization': ACCESS_TOKEN,
        },
        body: formData,
      });

      if (res.ok) {
        console.log('프로필 업데이트 완료');
        const imageUrl = URL.createObjectURL(file);
        setSelectedPhoto(file);
        sessionStorage.setItem('selectedPhoto', JSON.stringify(imageUrl));
        fetchData(); // Fetch updated user data after profile update
      } else {
        console.error('프로필 업데이트 실패', res.status);
      }
    } catch (error) {
      console.error('프로필 업데이트 에러', error);
    }
  };

  const getProfileImage = () => {
    if (profileImg === 'default image') {
      return defaultProfileImg;
    } else if (profileImg) {
      return profileImg;
    } else {
      return defaultProfileImg;
    }
  };

  const handlePhotoRemove = async () => {
    const confirmMessage = '사진을 삭제하시겠습니까?';
    const toastId = toast(<ConfirmationModal message={confirmMessage} onConfirm={onConfirmDelete} onCancel={onCancelDelete} />, {
      closeOnClick: false,
    });
  
    async function onConfirmDelete() {
      toast.dismiss(toastId);
  
      setIsDeletingPhoto(true);
  
      try {
        const ACCESS_TOKEN = sessionStorage.getItem('Authorization');
        const res = await fetch(`${APIURL}/mypage/profile`, {
          method: 'DELETE',
          headers: {
            'Authorization': ACCESS_TOKEN,
          },
        });
  
        if (res.ok) {
          console.log('프로필 사진 삭제 완료');
          setSelectedPhoto(null);
          const input = document.getElementById('photoInput') as HTMLInputElement;
          if (input) {
            input.value = '';
          }
          setUserData((prevUserData) => ({
            ...prevUserData,
            profileImg: 'default image',
          }));
  
          toast('프로필 사진이 삭제되었습니다.');
        } else {
          console.error('프로필 사진 삭제 실패', res.status);
  
          toast.error('프로필 사진 삭제에 실패했습니다.');
        }
      } catch (error) {
        console.error('프로필 사진 삭제 에러', error);
      } finally {
        setIsDeletingPhoto(false);
      }
    }
  
    function onCancelDelete() {
      toast.dismiss(toastId);
    }
  };

  const handleEditComplete = (updatedUserData) => {
    setUserData({
      ...updatedUserData,
      email: userData.email
    });
    setShowModal(false);
  };

  return (
    <>
      <MyPageContainer>
          <ToastContainer
            toastClassName={
              'h-[20px] rounded-md text-sm font-medium bg-[#EDF1F8] text-[#4771B7] text-center mt-[70px]'
            }
            position="top-right"
            limit={1}
            closeButton={false}
            autoClose={2000}
            hideProgressBar
         />
        <MyBioContainer>
          <MySpace>
            <ButtonGrid>
              <ButtonStyle type="button" onClick={openModal}>편집</ButtonStyle>
            </ButtonGrid>
            <TopSpace>
              <ImgStyle 
                src={selectedPhoto ? URL.createObjectURL(selectedPhoto) : getProfileImage()}
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