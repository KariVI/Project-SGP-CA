package dataaccess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import log.Log;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Connector {
        private Connection connection;
        Properties properties = new Properties();
         FileInputStream fi;
        
        private String url;
        private String userName;
        private String userPassword;

        public void inicializar() throws FileNotFoundException, IOException{
            this.fi = new FileInputStream("./properties.properties");
            properties.load(fi);
            url = properties.getProperty("url");
            userName = properties.getProperty("userName");
            userPassword = properties.getProperty("userPassword");
        }

        public void connect() throws ClassNotFoundException, IOException{
                connection = null;
                inicializar();
                try{
                   
                     Class.forName("com.mysql.cj.jdbc.Driver");
                    this.connection = DriverManager.getConnection(url, userName, userPassword);

                } catch (SQLException sqlException ) {
                     Log.logException(sqlException);
                } 
        } 

        public Connection getConnection() throws ClassNotFoundException, IOException{
                connect();
                return connection;
        }

        public void disconnect() throws SQLException {
                if(connection != null) {
                        if(!connection.isClosed()) {
                                try{
                                  connection.close();
                                }catch(SQLException sqlException) {
                                       Log.logException(sqlException);

                        }

                }
        }
    }
}