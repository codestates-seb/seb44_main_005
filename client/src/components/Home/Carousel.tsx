import tw from 'tailwind-styled-components';

function Carousel({ data }) {
  return (
    <CarouselSection>
      <CarouselImg src={data.img} alt="대표사진" />
      <div className="w-[420px]">
        <div className="carousel-title mb-[10px]">{data.store_name}</div>
        <div className="font-bold text-lg mb-[30px]">{data.body}</div>
        <button className="bg-white px-[10px] py-[8px] font-bold rounded-[10px]">자세히 보기</button>
      </div>
    </CarouselSection>
  );
}

export default Carousel;

const CarouselSection = tw.section`
  w-[100vw] h-[400px]
  mb-5
  flex justify-center items-center
`;

const CarouselImg = tw.img`
  w-[400px] h-[300px]
  object-cover
  rounded-[10px]
  shadow-[2px_3px_4px_3px_rgba(0,0,0,0.4)]
  mr-20
`;