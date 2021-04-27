
package dataaccess;


import java.sql.Connection;
import log.BusinessException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConnectorTest {

  
   
    /**
     * Test of connect method, of class Connector.
     * @throws java.lang.Exception
     */
    @Test
    public void testConnect() throws Exception {
        System.out.println("connect");
        Connector instance;
        instance = new Connector();
         instance.connect();
        assertNotNull(instance.getConnection());
    }

    @Test
    public void testDisconnect() throws Exception {
        System.out.println("disconnect");
        Connector instance = new Connector();
        instance.disconnect();
    }
}
