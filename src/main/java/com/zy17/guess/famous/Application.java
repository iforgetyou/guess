package com.zy17.guess.famous;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
//    ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
//    run.addApplicationListener(new ApplicationPidFileWriter("./app.pid"));

    SpringApplication application = new SpringApplication(Application.class);
    application.addListeners(new ApplicationPidFileWriter("guess.pid"));
    application.run(args);
  }


}
