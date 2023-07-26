import { ImgStyle, MiniButtonGrid, PhotoInputStyle } from '../../styles/MyPage/MyPage';
import defaultProfileImg from '../../assets/profile.svg';

function ProfileImageSection({ profileImageUrl, handlePhotoChange, handlePhotoRemove }) {
  return (
    <>
      <ImgStyle 
        src={profileImageUrl !== 'default image' ? profileImageUrl : defaultProfileImg}
        alt="profile img"
      />
      <MiniButtonGrid>
        <label htmlFor="photoInput">
          <input
            id="photoInput"
            type="file"
            accept="image/*"
            style={{ display: 'none' }}
            onChange={handlePhotoChange}
          />
          <PhotoInputStyle>사진 변경</PhotoInputStyle>
        </label>
        <label htmlFor="photoRemoveInput">
          <input
            id="photoRemoveInput"
            type="button"
            style={{ display: 'none' }}
            onClick={handlePhotoRemove}
          />
          <PhotoInputStyle>사진 삭제</PhotoInputStyle>
        </label>
      </MiniButtonGrid>
    </>
  );
}

export default ProfileImageSection;
