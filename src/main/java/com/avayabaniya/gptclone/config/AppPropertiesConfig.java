package com.avayabaniya.gptclone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class AppPropertiesConfig {

    private final Environment env;

    @Autowired
    public AppPropertiesConfig(Environment env){
        this.env = env;
    }

    public String getProperty(String propertyKey) {
        return env.getProperty(propertyKey);
    }

    public String getJwtSecret(){
        return this.env.getProperty("authentication.jwt.jwtUtils.jwtSecret");
    }

    public int getJwtExpirationMs(){
        return Integer.parseInt(this.env.getProperty("authentication.jwt.jwtUtils.jwtExpirationMs"));
    }

    public Long getRefreshTokenDurationMs(){
        return Long.valueOf(this.env.getProperty("authentication.refreshtoken.RefreshTokenService.refreshTokenDurationMs"));
    }


    public String getOpenApiUrl() {
        return this.getProperty("openapi.url");
    }

    public String getOpenApiClientId() {
        return this.getProperty("openapi.clientid");
    }

}
