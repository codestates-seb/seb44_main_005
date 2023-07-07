import { useEffect, useState } from 'react';
import tw from 'tailwind-styled-components';
import { FaChevronLeft, FaChevronRight } from 'react-icons/fa6';

import { recommend } from "../dummy/main";
import Carousel from "../components/Home/Carousel";
import KakaoMap from '../components/Home/KakaoMap';

function Home() {
  const [current, setCurrent] = useState(0);
  const moveStyle = {
    0: 'translate-x-0 bg-[#67C2E4]',
    1: 'translate-x-[-100vw] bg-[#879fa8]',
    2: 'translate-x-[-200vw] bg-[#e4c767]',
    3: 'translate-x-[-300vw] bg-[#678fe4]'
  }

  const arrowLeftHandler = () => {
    if (current === 0) {
      setCurrent(recommend.recommend.length - 1);
    }
    else {
      setCurrent(current - 1)
    }
  }

  const arrowRightHandler = () => {
    if (current === (recommend.recommend.length - 1)) {
      setCurrent(0);
    }
    else {
      setCurrent(current + 1)
    }
  }

  useEffect(() => {
    const interval = setInterval(() => {
      if (current === recommend.recommend.length - 1) {
        setCurrent(0);
      }
      else {
        setCurrent(current + 1)
      }
    }, 3000);
    return () => {
      clearInterval(interval);
    };
  }, [current])

  return (
    <section>
      <section className="relative">
        <CarouselBox className={`${moveStyle[current]} carousel-container`}>
          {recommend.recommend.map((el) => {
            return <Carousel data={el} />
          })}
        </CarouselBox>
        <LeftArrow onClick={arrowLeftHandler}>
          <FaChevronLeft size="50" color="white" />
        </LeftArrow>
        <RightArrow onClick={arrowRightHandler}>
          <FaChevronRight size="50" color="white" />
        </RightArrow>
      </section>
      <section className="w-[80%] mt-10 mx-auto">
        <div className="font-bold text-2xl mb-5">모든 레저 한눈에 보기</div>
        <div className="rounded-xl h-[600px]">
          <KakaoMap marker={recommend.data} />
        </div>
      </section>
    </section>
  );
}

export default Home;

const CarouselBox = tw.div`
  w-[400vw]
  flex
  overflow-hidden
  duration-1000
`;

const LeftArrow = tw.div`
  absolute top-[50%] left-[3%]
  translate-y-[-50%]
  cursor-pointer
`;

const RightArrow = tw.div`
  absolute top-[50%] right-[3%]
  translate-y-[-50%]
  cursor-pointer
`;