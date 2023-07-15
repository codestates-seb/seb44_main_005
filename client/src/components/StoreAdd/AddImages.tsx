import { useState } from "react";
import { useRecoilState } from "recoil";

import { InputTitle } from "../../styles/StoreAdd/StoreAdd";
import { DeleteImgBtn, ImageInput, PreviewBox, PreviewImg } from "../../styles/StoreAdd/AddImages";
import { DetailImgsState, FirstImgState } from "../../store/StoreAdd";

function AddImages() {
  const [sendFirstImg, setSendFirstImg] = useRecoilState(FirstImgState);
  const [sendDetailImgs, setSendDetailImgs] = useRecoilState(DetailImgsState);
  const [firstImg, setFirstImg] = useState(null);
  const [detailImgs, setDetailImgs] = useState([]);

  const saveFirstImgFile = (e) => {
    const file = e.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onloadend = () => {
      setFirstImg(reader.result);
    }
    setSendFirstImg(file);
  }

  const saveDetailImgFile = (e) => {
    const files = e.target.files;
    for (let i = 0; i < files.length; i++) {
      const reader = new FileReader();
      reader.readAsDataURL(files[i]);
      reader.onloadend = () => {
        setDetailImgs((prevImgs) => [...prevImgs, reader.result]);
      }
    }
    setSendDetailImgs((prevImgs) => [...prevImgs, ...files]);
  }

  const detailImgDeleteHandler = (idx: number) => {
    const result = [...detailImgs].filter((_, detailIdx) => detailIdx !== idx);
    const sendResult = [...detailImgs].filter((_, detailIdx) => detailIdx !== idx);
    setDetailImgs(result);
    setSendDetailImgs(sendResult);
  }

  return (
    <section>
      <div className="flex mb-6 ">
        <InputTitle>대표 이미지 등록</InputTitle>
        <div className="ml-5">
          <ImageInput>
            <span>파일선택</span>
            <input
              className="hidden text-black"
              type="file"
              accept="image/*"
              onChange={saveFirstImgFile}
            />
          </ImageInput>
          {
            firstImg ? <PreviewImg src={firstImg} alt="업체사진" /> :
            <PreviewBox>대표 이미지</PreviewBox>
          }
        </div>
      </div>
      <div className="flex mb-6 ">
        <InputTitle>상세 이미지 등록</InputTitle>
        <div className="ml-5">
          <ImageInput>
            <span>파일선택</span>
            <input
              className="hidden text-black"
              type="file"
              accept="image/*"
              onChange={saveDetailImgFile}
              multiple={true}
            />
          </ImageInput>
          <div className="w-[750px] flex flex-wrap">
            {
              detailImgs ? detailImgs.map((detailImg, idx) => {
                return (
                  <div className="mr-5 mb-3" key={idx}>
                    <PreviewImg src={detailImg} alt="업체사진" />
                    <DeleteImgBtn
                      onClick={() => detailImgDeleteHandler(idx)}
                    >삭제</DeleteImgBtn>
                  </div>
                );
              }) : null
            }
          </div>
        </div>
      </div>
    </section>
  );
}

export default AddImages;