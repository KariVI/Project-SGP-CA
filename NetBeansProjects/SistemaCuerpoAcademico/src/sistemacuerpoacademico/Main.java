
package sistemacuerpoacademico;

import businessLogic.MeetingDAO;
import domain.Meeting;


public class Main {

    public static void main(String[] args) {
        MeetingDAO meetingDAO= new MeetingDAO();
        Meeting meetingAuxiliar;
        meetingAuxiliar = new Meeting("Revisión de avances en proyectos actuales" ,"04/05/2021","11:30");
        System.out.println(meetingDAO.searchId(meetingAuxiliar) );
        String prerequisite= "Avances de la propuesta del GastroCafe";
        
       
    }
    
}
