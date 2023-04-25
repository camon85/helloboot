package camonspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

public class HelloApiTests {
  @Test
  void helloApi() {
    // http localhost:8080/hello?name=camon
    TestRestTemplate testRestTemplate = new TestRestTemplate();
    ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(
        "http://localhost:8080/hello?name={name}", String.class, "camon");

    // status code 200
    assertThat(responseEntity.getStatusCode())
        .isEqualTo(HttpStatus.OK);

    // header(content-type) text/plain
    assertThat(responseEntity.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE))
        .startsWith(MediaType.TEXT_PLAIN_VALUE);

    // body Hello camon
    assertThat(responseEntity.getBody()).isEqualTo("Hello camon");

  }
}
