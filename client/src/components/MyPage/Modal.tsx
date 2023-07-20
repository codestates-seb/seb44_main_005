import { useState } from 'react';
import headerlogo from '../../assets/headerlogo.svg';
import close from '../../assets/close.svg';

function Modal({ onClick, defaultNickname, defaultPhoneNumber, onEditComplete }) {
  const APIURL = import.meta.env.VITE_APP_API_URL;
  const [updatedNickname, setUpdatedNickname] = useState(defaultNickname);
  const [updatedPhoneNumber, setUpdatedPhoneNumber] = useState(defaultPhoneNumber);
  const [isNicknameValid, setNicknameValid] = useState(true);

  const handleEditComplete = async () => {
    try {
      const EDIT_ACCESS_TOKEN = sessionStorage.getItem('Authorization');
      const requestBody: { nickname?: string, phoneNumber?: string } = {};

      if (updatedNickname !== defaultNickname) {
        requestBody.nickname = updatedNickname;
      }

      if (updatedPhoneNumber !== defaultPhoneNumber) {
        requestBody.phoneNumber = updatedPhoneNumber;
      }

      const res = await fetch(`${APIURL}/mypage`, {
        method: 'PATCH',
        headers: {
          'Authorization': EDIT_ACCESS_TOKEN,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
      });

      if (res.ok) {
        console.log('편집완료');
        onClick();
        onEditComplete({
          nickname: updatedNickname,
          phoneNumber: updatedPhoneNumber
        });
      } else {
        console.error('편집실패', res.status);
      }
    } catch (error) {
      console.error('편집 실패', error);
    }
  };

  const handleNicknameChange = (e) => {
    const nickname = e.target.value;
    setUpdatedNickname(nickname);
    setNicknameValid(isValidNickname(nickname));
  };

  const isValidNickname = (nickname) => {
    const regex = /^[A-Za-z0-9]+$/;
    return regex.test(nickname);
  }

  const handlePhoneNumberChange = (e) => {
    setUpdatedPhoneNumber(e.target.value);
  };

  const handleOutsideClick = (e) => {
    if (e.target === e.currentTarget) {
      onClick();
    }
  }

  return (
    <div className='fixed top-0 left-0 right-0 bottom-0 flex justify-center items-center bg-black bg-opacity-40 z-50' onClick={handleOutsideClick}>
      <div className='flex flex-col w-[600px] h-[500px] space-y-[20px] rounded-lg bg-white drop-shadow-lg'>
        <div className='flex justify-end p-2'>
          <img className='cursor-pointer' src={close} alt='close button' onClick={onClick} />
        </div>
        <div className='flex justify-center py-12'>
          <img className='w-[182px]' src={headerlogo} alt='logo' />
        </div>
        <div className='flex flex-row justify-center space-x-[50px]'>
          <p>닉네임</p>
          <input
            className='border-[2px] border-[#CCCCCC] rounded p-1'
            type='text'
            defaultValue={updatedNickname}
            onChange={handleNicknameChange}
          />
        </div>
        {!isNicknameValid && (
           <div className='flex flex-row justify-center text-sm'>
             <p className='text-red-500'>닉네임 편집은 영문, 숫자로만 가능합니다.</p>
          </div>
        )}
        <div className='flex flex-row justify-center space-x-[50px]'>
          <p>연락처</p>
          <input
            className='border-[2px] border-[#CCCCCC] rounded p-1'
            type='text'
            defaultValue={updatedPhoneNumber}
            onChange={handlePhoneNumberChange}
          />
        </div>
        <div className='flex justify-center pt-[70px]'>
          <button
            className='bg-[#4771B7] text-white p-3 rounded-lg'
            type='button'
            onClick={handleEditComplete}
          >
            편집 완료
          </button>
        </div>
      </div>
    </div>
  );
}

export default Modal;
