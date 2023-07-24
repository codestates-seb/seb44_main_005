import { ImgStyle, MiniButtonGrid, PhotoInputStyle } from '../../styles/MyPage/MyPage';
import defaultProfileImg from '../../assets/profile.svg';

function ProfileImageSection({ profileImageUrl, handlePhotoChange, handlePhotoRemove }) {
  return (
    <>
      {profileImageUrl !== 'default image' ? (
        <ImgStyle 
          src={profileImageUrl}
          alt="profile img"
        />
      ) : (
        <ImgStyle 
          src={defaultProfileImg}
          alt='profile img'
        />
      )}
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
