
package dataaccess;


import static org.junit.Assert.fail;
import org.junit.Test;

public class ConnectorTest {

    public ConnectorTest() {

    }
    @Test
    public void testDataBaseConnection(){
        Connector connector = new Connector();
        try {
            connector.connect();
            System.out.println("Conexion exitosa");
             
        } catch (ClassNotFoundException ex) {
          fail ("No conexion exitosa");
        }
    }
}
