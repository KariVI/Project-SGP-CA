
package sistemacuerpoacademico;

import businessLogic.MeetingDAO;
import businessLogic.MinuteDAO;
import domain.Meeting;
import domain.Minute;


public class Main {

    public static void main(String[] args) {
        Minute minute = new Minute("Esta es una nota","Estado","Esto es un pendiente");
        int idMeeting = 1;
        MinuteDAO instance = new MinuteDAO();
        instance.saveMinute(minute, idMeeting);
       
    }
    
}
