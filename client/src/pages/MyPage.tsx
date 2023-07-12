// import { dummyBio } from "../../dummy/mypage";
import dummyImg from '../dummy/mypagedummy.jpeg';
import {
  BusinessCategory,
  BusinessSpace,
  ButtonGrid,
  ButtonStyle,
  ImgStyle,
  MiniButtonGrid,
  MiniButtonStyle,
  MySpace,
  NicknameAccent,
  TopSpace,
  UserInfo,
  UserInfoTitle,
  BusinessCategoryTitle,
  MyPageContainer,
  MyBioContainer,
} from '../styles/MyPage/MyPage';

function MyPage() {
  return (
    <MyPageContainer>
        <MyBioContainer>
            <MySpace>
                <ButtonGrid>
                <ButtonStyle type="button">편집</ButtonStyle>
                </ButtonGrid>
                <TopSpace>
                    <ImgStyle src={dummyImg} alt="dummy bio img" />
                    <MiniButtonGrid>
                        <MiniButtonStyle type="button">사진 변경</MiniButtonStyle>
                        <MiniButtonStyle type="button">사진 삭제</MiniButtonStyle>
                    </MiniButtonGrid>
                    <NicknameAccent>Taewoo Kim</NicknameAccent>
                </TopSpace>
                <MySpace>
                    <UserInfo>
                        <UserInfoTitle>닉네임</UserInfoTitle>
                        <span>Taewoo Kim</span>
                    </UserInfo>
                    <UserInfo>
                        <UserInfoTitle>이메일</UserInfoTitle>
                        <span>abc123@naver.com</span>
                    </UserInfo>
                    <UserInfo>
                        <UserInfoTitle>연락처</UserInfoTitle>
                        <span>010-1234-5678</span>
                    </UserInfo>
                </MySpace>
            </MySpace>
            <MySpace>
                <ButtonGrid>
                    <ButtonStyle type="button">등록한 업체보기</ButtonStyle>
                </ButtonGrid>
                <BusinessSpace>
                    <BusinessCategory>
                        <BusinessCategoryTitle>업태</BusinessCategoryTitle>
                        <span>스포츠 및 여가관련 서비스업</span>
                        <span>스포츠 및 여가관련 서비스업</span>
                        <span>스포츠 및 여가관련 서비스업</span>
                    </BusinessCategory>
                    <BusinessCategory>
                        <BusinessCategoryTitle>업종</BusinessCategoryTitle>
                        <span>레저 좋아</span>
                        <span>레저 화이팅</span>
                        <span>태우네 레저</span>
                    </BusinessCategory>
                    <BusinessCategory>
                        <BusinessCategoryTitle>업태</BusinessCategoryTitle>
                        <span>언더워터플레이 함덕점</span>
                        <span>언더워터플레이 애월점</span>
                        <span>언더워터플레이 김녕점</span>
                    </BusinessCategory>
                </BusinessSpace>
            </MySpace>
        </MyBioContainer>
    </MyPageContainer>
  );
}

export default MyPage;
