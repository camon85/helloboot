package camonspring.helloboot;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MySpringApplication {
  public static void run(Class<?> applicationClass, String[] args) {
    AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
      @Override
      protected void onRefresh() {
        super.onRefresh();

        ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);
        DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
        dispatcherServlet.setApplicationContext(this); // 생략해도 spring container가 주입해준다.

        WebServer webServer = serverFactory.getWebServer(servletContext -> {
          servletContext.addServlet("dispatcherServlet", dispatcherServlet)
              .addMapping("/*"); // 모든 요청을 받아서 처리한다.
        });
        webServer.start();
      }
    };
    applicationContext.register(applicationClass);
    applicationContext.refresh();
  }

}
