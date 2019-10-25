package com.purge.redisson.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RedissonConfig implements EnvironmentAware {

    private static final String PREFIX = "redis://";
    private static final String HOST_KEY = "spring.redis.host";
    private static final String PORT_KEY = "spring.redis.port";
    private static final String CLUSTER_KEY = "spring.redis.cluster.nodes";
    private static final String PASSWORD_KEY = "spring.redis.password";

    private Environment environment;

//    @Value("${spring.redis.host:}")
//    private String host;
//
//    @Value("${spring.redis.port:}")
//    private String port;
//
//    @Value("${spring.redis.cluster.nodes:}")
//    private String cluster;
//
//    @Value("${spring.redis.password:}")
//    private String password;

    @Bean
    public RedissonClient getRedisson() {

        String host = environment.getProperty(HOST_KEY);
        String password = environment.getProperty(PASSWORD_KEY);
        Config config = new Config();

        if (StringUtils.isNoneEmpty(host)) {
            String port = environment.getProperty(PORT_KEY);
            if (!StringUtils.isNoneEmpty(port)) {
                throw new IllegalArgumentException("[getRedisson] port is null.");
            }
            config.useSingleServer().setAddress(PREFIX + host + ":" + port);
            if (StringUtils.isNoneEmpty(password)) {
                config.useSingleServer().setPassword(password);
            }
        } else {
            String cluster = environment.getProperty(CLUSTER_KEY);
            if (!StringUtils.isNoneEmpty(cluster)) {
                throw new IllegalArgumentException("[getRedisson] cluster is null.");
            }
            String[] nodes = cluster.split(",");
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = PREFIX + nodes[i];
            }
            config.useClusterServers().setScanInterval(2000).addNodeAddress(nodes);
            if (StringUtils.isNoneEmpty(password)) {
                config.useClusterServers().setPassword(password);
            }
        }

        return Redisson.create(config);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}