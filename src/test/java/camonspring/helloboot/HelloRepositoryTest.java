package camonspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@HelloBootTest
public class HelloRepositoryTest {

  @Autowired JdbcTemplate jdbcTemplate;
  @Autowired HelloRepository helloRepository;

  @Test
  void findHelloFailed() {
    Assertions.assertThat(helloRepository.findHello("camon")).isNull();
  }

  @Test
  void increaseCountTest() {
    Assertions.assertThat(helloRepository.countOf("camon")).isEqualTo(0);

    helloRepository.increaseCount("camon");
    Assertions.assertThat(helloRepository.countOf("camon")).isEqualTo(1);

    helloRepository.increaseCount("camon");
    Assertions.assertThat(helloRepository.countOf("camon")).isEqualTo(2);
  }
}
