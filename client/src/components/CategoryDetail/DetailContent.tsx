import tw from 'tailwind-styled-components';
import { RiMapPinLine } from 'react-icons/ri';
import { FiClock, FiPhone } from 'react-icons/fi';

function DetailContent({ data }) {
  return (
    <section className="w-[600px] mb-14">
      <DetailCategoryName>{data.category}</DetailCategoryName>
      <DetailTitle>{data.storeName}</DetailTitle>
      <ImgBox>
        <ThumbnailImg src={data.storeImages[0]} alt="업체사진" />
        <DetailImgs src={data.storeImages[1]} alt="업체사진" />
        <DetailImgs src={data.storeImages[2]} alt="업체사진" />
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

const DetailCategoryName = tw.div`
  text-xl text-[#787878] font-medium
  mb-1
`;

const DetailTitle = tw.div`
  text-3xl font-bold
  border-b-[1px] border-[#4771B7]
  pb-3 mb-10
`;

const ImgBox = tw.div`
  flex flex-wrap justify-between
  w-[570px]
  mx-auto
`;

const ThumbnailImg = tw.img`
  w-[570px] h-[400px]
  mb-2
  mx-auto
  object-cover
`;

const DetailImgs = tw.img`
  w-[280px] h-[200px]
  object-cover
`;

const StoreInfoBox = tw.div`
  mt-10 p-5
  font-medium
  border-[1px] border-[#4771B7]
  rounded-[5px]
`;