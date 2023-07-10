import { Map, MapMarker } from 'react-kakao-maps-sdk';

function LocationInfo({ location }) {
  return (
    <section className="w-[600px] mb-10">
      <div className="text-2xl font-semibold mb-3">위치 안내</div>
      <Map
        center={{ lat: location.latitude, lng: location.longitude }}
        style={{
          width: "100%",
          height: "360px",
          borderRadius: "10px",
          border: "1px solid #4771B7"
        }}
      >
      <MapMarker position={{ lat: location.latitude, lng: location.longitude }} />
    </Map>
    </section>
  );
}

export default LocationInfo;