import { useState } from 'react';
import headerlogo from '../../assets/headerlogo.svg';
import close from '../../assets/close.svg';
import { 
  CloseButtonContainer,
  ModalAllContainer, 
  ModalBoxContainer ,
  CloseButton,
  LogoContainer,
  Logo,
  EditContainer,
  EditInput,
  EditMessage,
  EditComplete,
  EditConfirmButton
} from '../../styles/MyPage/Modal';

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
    <ModalAllContainer onClick={handleOutsideClick}>
      <ModalBoxContainer>
        <CloseButtonContainer>
          <CloseButton src={close} alt='close button' onClick={onClick} />
        </CloseButtonContainer>
        <LogoContainer>
          <Logo src={headerlogo} alt='logo' />
        </LogoContainer>
        <EditContainer>
          <p>닉네임</p>
          <EditInput
            type='text'
            defaultValue={updatedNickname}
            onChange={handleNicknameChange}
          />
        </EditContainer>
        {!isNicknameValid && (
           <EditMessage>
             <p className='text-red-500'>닉네임 편집은 영문, 숫자로만 가능합니다.</p>
          </EditMessage>
        )}
        <EditContainer>
          <p>연락처</p>
          <EditInput
            type='text'
            defaultValue={updatedPhoneNumber}
            onChange={handlePhoneNumberChange}
          />
        </EditContainer>
        <EditComplete>
          <EditConfirmButton
            type='button'
            onClick={handleEditComplete}
          >
            편집 완료
          </EditConfirmButton>
        </EditComplete>
      </ModalBoxContainer>
    </ModalAllContainer>
  );
}

export default Modal;
