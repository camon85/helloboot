package camonspring.helloboot;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloServiceTest {
  @Test
  void simpleHelloService() {
    SimpleHelloService helloService = new SimpleHelloService();
    String ret = helloService.sayHello("camon");
    assertThat(ret).isEqualTo("Hello camon");
  }

  @Test
  void helloDecoratorTest() {
    HelloDecorator helloDecorator = new HelloDecorator(name -> name);
    String ret = helloDecorator.sayHello("camon");
    assertThat(ret).isEqualTo("*camon*");
  }
}
