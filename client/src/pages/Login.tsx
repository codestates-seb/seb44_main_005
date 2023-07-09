import React, { useState } from 'react';

import {
  InputContainer,
  StyleContainer,
  LoginContainer,
  IntroText,
} from '../styles/Login/Login';
import headerlogo from '../assets/headerlogo.svg';
import Button from '../components/Button/Button';
import google from '../assets/google.svg';

function Login() {
  const [id, setId] = useState('');
  const [password, setPassWord] = useState('');

  const onIdHandler = (event) => {
    setId(event.currentTarget.value);
  };

  const onPwHandler = (event) => {
    setPassWord(event.currentTarget.value);
  };
  const onClickHandler = () => {
    console.log('Hi');
  };

  return (
    <StyleContainer>
      <LoginContainer>
        <img src={headerlogo} className="pl-[30px]" />
        <IntroText>
          레저 스포츠는
          <br /> <span className="text-[#4771B7]">액티온</span>에서
          시작해보세요.
        </IntroText>
        <InputContainer>
          <div>
            <label className="font-medium">아이디</label>
            <input
              type="text"
              value={id}
              onChange={onIdHandler}
              className="border-[2px] h-[30px] w-[200px] ml-4 rounded-md mb-3 px-2"
            />
          </div>
          <div>
            <label className="font-medium">비밀번호</label>
            <input
              type="password"
              value={password}
              onChange={onPwHandler}
              className="border-[2px] h-[30px] w-[200px] ml-3 rounded-md mr-3 px-2"
            />
          </div>
        </InputContainer>
        <Button bgColor="#FFFFFF" color="#000000" onClick={onClickHandler}>
          <div className="flex justify-center items-center">
            <img src={google} className="mr-2" />
            <span className="font-medium">구글로 로그인하기</span>
          </div>
        </Button>
        <Button bgColor="#4771B7" color="#FFFFFF" onClick={onClickHandler}>
          <span className="font-medium">로그인</span>
        </Button>
      </LoginContainer>
    </StyleContainer>
  );
}

export default Login;
