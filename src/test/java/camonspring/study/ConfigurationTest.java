package camonspring.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationTest {
  @Test
  void configurationTest() {
    MyConfig myConfig = new MyConfig();
    Bean1 bean1 = myConfig.bean1();
    Bean2 bean2 = myConfig.bean2();

    // common이 다른 객체
    Assertions.assertThat(bean1.common).isNotSameAs(bean2.common);
  }

  @Test
  void configurationTest2() {
    /*
      @Configuration 애너테이션에는 proxyBeanMethods = true 설정이 되어 있다
      자동으로 proxy 객체가 생성되어 spring 에서 관리된다.
    */
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
    ac.register(MyConfig.class);
    ac.refresh();

    Bean1 bean1 = ac.getBean(Bean1.class);
    Bean2 bean2 = ac.getBean(Bean2.class);

    // common이 동일한 객체
    Assertions.assertThat(bean1.common).isSameAs(bean2.common);
  }

  @Test
  void proxyCommonMethod() {
    // spring 의 도움 없이 proxy 동작 구현해보기
    MyConfigProxy myConfigProxy = new MyConfigProxy();
    Bean1 bean1 = myConfigProxy.bean1();
    Bean2 bean2 = myConfigProxy.bean2();

    // common이 동일한 객체
    Assertions.assertThat(bean1.common).isSameAs(bean2.common);
  }

  static class MyConfigProxy extends MyConfig {
    private Common common;

    @Override
    Common common() {
      if (this.common == null) {
        this.common = super.common();
      }
      return this.common;
    }
  }

  @Configuration
  static class MyConfig {
    @Bean
    Common common() {
      return new Common();
    }

    @Bean
    Bean1 bean1() {
      return new Bean1(common());
    }

    @Bean
    Bean2 bean2() {
      return new Bean2(common());
    }
  }

  static class Bean1 {
    private final Common common;

    Bean1(Common common) {
      this.common = common;
    }
  }

  static class Bean2 {
    private final Common common;

    Bean2(Common common) {
      this.common = common;
    }
  }

  static class Common {

  }

}
