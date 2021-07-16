package dev.hoksv.common.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.io.Closeable;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Redis extends JedisPubSub implements Closeable {
    private final Logger logger = Logger.getLogger("Redis");

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private JedisPool jedisPool = null;

    public Redis(String host, int port) {
        initializePool(host, port, null);
    }

    public Redis(String host, int port, String password) {
        initializePool(host, port, password);
    }

    private synchronized void initializePool(String host, int port, String password) {
        logger.info("Initializing Jedis pool");
        if(jedisPool != null) return;

        JedisPoolConfig poolConfig = new JedisPoolConfig();

        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);

        jedisPool = password == null
                ? new JedisPool(poolConfig, host, port)
                : new JedisPool(poolConfig, host, port, 3000, password);
    }

    public void subscribe(RedisListener ...listener) {
        Jedis jedisResource = jedisPool.getResource();
        RedisSubscriber jedisSub = new RedisSubscriber(listener);

        List<String> channels = Arrays.stream(listener).map(RedisListener::getChannel).collect(Collectors.toList());
        String[] objects = channels.toArray(String[]::new);

        executorService.submit(() -> {
            try {
                logger.info("Creating new thread for " + channels.size() + " channels");
                jedisResource.subscribe(jedisSub, objects);
                jedisPool.returnResource(jedisResource);
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    @Override
    public void close() {
        jedisPool.close();
    }
}
