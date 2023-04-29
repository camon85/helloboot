package camonspring.study;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ConditionalTest {
  @Test
  void conditionalTest() {
    // true
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
    ac.register(Config1.class);
    ac.refresh();

    MyBean bean = ac.getBean(MyBean.class);
    assertThat(bean).isNotNull();

    // false
    AnnotationConfigApplicationContext ac2 = new AnnotationConfigApplicationContext();
    ac2.register(Config2.class);
    ac2.refresh();

    assertThatThrownBy(() -> ac2.getBean(MyBean.class))
        .isInstanceOf(NoSuchBeanDefinitionException.class);

  }

  @Test
  void conditional_whit_contextRunner_Test() {
    // true
    new ApplicationContextRunner().withUserConfiguration(Config1.class)
        .run(context -> {
          assertThat(context).hasSingleBean(MyBean.class);
          assertThat(context).hasSingleBean(Config1.class);
        });

    // false
    new ApplicationContextRunner().withUserConfiguration(Config2.class)
        .run(context -> {
          assertThat(context).doesNotHaveBean(MyBean.class);
          assertThat(context).doesNotHaveBean(Config2.class);
        });

  }

  @Configuration
  @Conditional(TrueCondition.class)
  static class Config1 {
    @Bean
    MyBean myBean() {
      return new MyBean();
    }
  }

  @Configuration
  @Conditional(FalseCondition.class)
  static class Config2 {
    @Bean
    MyBean myBean() {
      return new MyBean();
    }
  }

  static class MyBean {

  }

  static class TrueCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
      return true;
    }
  }

  static class FalseCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
      return false;
    }
  }

}
