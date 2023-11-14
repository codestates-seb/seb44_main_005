import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import tw from 'tailwind-styled-components';
import { IoClose } from 'react-icons/io5';
import {
  Map,
  MapMarker,
  CustomOverlayMap,
  ZoomControl,
  MapTypeControl
} from 'react-kakao-maps-sdk';

import { homeMapPropsType } from '../../intefaces/Home';
import markerYellow from '../../assets/marker/marker-yellow.png';
import markerOrange from '../../assets/marker/marker-orange.png';
import markerGreen from '../../assets/marker/marker-green.png';
import markerBlue from '../../assets/marker/marker-blue.png';
import markerRed from '../../assets/marker/marker-red.png';

function KakaoMap({ marker }: homeMapPropsType) {
  return (
    <Map
      center={{ lat: 33.35910359999967, lng: 126.53439149999889 }}
      style={{
        width: "100%",
        height: "600px",
        borderRadius: "10px",
        border: "1px solid #4771B7",
      }}
      level={10}
    >
      <MapTypeControl position={kakao.maps.ControlPosition.TOPLEFT}/>
      <ZoomControl position={kakao.maps.ControlPosition.TOPRIGHT} />
      {marker.map((el) => (
        <EventMarkerContainer
          position={{ lat: el.latitude, lng: el.longitude }}
          content={el}
          key={el.storeId}
        />
      ))}
    </Map>
  );
}

export default KakaoMap;

const EventMarkerContainer = ({ position, content }) => {
  const [isVisible, setIsVisible] = useState(false);
  const [imgSrc, setImgSrc] = useState('');

  useEffect(() => {
    switch(content.category) {
      case '스노클링/다이빙':
        return setImgSrc(markerBlue);
      case '수상레저':
        return setImgSrc(markerYellow);
      case '서핑':
        return setImgSrc(markerRed);
      case '승마':
        return setImgSrc(markerGreen);
      case 'ATV':
        return setImgSrc(markerOrange);
    }
  }, [])

  return (
    <>
      <MapMarker
        position={position}
        onClick={() => setIsVisible((prev) => !prev)}
        image={{
          src: imgSrc && imgSrc, // 마커이미지의 주소입니다
          size: {
            width: 40,
            height: 43,
          }, // 마커이미지의 크기입니다
        }}
      />
      {isVisible && (
        <CustomOverlayMap position={position} yAnchor={1}>
          <OverlaySection>
            <div>
              <div className="mb-1 font-medium text-xs text-gray-400">{content.category}</div>
              <Link to={`/category/${content.storeId}`} className="font-bold hover:text-[#4771B7] duration-300">{content.storeName}</Link>
            </div>
            <button className="absolute top-1 right-2 text-right font-bold" onClick={() => setIsVisible(false)}><IoClose size="22" /></button>
          </OverlaySection>
        </CustomOverlayMap>
      )}
    </>
  )
}

const OverlaySection = tw.section`
  relative
  min-w-[150px]
  bg-white/60
  backdrop-blur-sm
  px-3 py-2
  border-[1px] border-black rounded-[10px]
`;