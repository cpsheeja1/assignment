package com.bitcoin.assignment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {

    @Value("${redis.primary.host}")
    private String primary_host;

    @Value("${redis.primary.port}")
    private int primary_port;

    @Primary
    @Bean(name = "jedisPrimaryConnectionFactory")
    JedisConnectionFactory jedisPrimaryConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration(this.primary_host,
                        this.primary_port);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

   @Bean(name = "primaryRedisTemplate")
    public StringRedisTemplate primaryRedisTemplate() {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(jedisPrimaryConnectionFactory());
        return redisTemplate;
    }
}
