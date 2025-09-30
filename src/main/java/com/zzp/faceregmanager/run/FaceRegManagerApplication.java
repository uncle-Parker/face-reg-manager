package com.zzp.faceregmanager.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"com.zzp.faceregmanager"})
@EntityScan("com.zzp.faceregmanager.po")
@EnableJpaRepositories("com.zzp.faceregmanager.repository")
public class FaceRegManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaceRegManagerApplication.class, args);
    }

}
