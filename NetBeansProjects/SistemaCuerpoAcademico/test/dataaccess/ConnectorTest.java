
package dataaccess;


import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class ConnectorTest {

    @Test
    public void testConnect() throws Exception {
        System.out.println("connect");
        Connector instance;
        instance = new Connector();
        assertNotNull(instance.getConnection());
    }

    @Test
    public void testDisconnect() throws Exception {
        System.out.println("disconnect");
        Connector instance = new Connector();
        instance.disconnect();
    }
}
