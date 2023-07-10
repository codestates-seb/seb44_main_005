import { useState } from "react";

import { InputTitle } from "../../styles/StoreAdd/StoreAdd";
import { ImageInput, PreviewBox, PreviewImg } from "../../styles/StoreAdd/AddImages";

function AddImages() {
  const [sendImgs, setSendImgs] = useState([null, null, null]);
  const [firstImg, setFirstImg] = useState(null);
  const [secondImg, setSecondImg] = useState(null);
  const [thirdImg, setThirdImg] = useState(null);

  const saveImgFile = (e) => {
    const file = e.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onloadend = () => {
      if (e.target.name === "first") {
        const result = [...sendImgs];
        result[0] = file;
        setSendImgs(result);
        setFirstImg(reader.result);
      }
      else if (e.target.name === "second") {
        const result = [...sendImgs];
        result[1] = file;
        setSendImgs(result);
        setSecondImg(reader.result);
      }
      else if (e.target.name === "third") {
        const result = [...sendImgs];
        result[2] = file;
        setSendImgs(result);
        setThirdImg(reader.result);
      }
    }
  }

  return (
    <div className="flex mt-20 mb-6 ">
      <InputTitle>업체 이미지 첨부</InputTitle>
      <div className="ml-5 flex flex-col items-center">
        <ImageInput>
          <span>파일선택</span>
          <input
            className="hidden text-black"
            type="file"
            accept="image/*"
            onChange={saveImgFile}
            name="first"
          />
        </ImageInput>
        {
          firstImg ? <PreviewImg src={firstImg} alt="업체사진" /> :
          <PreviewBox>대표 이미지</PreviewBox>
        }
      </div>
      <div className="ml-5 flex flex-col items-center">
        <ImageInput>
          <span>파일선택</span>
          <input
            className="hidden text-black"
            type="file"
            accept="image/*"
            onChange={saveImgFile}
            name="second"
          />
        </ImageInput>
        {
          secondImg ? <PreviewImg src={secondImg} alt="업체사진" /> :
          <PreviewBox>2번째 이미지</PreviewBox>
        }
      </div>
      <div className="ml-5 flex flex-col items-center">
        <ImageInput>
          <span>파일선택</span>
          <input
            className="hidden text-black"
            type="file"
            accept="image/*"
            onChange={saveImgFile}
            name="third"
          />
        </ImageInput>
        {
          thirdImg ? <PreviewImg src={thirdImg} alt="업체사진" /> :
          <PreviewBox>3번째 이미지</PreviewBox>
        }
      </div>
    </div>
  );
}

export default AddImages;
