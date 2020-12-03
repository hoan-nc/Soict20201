package application;


import application.service.FileStorageService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

@SpringBootApplication
@ComponentScan
@EnableJpaRepositories(basePackages = "application")
@EnableScheduling
public class Application {

//    private static final Logger logger = LogManager.getLogger(Application.class);

    @Resource
    FileStorageService storageService;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
