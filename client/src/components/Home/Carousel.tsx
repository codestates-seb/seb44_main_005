import { Link } from 'react-router-dom';

import { CarouselImg, CarouselSection } from '../../styles/Home/Carousel';

function Carousel({ data }) {
  return (
    <CarouselSection>
      <CarouselImg src={data.img} alt="대표사진" />
      <div className="w-[420px]">
        <div className="carousel-title mb-[10px]">{data.storeName}</div>
        <div className="font-bold text-lg mb-[30px]">{data.body}</div>
        <Link
          to={`/category/${data.storeId}`}
          className="bg-white px-[10px] py-[8px] font-bold rounded-[10px]"
        >
          자세히 보기
        </Link>
      </div>
    </CarouselSection>
  );
}

export default Carousel;

