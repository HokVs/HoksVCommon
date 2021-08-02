package dev.hoksv.common.database.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.hoksv.common.database.sql.util.HikariUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {

    private HikariDataSource dataSource;
    private final Logger logger = Logger.getLogger("[Hikari]");

    private final String host;
    private final String port;
    private final String username;
    private final String password;
    private final String database;

    private final int connectionTimeout;
    private final int maximumPoolsize;

    public ConnectionManager(String host, String port, String username, String password, String database, int connectionTimeout, int maximumPoolsize) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
        this.connectionTimeout = connectionTimeout;
        this.maximumPoolsize = maximumPoolsize;
    }

    public ConnectionManager(String host, String port, String username, String password, String database) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
        this.connectionTimeout = 5000;
        this.maximumPoolsize = 10;
    }

    public Connection getConnection() {
        if (isClosed())
            throw new IllegalStateException("Connection is not open.");

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean open() {
        return open(SQLTypes.MYSQL);
    }

    public boolean open(SQLTypes type) {
        try {
            HikariConfig config = HikariUtils.generateConfig(username, password, type.getDriverName(), HikariUtils.generateURL(type.getDriverURL(), host, port, database), connectionTimeout, maximumPoolsize);
            this.dataSource = new HikariDataSource(config);
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            return false;
        }
    }

    public void close() {
        if (isClosed())
            throw new IllegalStateException("Connection is not open.");

        this.dataSource.close();
    }

    public boolean isClosed() {
        return dataSource == null || dataSource.isClosed();
    }

}