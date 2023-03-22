package actiOn.auth.utils;

public enum TokenPrefix {
    AUTHORIZATION("Authorization"),
    REFRESH("Refresh"),
    BEARER("Bearer ");

    private final String type;

    TokenPrefix(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
