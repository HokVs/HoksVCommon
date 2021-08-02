package dev.hoksv.common.database.sql.util;

import com.zaxxer.hikari.HikariConfig;

public class HikariUtils {

    public static HikariConfig generateConfig(String username, String password, String driverClassName, String jdbcURL, int connectionTimeout, int maximumPoolSize) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(jdbcURL);
        config.setConnectionTimeout(connectionTimeout);
        config.setMaximumPoolSize(maximumPoolSize);

        return config;
    }

    public static String generateURL(String jdurl, String host, String port, String database) {
        String url = jdurl.replace("{host}", host);
        url = url.replace("{port}", port);
        url = url.replace("{database}", database);

        return url;
    }
}
