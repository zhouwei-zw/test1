package com.example.demo.service;

import com.example.demo.entity.RedissonProperties;
import org.redisson.config.Config;

public interface RedissonConfigService {
    /**
     * 根据不同的Redis配置策略创建对应的Config
     * @param redissonProperties
     * @return Config
     */
    Config createRedissonConfig(RedissonProperties redissonProperties);
}
