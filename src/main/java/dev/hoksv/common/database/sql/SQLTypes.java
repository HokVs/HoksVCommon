package dev.hoksv.common.database.sql;

public enum SQLTypes {

    MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql://{host}:{port}/{database}"),
    POSTGRES("org.postgresql.ds.PGSimpleDataSource", "jdbc:postgresql://{host}:{port}/{database}");

    private final String driverName;
    private final String driverURL;

    SQLTypes(String driverName, String driverURL) {
        this.driverName = driverName;
        this.driverURL = driverURL;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverURL() {
        return driverURL;
    }
}
