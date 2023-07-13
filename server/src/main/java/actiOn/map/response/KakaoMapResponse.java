package actiOn.map.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class KakaoMapResponse {
    @JsonProperty("documents")
    private List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    @Getter
    @Setter
    public static class Document {
        @JsonProperty("address_name")
        private String addressName;

        @JsonProperty("x")
        private String longitude;

        @JsonProperty("y")
        private String latitude;

        // Getters and Setters
        // ...
    }
}
