import { useEffect, useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

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

  // 오류메세지 상태 저장
  const [emailMessage, setEmailMessage] =
    useState('올바른 이메일 형식이 아닙니다.');
  const [nameMessage, setNameMessage] = useState('영문 숫자로만 입력해주세요.');
  const [passwordMessage, setPasswordMessage] = useState(
    '영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다.'
  );
  const [passwordConfirmMessage, setPasswordConfirmMessage] =
    useState('비밀번호가 일치하지 않습니다');
  const [phoneMessage, setPhoneMessage] =
    useState('전화번호에 -를 포함해주세요');
  const [isSubmitDisabled, setIsSubmitDisabled] = useState(true);

  //이메일 유효성

  const onChangeEmail = (e) => {
    const currentEmail = e.target.value;
    setEmail(currentEmail);
    const emailRegExp =
      /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}$/;
    if (emailRegExp.test(currentEmail)) {
      setEmailMessage('사용 가능한 이메일 입니다.');
    } else {
      setEmailMessage('올바른 이메일 형식이 아닙니다.');
    }
  };

  const onChangeName = (e) => {
    const currentName = e.target.value;
    setName(currentName);
    const nameRegExp = /^(?=.*[a-zA-Z])(?=.*[0-9]).{4,25}$/;

    if (nameRegExp.test(currentName)) {
      setNameMessage('사용 가능한 닉네임 입니다.');
    } else {
      setNameMessage('영문 숫자로만 입력해주세요.');
    }
  };

  const onChangePassword = (e) => {
    const currentPassword = e.target.value;
    setPassword(currentPassword);
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
    const phoneRegExp = /^(01[016789]{1})-[0-9]{4}-[0-9]{4}$/;
    let formattedNumber = '';
    if (currentPhone.length > 0) {
      formattedNumber = currentPhone.replace(
        /(\d{3})(\d{4})(\d{4})/,
        '$1-$2-$3'
      );
      setFormattedPhoneNumber(formattedNumber);
      if (phoneRegExp.test(formattedNumber)) {
        setPhoneMessage('올바른 전화번호 형식입니다.');
      } else {
        setPhoneMessage('전화번호에 -를 추가해주세요.');
      }
    }
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
          navigate('/login');
        }
      } catch (error) {
        console.error('회원가입 요청 중 오류가 발생했습니다', error);
        toast('회원가입을 실패했습니다');
      }
    } else {
      toast('🚨 가입조건을 모두 만족해주세요 !');
    }
  };

  const handleGoogleSignup = async (e) => {
    e.preventDefault();
    window.location.href = `${url}/oauth2/authorization/google`;
  };

  return (
    <StyleContainer>
      <ToastContainer
        toastClassName={
          'h-[20px] rounded-md text-sm font-medium bg-[#EDF1F8] text-[#4771B7] text-center mt-[70px]'
        }
        position="top-right"
        limit={1}
        closeButton={false}
        autoClose={3000}
        hideProgressBar
      />
      <RegisterContainer>
        <div className="flex pt-2">
          <Label htmlFor="email">이메일</Label>
          <InputContainer>
            <Input id="email" type="email" onChange={onChangeEmail} />
            <Message>{emailMessage}</Message>
          </InputContainer>
        </div>
        <div className="flex pt-2">
          <Label htmlFor="name">닉네임</Label>
          <InputContainer>
            <Input id="name" type="text" onChange={onChangeName} />
            <Message>{nameMessage}</Message>
          </InputContainer>
        </div>
        <div className="flex pt-2 pr-[12px]">
          <Label htmlFor="password">비밀번호</Label>
          <InputContainer>
            <Input id="password" type="password" onChange={onChangePassword} />
            <Message>{passwordMessage}</Message>
          </InputContainer>
        </div>
        <div className="flex pt-2 pr-[50px]">
          <Label htmlFor="passwordConfirm">비밀번호 확인</Label>
          <InputContainer>
            <Input
              id="passwordConfirm"
              type="password"
              onChange={onChangePasswordConfirm}
            />
            <Message>{passwordConfirmMessage}</Message>
          </InputContainer>
        </div>
        <div className="flex pt-2 pr-[15px] mb-[30px]">
          <Label htmlFor="phone">전화번호</Label>
          <InputContainer>
            <Input
              id="phone"
              type="text"
              value={formattedPhoneNumber}
              pattern="\d{3}-\d{3,4}-\d{4}"
              onChange={onChangePhone}
              maxLength={13}
            />
            <Message>{phoneMessage}</Message>
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
          // disabled={isSubmitDisabled}
        >
          <span className="font-medium">가입 진행하기</span>
        </Button>
      </RegisterContainer>
    </StyleContainer>
  );
}

export default Register;
