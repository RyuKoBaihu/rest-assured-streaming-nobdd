package streaming.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class YouTubeApi {

    private final String apiKey;

    public YouTubeApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public Response getVideos(String part, String id) {
        return given()
                .accept(ContentType.JSON)
                .queryParam("part", part)
                .queryParam("id", id)
                .queryParam("key", apiKey)
                .when()
                .get("/videos")
                .then()
                .extract()
                .response();
    }

    public Response getCaptions(String videoId) {
        return given()
                .accept(ContentType.JSON)
                .queryParam("part", "snippet")
                .queryParam("videoId", videoId)
                .queryParam("key", apiKey)
                .when()
                .get("/captions")
                .then()
                .extract()
                .response();
    }
}
