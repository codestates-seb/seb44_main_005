import React, { useState } from 'react';
import tw from 'tailwind-styled-components';

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

function Register() {
  // 초기값 세팅 - 아이디, 닉네임, 비밀번호, 비밀번호확인, 이메일, 전화번호, 생년월일
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [email, setEmail] = useState();

  // 오류메세지 상태 저장
  const [emailMessage, setEmailMessage] =
    useState('올바른 이메일 형식이 아닙니다.');
  const [nameMessage, setNameMessage] = useState('영문 숫자로만 입력해주세요.');
  const [passwordMessage, setPasswordMessage] = useState(
    '영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다.'
  );
  const [passwordConfirmMessage, setPasswordConfirmMessage] =
    useState('비밀번호가 일치하지 않습니다');

  //이메일 유효성
  const onChangeEmail = (e) => {
    const currentEmail = e.target.value;
    setEmail(currentEmail);
    const emailRegExp =
      /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}$/;

    if (!emailRegExp.test(currentEmail)) {
      setEmailMessage('올바른 이메일 형식이 아닙니다.');
    } else {
      setEmailMessage('사용 가능한 이메일 입니다.');
    }
  };
  //닉네임 유효성
  const onChangeName = (e) => {
    const currentName = e.target.value;
    setName(currentName);
    const nameRegExp = /^(?=.*[a-zA-Z])(?=.*[0-9]).{4,25}$/;

    if (!nameRegExp.test(currentName)) {
      setNameMessage('영문 숫자로만 입력해주세요.');
    } else {
      setNameMessage('사용가능한 닉네임 입니다.');
    }
  };
  //비밀번호 유효성
  const onChangePassword = (e) => {
    const currentPassword = e.target.value;
    setPassword(currentPassword);
    const passwordRegExp =
      /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
    if (!passwordRegExp.test(currentPassword)) {
      setPasswordMessage(
        '영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다.'
      );
    } else {
      setPasswordMessage('안전한 비밀번호 입니다.');
    }
  };

  //비밀번호 확인 유효성
  const onChangePasswordConfirm = (e) => {
    const currentPasswordConfirm = e.target.value;
    setPasswordConfirm(currentPasswordConfirm);
    if (password !== currentPasswordConfirm) {
      setPasswordConfirmMessage('비밀번호가 일치하지 않습니다');
    } else {
      setPasswordConfirmMessage('비밀번호가 일치합니다');
    }
  };

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
            <Input id="phone" type="tel" />
            <Message>전화번호는 - 를 빼고 입력해주세요.</Message>
          </InputContainer>
        </div>
        <Button bgColor="#FFFFFF" color="#000000">
          <div className="flex justify-center items-center">
            <img src={google} className="mr-2" />
            <span className="font-medium">구글로 회원가입하기</span>
          </div>
        </Button>
        <Button bgColor="#4771B7" color="#FFFFFF">
          <span className="font-medium">가입 진행하기</span>
        </Button>
      </RegisterContainer>
    </StyleContainer>
  );
}

export default Register;
