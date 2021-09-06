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

/**
 * <p>Redis class.</p>
 *
 * @author andyl
 * @version $Id: $Id
 */
public class Redis extends JedisPubSub implements Closeable {
    private final Logger logger = Logger.getLogger("Redis");

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private JedisPool jedisPool = null;

    /**
     * <p>Constructor for Redis.</p>
     *
     * @param host a {@link java.lang.String} object
     * @param port a int
     */
    public Redis(String host, int port) {
        initializePool(host, port, null);
    }

    /**
     * <p>Constructor for Redis.</p>
     *
     * @param host a {@link java.lang.String} object
     * @param port a int
     * @param password a {@link java.lang.String} object
     */
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

    /**
     * <p>subscribe.</p>
     *
     * @param listener a {@link dev.hoksv.common.jedis.RedisListener} object
     */
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

    /**
     * Publishes a jedis message
     * @param channel the channel to send through
     * @param message the message
     */
    public void publish(String channel, String message) {
        this.getJedis().publish(channel, message);
    }

    /**
     * <p>getJedis.</p>
     *
     * @return a {@link redis.clients.jedis.Jedis} object
     */
    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    /** {@inheritDoc} */
    @Override
    public void close() {
        jedisPool.close();
    }
}
