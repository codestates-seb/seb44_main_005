package actiOn.map.service;

import actiOn.map.response.GeoLocation;
import actiOn.map.response.KakaoMapResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;


@Service

public class KakaoMapService {
    @Value("${kakao.url}")
    @Getter
    private String KAKAO_MAP_API_URL;

    @Value("${kakao.access-key}")
    @Getter
    private String KAKAO_REST_API_KEY;

    public GeoLocation addressToLocation(String address) { // 주소를 받아서 api 요청을 보내고, KakaoMapResponse로 받음
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "KakaoAK " + KAKAO_REST_API_KEY);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        String url = KAKAO_MAP_API_URL + "?query=" + address;

        ResponseEntity<KakaoMapResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, KakaoMapResponse.class);
        KakaoMapResponse response = responseEntity.getBody();

        // 응답에서 위도와 경도 추출
        if (response != null && response.getDocuments() != null && !response.getDocuments().isEmpty()) {
            KakaoMapResponse.Document document = response.getDocuments().get(0);
            String latitude = document.getLatitude();
            String longitude = document.getLongitude();
            return new GeoLocation(latitude, longitude); // 위도 경도를 담은 GeoLocation 객체를 반환
        }

        return null; // 주소에 대한 결과가 없는 경우 처리
    }
}
