package streaming.base;

import io.restassured.RestAssured;

public abstract class RestAssuredBase {

    protected static final String BASE_URI = "https://www.googleapis.com/youtube/v3";
    private static final String ENV_KEY_NAME = "YT_API_KEY";
    private static final String FALLBACK_API_KEY = "AIzaSyCF8nWbDfbjvVpJnaFNtmi109nMuLR9jgo";
    protected static final String DEFAULT_VIDEO_ID = "SnzoFzCAG5Y";

    static {
        RestAssured.baseURI = BASE_URI;
    }

    protected String getApiKey() {
        String fromEnv = System.getenv(ENV_KEY_NAME);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }
        if (FALLBACK_API_KEY != null && !FALLBACK_API_KEY.isBlank()) {
            return FALLBACK_API_KEY;
        }
        throw new IllegalStateException(
                "YouTube API key is missing"
        );
    }

    protected String getSampleVideoId() {
        return DEFAULT_VIDEO_ID;
    }
}
