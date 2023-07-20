import { UserInfo, UserInfoTitle, MySpace } from '../../styles/MyPage/UserInfo';

function UserInfoSection({ nickname, email, phoneNumber }) {
  return (
    <MySpace>
      <UserInfo>
        <UserInfoTitle>닉네임</UserInfoTitle>
        <span>{nickname}</span>
      </UserInfo>
      <UserInfo>
        <UserInfoTitle>이메일</UserInfoTitle>
        <span>{email}</span>
      </UserInfo>
      <UserInfo>
        <UserInfoTitle>연락처</UserInfoTitle>
        <span>{phoneNumber}</span>
      </UserInfo>
    </MySpace>
  );
};

export default UserInfoSection;
