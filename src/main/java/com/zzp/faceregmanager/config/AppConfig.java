package com.zzp.faceregmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @BelongsProject: face-reg-manager
 * @BelongsPackage: com.zzp.faceregmanager.config
 * @Author: zhouzpa
 * @CreateTime: 2025-09-29  16:04
 * @Description: TODO
 * @Version: 1.0
 */
// 配置类
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
