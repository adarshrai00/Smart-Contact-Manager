package com.scm.config;


import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class AppConfig {



    @Bean
    public Cloudinary cloudinary() {




        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", "dan76z685",
                        "api_key", "812769756143142",
                        "api_secret", "lxzFNi37XOfgMJllV2yHDHQOJ_E"

                )
        );
    }
}
