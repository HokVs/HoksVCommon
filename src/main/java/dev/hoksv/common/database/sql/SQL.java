package dev.hoksv.common.database.sql;

import java.io.Closeable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SQL implements Closeable {
    private ConnectionManager connectionManager;

    /**
     * The thread executor service used by mysql
     */
    private final ExecutorService threadPool;

    public SQL() {
        this(10);
    }

    /**
     * Creates a SQL instance with specified threads
     * @param maxThreads Max threads you want, default 10.
     */
    public SQL(int maxThreads) {
        threadPool = Executors.newFixedThreadPool(maxThreads);
    }

    public boolean connect(String host, String port, String username, String password, String database) {
        return connect(host, port, username, password, database, SQLTypes.MYSQL);
    }

    /**
     * Connect to a MySQL database.
     *
     * @param host     the host for connecting to the database
     * @param port     the port (by default 3306)
     * @param username username used to connect to the database
     * @param password password used to connect to the database
     * @param database name of the database to connect to
     * @return TRUE if connection was successful, FALSE if an error occurred
     */
    public boolean connect(String host, String port, String username, String password, String database, SQLTypes type) {
        connectionManager = new ConnectionManager(host, port, username, password, database);
        return connectionManager.open(type);
    }

    /**
     * Closes the SQL connection
     * and stops the thread executor service.
     */
    public void close() {
        if(connectionManager != null)
            connectionManager.close();

        if(threadPool != null)
            threadPool.shutdown();
    }
}
