import { useEffect, useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';

import {
  StyleContainer,
  RegisterContainer,
  Message,
  InputContainer,
  Input,
  Label,
} from '../styles/Register/Register';
import Button from '../components/Button/Button';
import google from '../assets/google.svg';
import { useNavigate } from 'react-router-dom';

function Register() {
  const url = import.meta.env.VITE_APP_API_URL;
  const navigate = useNavigate();
  // 초기값 세팅 - 아이디, 닉네임, 비밀번호, 비밀번호확인, 이메일, 전화번호, 생년월일
  //useRef ->객체관리....
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');
  const [phone, setPhone] = useState('');

  const [isClicked, setIClicked] = useState(false);

  // 오류메세지 상태 저장
  const [emailMessage, setEmailMessage] = useState('');
  const [nameMessage, setNameMessage] = useState('');
  const [passwordMessage, setPasswordMessage] = useState('');
  const [passwordConfirmMessage, setPasswordConfirmMessage] = useState('');
  const [phoneMessage, setPhoneMessage] = useState('');
  const [isSubmitDisabled, setIsSubmitDisabled] = useState(true);

  //이메일 유효성

  const onChangeEmail = (e) => {
    const currentEmail = e.target.value;
    setEmail(currentEmail);
    if (!currentEmail) {
      setEmailMessage('');
      return;
    }
    const emailRegExp =
      /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}$/;
    if (currentEmail && emailRegExp.test(currentEmail)) {
      setEmailMessage('사용 가능한 이메일 입니다.');
    } else {
      setEmailMessage('올바른 이메일 형식이 아닙니다.');
    }
  };

  const onChangeName = (e) => {
    const currentName = e.target.value;
    setName(currentName);
    if (!currentName) {
      setNameMessage('');
      return;
    }
    const nameRegExp = /^[a-zA-Z0-9]{4,}$/;

    if (nameRegExp.test(currentName)) {
      setNameMessage('사용 가능한 닉네임 입니다.');
    } else {
      setNameMessage('영문, 숫자로만 4자 이상으로 입력해주세요.');
    }
  };

  const onChangePassword = (e) => {
    const currentPassword = e.target.value;
    setPassword(currentPassword);
    if (!currentPassword) {
      setPasswordMessage('');
      return;
    }
    const passwordRegExp =
      /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
    if (passwordRegExp.test(currentPassword)) {
      setPasswordMessage('안전한 비밀번호 입니다.');
    } else {
      setPasswordMessage(
        '영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다.'
      );
    }
  };

  const onChangePasswordConfirm = (e) => {
    const currentPasswordConfirm = e.target.value;
    if (!currentPasswordConfirm) {
      setPasswordConfirmMessage('');
      return;
    }
    if (password === currentPasswordConfirm) {
      setPasswordConfirmMessage('비밀번호가 일치합니다.');
    } else {
      setPasswordConfirmMessage('비밀번호가 일치하지 않습니다.');
    }
  };
  const [formattedPhoneNumber, setFormattedPhoneNumber] = useState('');

  const onChangePhone = (e) => {
    const currentPhone = e.target.value;
    setPhone(currentPhone);
    if (!currentPhone) {
      setPhoneMessage('');
      return;
    }
    const phoneRegExp = /^(010)-[0-9]{4}-[0-9]{4}$/;
    let formattedNumber = '';

    formattedNumber = currentPhone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');

    setPhone(formattedNumber);
    setFormattedPhoneNumber(formattedNumber);

    if (phoneRegExp.test(formattedNumber)) {
      setPhoneMessage('올바른 전화번호 형식입니다.');
    } else {
      setPhoneMessage('전화번호에 -를 제외하고 입력해 주세요.');
    }
    console.log(formattedPhoneNumber);
  };

  useEffect(() => {
    // 모든 유효성 검사가 통과한 경우에만 가입 진행하기 버튼 활성화
    if (
      emailMessage === '사용 가능한 이메일 입니다.' &&
      nameMessage === '사용 가능한 닉네임 입니다.' &&
      passwordMessage === '안전한 비밀번호 입니다.' &&
      passwordConfirmMessage === '비밀번호가 일치합니다.' &&
      phoneMessage === '올바른 전화번호 형식입니다.'
    ) {
      setIsSubmitDisabled(false);
    } else {
      setIsSubmitDisabled(true);
    }
  }, [
    emailMessage,
    nameMessage,
    passwordMessage,
    passwordConfirmMessage,
    phoneMessage,
  ]);
  const handleSubmit = async (e) => {
    e.preventDefault();
    setIClicked(true);
    if (isClicked) {
      return;
    }
    if (!isSubmitDisabled) {
      try {
        const res = await fetch(`${url}/signup`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            email: email,
            password: password,
            phoneNumber: phone,
            nickname: name,
          }),
        });
        if (res.ok) {
          toast('회원가입을 성공했습니다 !');
          setTimeout(() => {
            navigate('/login');
          }, 2000);
        } else if (res.status === 403) {
          toast('🚨 중복된 이메일입니다.');
          setTimeout(() => {
            setIClicked(false);
          }, 3000);
        } else if (res.status === 409) {
          toast('🚨 중복된 닉네임입니다.');
          setTimeout(() => {
            setIClicked(false);
          }, 3000);
        } else if (res.status === 422) {
          toast('🚨 중복된 전화번호입니다.');
          setTimeout(() => {
            setIClicked(false);
          }, 3000);
        }
      } catch (error) {
        console.error('회원가입 요청 중 오류가 발생했습니다', error);
        setTimeout(() => {
          setIClicked(false);
        }, 3000);
      }
    } else {
      alert('🚨 가입조건을 모두 만족해주세요 !');
      setTimeout(() => {
        setIClicked(false);
      }, 3000);
    }
  };

  const handleGoogleSignup = async (e) => {
    e.preventDefault();
    window.location.href = `${url}/oauth2/authorization/google`;
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      handleSubmit(e);
    }
  };

  return (
    <StyleContainer>
      <ToastContainer
        toastClassName={
          'h-[20px] rounded-md text-sm font-medium bg-[#EDF1F8] text-[#4771B7] text-center mt-[70px]'
        }
        position="top-center"
        limit={1}
        closeButton={false}
        autoClose={3000}
        hideProgressBar
      />
      <RegisterContainer>
        <div className="flex pt-2">
          <Label htmlFor="email">이메일</Label>
          <InputContainer>
            <Input
              id="email"
              type="email"
              onChange={onChangeEmail}
              onKeyDown={handleKeyDown}
            />
            <Message
              className={
                emailMessage === '사용 가능한 이메일 입니다.'
                  ? 'text-[#21C55D]'
                  : 'text-red-600'
              }
            >
              {emailMessage}
            </Message>
          </InputContainer>
        </div>
        <div className="flex pt-2">
          <Label htmlFor="name">닉네임</Label>
          <InputContainer>
            <Input
              id="name"
              type="text"
              onChange={onChangeName}
              onKeyDown={handleKeyDown}
            />
            <Message
              className={
                nameMessage === '사용 가능한 닉네임 입니다.'
                  ? 'text-[#21C55D]'
                  : 'text-red-600'
              }
            >
              {nameMessage}
            </Message>
          </InputContainer>
        </div>
        <div className="flex pt-2 pr-[12px]">
          <Label htmlFor="password">비밀번호</Label>
          <InputContainer>
            <Input
              id="password"
              type="password"
              onChange={onChangePassword}
              onKeyDown={handleKeyDown}
            />
            <Message
              className={
                passwordMessage === '안전한 비밀번호 입니다.'
                  ? 'text-[#21C55D]'
                  : 'text-red-600'
              }
            >
              {passwordMessage}
            </Message>
          </InputContainer>
        </div>
        <div className="flex pt-2 pr-[50px]">
          <Label htmlFor="passwordConfirm">비밀번호 확인</Label>
          <InputContainer>
            <Input
              id="passwordConfirm"
              type="password"
              onChange={onChangePasswordConfirm}
              onKeyDown={handleKeyDown}
            />
            <Message
              className={
                passwordConfirmMessage === '비밀번호가 일치합니다.'
                  ? 'text-[#21C55D]'
                  : 'text-red-600'
              }
            >
              {passwordConfirmMessage}
            </Message>
          </InputContainer>
        </div>
        <div className="flex pt-2 pr-[15px] mb-[30px]">
          <Label htmlFor="phone">전화번호</Label>
          <InputContainer>
            <Input
              id="phone"
              type="text"
              value={formattedPhoneNumber}
              onChange={onChangePhone}
              onKeyDown={handleKeyDown}
              maxLength={13}
            />
            <Message
              className={
                phoneMessage === '올바른 전화번호 형식입니다.'
                  ? 'text-[#21C55D]'
                  : 'text-red-600'
              }
            >
              {phoneMessage}
            </Message>
          </InputContainer>
        </div>
        <Button
          bgColor="#FFFFFF"
          color="#000000"
          clickHandler={handleGoogleSignup}
        >
          <div className="flex justify-center items-center">
            <img src={google} className="mr-2" />
            <span className="font-medium">구글로 회원가입하기</span>
          </div>
        </Button>
        <Button
          bgColor="#4771B7"
          color="#FFFFFF"
          clickHandler={handleSubmit}
          disabled={isClicked}
        >
          <span className="font-medium">가입 진행하기</span>
        </Button>
      </RegisterContainer>
    </StyleContainer>
  );
}

export default Register;
