
package businessLogic;

import dataaccess.Connector;
import domain.LGAC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LGACDAO implements ILGACDAO {
    
    public boolean save(LGAC lgac){
        try {
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String insertLgac = "INSERT INTO LGAC(nombre,descripcion) VALUES (?,?)";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertLgac);
                preparedStatement.setString(1, lgac.getName());
                preparedStatement.setString(2, lgac.getDescription());
              
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(LGACDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LGACDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public LGAC getLgacByName(String name) {
        return null;
        
    }
}
