package streaming.tests;

import streaming.base.RestAssuredBase;
import streaming.client.YouTubeApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Tag("youtube")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class YouTubeVideoTests extends RestAssuredBase {

    private YouTubeApi api;
    private String videoId;

    @BeforeEach
    void init() {
        this.api = new YouTubeApi(getApiKey());
        this.videoId = getSampleVideoId();
    }

    @Test
    @Order(1)
    @DisplayName("Given that I have a YouTube video id")
    void shouldReturnVideo() {
        Response resp = api.getVideos("status,contentDetails,snippet", videoId);
        assertThat(resp.statusCode(), is(200));
        assertThat(resp.path("items.size()"), greaterThan(0));
    }

    @Test
    @Order(2)
    @DisplayName("And it have duration > 0)")
    void shouldHaveDuration() {
        Response resp = api.getVideos("contentDetails", videoId);
        assertThat(resp.statusCode(), is(200));

        String iso = resp.path("items[0].contentDetails.duration");
        assertThat(iso, notNullValue());

        Duration d = Duration.parse(iso); // Corrigido
        assertThat(d.getSeconds(), greaterThan(0L));
    }

    @Test
    @Order(3)
    @DisplayName("And it is not null")
    void shouldCheckEmbeddable() {
        Response resp = api.getVideos("status", videoId);
        assertThat(resp.statusCode(), is(200));

        Boolean embeddable = resp.path("items[0].status.embeddable");
        assertThat(embeddable, notNullValue());
        System.out.println("embeddable = " + embeddable);
    }

    @Test
    @Order(4)
    @DisplayName("and Title is not blank")
    void shouldHaveTitle() {
        Response resp = api.getVideos("snippet", videoId);
        assertThat(resp.statusCode(), is(200));

        String title = resp.path("items[0].snippet.title");
        assertThat(title, not(isEmptyOrNullString()));
    }

    @Test
    @Order(5)
    @DisplayName("And the captions return is 200")
    void shouldListCaptions() {
        Response resp = api.getCaptions(videoId);
        assertThat(resp.statusCode(), is(200));
        assertThat(resp.path("items"), notNullValue());
    }
}
