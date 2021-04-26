package dataaccess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.BusinessException;
import log.Log;

public class Connector {
        private Connection connection;
        //Properties properties = new Properties();
        //private String host = properties.getProperty("host");
        private String host = "localhost";
        private int port = 3306;
        private String dataBaseName = "CuerpoAcademico";
        private String url = "jdbc:mysql://" + host + ":" + String.valueOf(port) + "/" + dataBaseName;
        private String userName = "integrante";
        private String userPassword = "password";

        public void connect() throws BusinessException, ClassNotFoundException {
                connection = null;
                try{
                     Class.forName("com.mysql.cj.jdbc.Driver");
                    this.connection = DriverManager.getConnection(url, userName, userPassword);
                    
                } catch (SQLException sqlException ) {
                  
                   throw new BusinessException("DataBase connection failed ", sqlException);
                } 
        } 
         
        public Connection getConnection() throws ClassNotFoundException, BusinessException {
                connect();
                return connection;
        }

        public void disconnect() throws SQLException{
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