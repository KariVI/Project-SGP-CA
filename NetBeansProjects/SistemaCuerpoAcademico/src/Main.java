
import businessLogic.ProjectDAO;
import domain.Project;
import domain.Student;
import domain.Topic;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.BusinessException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mariana
 */
public class Main {
   public static void main(String[] args) {

       System.out.println("KK");
       try {
           Project project = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021","JDOEIJ804");
           Student student1 = new Student("S19014013", "Mariana Yazmin Vargas Segura");
           Student student2 = new Student("S19014023", "Karina Valdes Iglesias");
           project.setStudent(student1);
           project.setStudent(student2)
                   ;
           System.out.println("K");
           ProjectDAO projectDAO= new ProjectDAO();
           //projectDAO.addStudents(project);
           projectDAO.deleteStudents(project);
           //projectDAO.addStudents(project);
       } catch (BusinessException ex) {
           Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
       }
 

        }
}
