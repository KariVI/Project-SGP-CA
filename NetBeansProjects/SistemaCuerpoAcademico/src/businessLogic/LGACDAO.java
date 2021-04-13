
package businessLogic;

import dataaccess.Connector;
import domain.LGAC;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LGACDAO {
    public void save(LGAC lgac){
        try {
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(LGACDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
