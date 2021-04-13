/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

package businessLogic;

import domain.AgendaTopic;
import java.util.ArrayList;


public interface IAgendaTopic {
      public boolean save(AgendaTopic agendaTopic, int idMeeting);
      public ArrayList<AgendaTopic>  getAgendaTopics(int idReunion);
}
 */