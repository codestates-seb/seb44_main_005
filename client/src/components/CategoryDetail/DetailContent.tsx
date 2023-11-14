import { useState } from 'react';
import { FaChevronLeft, FaChevronRight } from 'react-icons/fa6';
import { RiMapPinLine } from 'react-icons/ri';
import { FiClock, FiPhone } from 'react-icons/fi';
import { ToastContainer, toast } from 'react-toastify';
import { CopyToClipboard } from "react-copy-to-clipboard/src";

import {
  DetailCategoryName,
  DetailImg,
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
      <ToastContainer
        toastClassName={
          'h-[20px] rounded-md text-sm font-medium bg-[#EDF1F8] text-[#4771B7] text-center shadow-sm'
        }
        position="top-center"
        limit={10}
        closeButton={false}
        autoClose={2000}
        hideProgressBar
      />
      <DetailCategoryName>{data.category}</DetailCategoryName>
      <DetailTitle>{data.storeName}</DetailTitle>
      <ImgBox>
        {data.storeImages && (
          <div className="overflow-x-hidden">
            <div
              className="flex duration-500 min-w-[1200px] overflow-hidden ease-in-out"
              style={{
                transform: `translateX(${current * -600}px)`,
                width: `${data.storeImages.length * 600}px`,
              }}
            >
              {data.storeImages.map((src, idx) => (
                <DetailImg src={src} alt="업체사진" key={idx} />
              ))}
            </div>
            <div className="flex justify-center items-center mt-3">
              <div
                className="cursor-pointer my-auto arrow-left"
                onClick={arrowLeftHandler}
              >
                <FaChevronLeft
                  className="hover:fill-[#9dacc5] duration-500"
                  size="40"
                  color="#4771B7"
                />
              </div>
              <div className="mx-5 text-center text-xl font-bold text-[#4771B7]">
                {current + 1} / {data.storeImages.length}
              </div>
              <div
                className="cursor-pointer my-auto arrow-right"
                onClick={arrowRightHandler}
              >
                <FaChevronRight
                  className="hover:fill-[#9dacc5] duration-500"
                  size="40"
                  color="#4771B7"
                />
              </div>
            </div>
          </div>
        )}
      </ImgBox>
      <StoreInfoBox>
        <div className="flex mb-2">
          <RiMapPinLine color="#4771B7" size="25" />
          <div className="ml-2 mr-5">{data.address}</div>
          <CopyToClipboard text={data.address} onCopy={() => toast("클립보드에 복사되었습니다.")}>
            <button
              className="bg-[#4771B7] rounded-[5px] text-white text-xs px-3 py-1"
            >주소복사</button>
          </CopyToClipboard>
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
