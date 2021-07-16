package dev.hoksv.common.jedis;

import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class RedisSubscriber extends JedisPubSub {

    private final static Logger logger = Logger.getLogger("Jedis Subscriber");

    private final Map<String, RedisListener> listeners = new HashMap<>();

    public RedisSubscriber(RedisListener ...listeners) {
        for (RedisListener listener : listeners) {
            this.listeners.put(listener.getChannel(), listener);
        }
    }
    @Override
    public void onMessage(String channel, String message) {
        RedisListener listener = listeners.get(channel);
        if(listener != null)
            listener.onMessage(message);

    }
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        logger.info("Client has subscribed to channel " + channel + " (" + subscribedChannels + ") total");
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println("Client is Unsubscribed from channel : "+ channel);
        System.out.println("Client is Subscribed to "+ subscribedChannels + " no. of channels");
    }
}