package dev.hoksv.common.jedis;

public abstract class RedisListener {

    public String name;
    public String channel;

    public RedisListener(String name, String channel) {
        this.name = name;
        this.channel = channel;
    }

    protected abstract void onMessage(String message);

    public String getName() {
        return name;
    }

    public String getChannel() {
        return channel;
    }
}
