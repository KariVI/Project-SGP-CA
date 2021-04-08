
package businessLogic;

import dataaccess.Connector;
import domain.Prerequisite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrerequisiteDAO implements IPrerequisiteDAO{
    public void savePrerequisites(Prerequisite prerequisite, int idReunion) {
        try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertPrerequisite = "INSERT INTO Prerequisito(descripcion, idReunion) VALUES (?,?)";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertPrerequisite);
                
                preparedStatement.setString(1, prerequisite.getDescription());
                preparedStatement.setInt(2, idReunion);
                
                preparedStatement.executeUpdate();
                connectorDataBase.disconnect();
            } catch (SQLException sqlException) {
                throw new IllegalStateException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getPrerequisiteDescription(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
