/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import dataaccess.Connector;
import domain.Minute;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mariana
 */
public class MinuteDAO implements IMinuteDAO {

    @Override
    public void saveMinute(Minute minute, int idMeeting) {
                    try{
                        Connector connectorDataBase = new Connector();
                        Connection connectionDataBase = connectorDataBase.getConnection();
                        try{
                            
                            PreparedStatement insertMinuteStatment;
                            insertMinuteStatment = connectionDataBase.prepareStatement("INSERT INTO Minuta(nota, estado, pendiente, idReunion) VALUES(?,?,?,?) ");
                            insertMinuteStatment.setString(1, minute.getNote());
                            insertMinuteStatment.setString(2,  minute.getSate());
                            insertMinuteStatment.setString(3, minute.getDue());
                            insertMinuteStatment.setInt(4, idMeeting);
                            System.out.println(minute.getNote());
                            insertMinuteStatment.executeUpdate();
                            
                            connectorDataBase.disconnect();
                        }catch(SQLException sqlException) {
                            throw new IllegalStateException("Parameter index ", sqlException);
                        }
                    }catch(ClassNotFoundException ex) {
                        Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
    }
    
}
