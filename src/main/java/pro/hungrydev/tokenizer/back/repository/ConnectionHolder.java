package pro.hungrydev.tokenizer.back.repository;

import org.postgresql.ds.PGPoolingDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionHolder {

    private static PGPoolingDataSource dataSource;
    public static final ConnectionHolder INSTANCE = new ConnectionHolder();

    static{
        try {
            dataSource = new PGPoolingDataSource();
            Properties props = new Properties();
            InputStream is = ConnectionHolder.class.getClassLoader().getResourceAsStream("db.properties");
            System.out.println(is);
            props.load(is);
            dataSource = new PGPoolingDataSource();
            dataSource.setDataSourceName(props.getProperty("datasource.name"));
            dataSource.setServerName(props.getProperty("server.name"));
            dataSource.setDatabaseName(props.getProperty("database.name"));
            dataSource.setPortNumber(Integer.parseInt(props.getProperty("server.port")));
            dataSource.setUser(props.getProperty("database.user"));
            dataSource.setPassword(props.getProperty("database.password"));
            dataSource.setMaxConnections(Integer.parseInt(props.getProperty("database.maxconnections")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
