
package dataaccess;


import java.sql.Connection;
import log.BusinessException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
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
