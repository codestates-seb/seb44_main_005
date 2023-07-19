import { useState } from 'react';
import { Map, MapMarker, CustomOverlayMap } from 'react-kakao-maps-sdk';
import { Link } from 'react-router-dom';
import tw from 'tailwind-styled-components';

import { homeMapPropsType } from '../../intefaces/Home';

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
    {
      marker.map((el) => {
        return (
            <EventMarkerContainer
              position={{ lat: el.latitude, lng: el.longitude }}
              content={el}
              key={el.storeId}
            />
            );
          })
        }
    </Map>
  );
}

export default KakaoMap;

const EventMarkerContainer = ({ position, content }) => {
  const [isVisible, setIsVisible] = useState(false)

  return (
    <>
      <MapMarker
        position={position}
        onClick={() => setIsVisible(true)}
      />
      {isVisible && (
        <CustomOverlayMap position={position} yAnchor={1}>
          <OverlaySection>
            <button className="w-full text-right font-bold" onClick={() => setIsVisible(false)}>X</button>
            <Link to={`/category/${content.storeId}`} className="font-bold">{content.storeName}</Link>
          </OverlaySection>
        </CustomOverlayMap>
      )}
    </>
  )
}

const OverlaySection = tw.section`
  bg-white
  p-2
  rounded-[10px]
  border-2
  border-black
  flex flex-col
`;