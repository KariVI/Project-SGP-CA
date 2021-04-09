/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import domain.AgendaTopic;
import domain.Member;
import java.util.ArrayList;

/**
 *
 * @author Mariana
 */
public interface IAgendaTopic {
      public void save(AgendaTopic agendaTopic, int idMeeting);
      public ArrayList<AgendaTopic>  getAgendaTopics(int idReunion);
}
