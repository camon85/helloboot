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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

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

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  @Conditional(BooleanCondition.class)
  @interface BooleanConditional {
    boolean value();
  }

  @Configuration
  @BooleanConditional(true)
  static class Config1 {
    @Bean
    MyBean myBean() {
      return new MyBean();
    }
  }

  @Configuration
  @BooleanConditional(false)
  static class Config2 {
    @Bean
    MyBean myBean() {
      return new MyBean();
    }
  }

  static class MyBean {

  }

  static class BooleanCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
      Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional.class.getName());
      return (Boolean) annotationAttributes.get("value");
    }
  }

}
