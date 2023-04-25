package camonspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloControllerTest {
  @Test
  void helloTest() {
    HelloController helloController = new HelloController(name -> name);
    String ret = helloController.hello("camon");
    Assertions.assertThat(ret).isEqualTo("camon");
  }

  @Test
  void failHelloControllerTest() {
    HelloController helloController = new HelloController(name -> name);
    Assertions.assertThatThrownBy(() -> {
          helloController.hello(null);
        })
        .isInstanceOf(IllegalArgumentException.class);

    Assertions.assertThatThrownBy(() -> {
          helloController.hello("");
        })
        .isInstanceOf(IllegalArgumentException.class);

  }
}
