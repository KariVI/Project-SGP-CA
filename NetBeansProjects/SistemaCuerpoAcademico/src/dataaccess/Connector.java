package dataaccess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import log.Log;
import java.io.FileInputStream;
import java.io.IOException;

public class Connector {
        private Connection connection;
        private String url;
        private String userName;
        private String userPassword;

        private void inicializar(){ 
            Properties properties = new Properties();
            FileInputStream fileInputStream;
            try {
                fileInputStream = new FileInputStream("./properties.properties");
                properties.load(fileInputStream);
            } catch (IOException ex) {
                Log.logException(ex);
            }
            url = properties.getProperty("url");
            userName = properties.getProperty("userName");
            userPassword = properties.getProperty("userPassword");
        }

        private void connect() throws ClassNotFoundException {

                connection = null;  
                inicializar();
                try{
                   
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    this.connection = DriverManager.getConnection(url, userName, userPassword);

                } catch (SQLException sqlException ) {
                     Log.logException(sqlException);
                } 
        } 

        public Connection getConnection() throws ClassNotFoundException {
                connect();
                return connection;
        }

        public void disconnect() {
                if(connection != null) {
                    try {
                        if(!connection.isClosed()) {
                            connection.close();   
                        }
                    } catch (SQLException sqlException) {
                          Log.logException(sqlException);
                    }


                }
        }
    }