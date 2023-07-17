import { useEffect, useState } from 'react';

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
  // const CLIENT_ID = import.meta.env.VITE_APP_CLIENT_ID;
  // const GOOGLE_REDIRECT_URI = import.meta.env.VITE_APP_REDIRECT_URI;
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
  const [isSubmitDisabled, setIsSubmitDisabled] = useState(true);
  // const setIsLoginState = useSetRecoilState(isLoginState);

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
      setPasswordConfirmMessage('비밀번호가 일치합니다');
    } else {
      setPasswordConfirmMessage('비밀번호가 일치하지 않습니다');
    }
  };

  const onChangePhone = (e) => {
    const currentPhone = e.target.value;
    setPhone(currentPhone);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!isSubmitDisabled) {
      await fetch(`${url}/signup`, {
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
      }).then(() => navigate('/login'));
    } else {
      alert('조건에 맞게 회원정보를 입력해주세요.');
    }
  };

  const handleGoogleSignup = async (e) => {
    e.preventDefault();
    window.location.href = `${url}/oauth2/authorization/google`;
    // 'https://accounts.google.com/o/oauth2/auth?' +
    // `client_id=${CLIENT_ID}&` +
    // `redirect_uri=${GOOGLE_REDIRECT_URI}&` +
    // 'response_type=token&' +
    // 'scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile';
    // const parsedHash = new URLSearchParams(window.location.hash);
    // console.log('2');
    // const accessToken = parsedHash.get('access_token');
    // sessionStorage.setItem('access_token', accessToken);

    // setIsLoginState(true);
    // navigate('/home');
  };

  useEffect(() => {
    // 모든 유효성 검사가 통과한 경우에만 가입 진행하기 버튼 활성화
    if (
      emailMessage === '사용 가능한 이메일 입니다.' &&
      nameMessage === '사용 가능한 닉네임 입니다.' &&
      passwordMessage === '안전한 비밀번호 입니다.' &&
      passwordConfirmMessage === '비밀번호가 일치합니다'
    ) {
      setIsSubmitDisabled(false);
    } else {
      setIsSubmitDisabled(true);
    }
  }, [emailMessage, nameMessage, passwordMessage, passwordConfirmMessage]);

  return (
    <StyleContainer>
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
              type="tel"
              onChange={onChangePhone}
              maxLength={13}
            />
            <Message>전화번호는 - 를 넣고 입력해주세요.</Message>
          </InputContainer>
        </div>
        <Button
          bgColor="#FFFFFF"
          color="#000000"
          clickHandler={handleGoogleSignup}
        >
          <div className="flex justify-center items-center">
            <img src={google} className="mr-2" />
            {/* <a
              href="https://daae-222-232-33-89.ngrok-free.app/oauth2/authorization/google"
              className="font-medium"
            > */}
            <span className="font-medium">구글로 회원가입하기</span>
            {/* </a> */}
          </div>
        </Button>
        <Button
          bgColor="#4771B7"
          color="#FFFFFF"
          clickHandler={handleSubmit}
          disabled={isSubmitDisabled}
        >
          <span className="font-medium">가입 진행하기</span>
        </Button>
      </RegisterContainer>
    </StyleContainer>
  );
}

export default Register;
