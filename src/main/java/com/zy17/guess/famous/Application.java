package com.zy17.guess.famous;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableCaching
@EnableAsync
@SpringBootApplication
public class Application {


  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(Application.class);
    application.addListeners(new ApplicationPidFileWriter("guess.pid"));
    application.run(args);
  }

}
