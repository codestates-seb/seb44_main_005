import { useState } from 'react';
import { FaChevronLeft, FaChevronRight } from 'react-icons/fa6';
import { RiMapPinLine } from 'react-icons/ri';
import { FiClock, FiPhone } from 'react-icons/fi';

import {
  DetailCategoryName,
  DetailTitle,
  ImgBox,
  StoreInfoBox,
} from '../../styles/CategoryDetail/DetailContent';
import { useRecoilValue } from 'recoil';
import { CategoryDetailState } from '../../store/categoryDetailAtom';

function DetailContent() {
  const [current, setCurrent] = useState(0);
  const data = useRecoilValue(CategoryDetailState);

  const arrowLeftHandler = () => {
    if (current === 0) {
      setCurrent(data.storeImages.length - 1);
    } else {
      setCurrent(current - 1);
    }
  };

  const arrowRightHandler = () => {
    if (current === data.storeImages.length - 1) {
      setCurrent(0);
    } else {
      setCurrent(current + 1);
    }
  };

  return (
    <section className="w-[600px] mb-14">
      <DetailCategoryName>{data.category}</DetailCategoryName>
      <DetailTitle>{data.storeName}</DetailTitle>
      <ImgBox>
        <div className="cursor-pointer my-auto" onClick={arrowLeftHandler}>
          <FaChevronLeft size="50" color="#4771B7" />
        </div>
        <div className='w-[400px] h-[320px]'>
          {data.storeImages && (
            <>
              <img className="w-[400px] h-[300px] object-cover" src={data.storeImages[current]} alt="업체사진" />
              <div className="mt-3 text-center font-bold text-[#4771B7]">{current + 1} / {data.storeImages.length}</div>
            </>
          )}
        </div>
        <div className="cursor-pointer my-auto" onClick={arrowRightHandler}>
          <FaChevronRight size="50" color="#4771B7" />
        </div>
      </ImgBox>
      <StoreInfoBox>
        <div className="flex mb-2">
          <RiMapPinLine color="#4771B7" size="25" />
          <div className="ml-2">{data.address}</div>
        </div>
        <div className="flex mb-2">
          <FiClock color="#4771B7" size="23" />
          <div className="ml-2">매일 10:00 ~ 18:00(18:00에 운영 종료)</div>
        </div>
        <div className="flex mb-2">
          <FiPhone color="#4771B7" size="23" />
          <div className="ml-2">{data.contact}</div>
        </div>
        <div>{data.body}</div>
      </StoreInfoBox>
    </section>
  );
}

export default DetailContent;
