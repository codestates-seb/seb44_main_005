package actiOn.map.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeoLocation {
    private String latitude;
    private String longitude;

    public GeoLocation(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude =longitude;
    }
}
