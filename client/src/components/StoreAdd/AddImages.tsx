import { useRecoilState, useSetRecoilState } from "recoil";
import { useSearchParams } from "react-router-dom";

import { InputTitle } from "../../styles/StoreAdd/StoreAdd";
import { DeleteImgBtn, ImageInput, PreviewBox, PreviewImg } from "../../styles/StoreAdd/AddImages";
import {
  DetailImgsState,
  FirstImgState,
  SendDetailImgsState,
  SendFirstImgState
} from "../../store/storeAddAtom";

function AddImages({ fetchImgsCount }) {
  const API_URL = import.meta.env.VITE_APP_API_URL;
  const [firstImg, setFirstImg] = useRecoilState(FirstImgState);
  const [detailImgs, setDetailImgs] = useRecoilState(DetailImgsState);
  const setSendFirstImg = useSetRecoilState(SendFirstImgState);
  const [sendDetailImgs, setSendDetailImgs] = useRecoilState(SendDetailImgsState);
  const [searchParams] = useSearchParams();
  const storeId = searchParams.get('store_id');
  const accessToken = sessionStorage.getItem('Authorization');

  const saveFirstImgFile = (e) => {
    const file = e.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onloadend = () => {
      setFirstImg(String(reader.result))
    }
    setSendFirstImg(file);
  }

  const saveDetailImgFile = (e) => {
    const files = e.target.files;
    if (detailImgs.length + files.length > 9) {
      return alert('상세 이미지는 최대 9장까지 업로드 할 수 있습니다.');
    }
    for (let i = 0; i < files.length; i++) {
      const reader = new FileReader();
      reader.readAsDataURL(files[i]);
      reader.onloadend = () => {
        setDetailImgs((prevImgs) => [...prevImgs, reader.result]);
        setSendDetailImgs((prevImgs) => [...prevImgs, files[i]]);
      }
    }
  }

  const detailImgDeleteHandler = (idx: number) => {
    const path = location.pathname.substring(6);
    if (path === '/edit') {
      if (detailImgs.length <= 3) {
        return alert('상세 이미지는 최소 3장 이상 등록해야합니다.');
      }
      if (detailImgs[idx][0] === 'h') {
        fetch(`${API_URL}/storeImages/${storeId}?link=${detailImgs[idx]}`, {
          method: 'DELETE',
          headers: {
            'Authorization': accessToken
          }
        });
      }
    }
    const result = [...detailImgs].filter((_, detailIdx) => detailIdx !== idx);
    const sendResult = [...sendDetailImgs].filter((_, detailIdx) => detailIdx !== idx - fetchImgsCount);
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