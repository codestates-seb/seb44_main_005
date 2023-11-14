import { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';
import { FaChevronLeft, FaChevronRight } from 'react-icons/fa6';

import Carousel from '../components/Home/Carousel';
import KakaoMap from '../components/Home/KakaoMap';
import { CarouselBox, LeftArrow, RightArrow } from '../styles/Home/Home';
import { homeDataState } from '../store/homeAtom';
import { homeDataType } from '../intefaces/Home';

function Home() {
  const API_URL = import.meta.env.VITE_APP_API_URL;
  const [homeData, setHomeData] = useRecoilState<homeDataType>(homeDataState);
  const [current, setCurrent] = useState<number>(0);
  const moveStyle = {
    0: 'translate-x-0 bg-[#4770b755]',
    1: 'translate-x-[-100vw] bg-[#879fa8]',
    2: 'translate-x-[-200vw] bg-[#e4c767]',
    3: 'translate-x-[-300vw] bg-[#678fe4]',
  };

  const arrowLeftHandler = () => {
    if (current === 0) {
      setCurrent(homeData.recommend.length - 1);
    } else {
      setCurrent(current - 1);
    }
  };

  const arrowRightHandler = () => {
    if (current === homeData.recommend.length - 1) {
      setCurrent(0);
    } else {
      setCurrent(current + 1);
    }
  };

  const homeDataFetch = async () => {
    const res = await fetch(`${API_URL}/main`);
    const json = await res.json();
    setHomeData(json);
  };

  useEffect(() => { // 바깥으로 로직 분리시켜보기
    if (homeData.recommend) {
      const interval = setInterval(() => {
        if (current === homeData.recommend.length - 1) {
          setCurrent(0);
        } else {
          setCurrent(current + 1);
        }
      }, 3000);
      return () => {
        clearInterval(interval);
      };
    }
  }, [current, homeData]);

  useEffect(() => {
    window.scrollTo(0, 0);
    try {
      homeDataFetch();
    } catch (error) {
      console.error(error);
    }
  }, []);

  return (
    <section>
      <section className="relative overflow-x-hidden">
        <CarouselBox className={`${moveStyle[current]}`}>
          {homeData.recommend &&
            homeData.recommend.map((el) => {
              return <Carousel data={el} key={el.storeId} />;
            })}
        </CarouselBox>
        <LeftArrow onClick={arrowLeftHandler}>
          <FaChevronLeft className="arrow-left" size="60" color="white" />
        </LeftArrow>
        <RightArrow onClick={arrowRightHandler}>
          <FaChevronRight className="arrow-right" size="60" color="white" />
        </RightArrow>
      </section>
      <section className="w-[70%] mt-10 mx-auto">
        <div className="font-bold text-2xl mb-5">모든 레저 한눈에 보기</div>
        <div className="rounded-xl h-[600px] mb-20">
          {homeData.data && <KakaoMap marker={homeData.data} />}
        </div>
      </section>
    </section>
  );
}

export default Home;
