package camonspring.helloboot;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloServiceTest {

  @Test
  void simpleHelloService() {
    SimpleHelloService helloService = new SimpleHelloService(helloRepository);
    String ret = helloService.sayHello("camon");
    assertThat(ret).isEqualTo("Hello camon");
  }

  public static HelloRepository helloRepository = new HelloRepository() {
    @Override
    public Hello findHello(String name) {
      return null;
    }

    @Override
    public void increaseCount(String name) {

    }
  };
  @Test
  void helloDecoratorTest() {
    HelloDecorator helloDecorator = new HelloDecorator(name -> name);
    String ret = helloDecorator.sayHello("camon");
    assertThat(ret).isEqualTo("*camon*");
  }


}
