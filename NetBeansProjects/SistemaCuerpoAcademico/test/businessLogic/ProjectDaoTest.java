package businessLogic;

import domain.Project;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import static org.junit.runners.model.MultipleFailureException.assertEmpty;

/**
 *
 * @author Mariana
 */
public class ProjectDaoTest {
   
  
    @Test
    public void testSearchId(){ 
      ProjectDAO projectDAO= new ProjectDAO();
      Project projectAuxiliar;
      projectAuxiliar = new Project("Inteligencia artificial" ,"Descripcion","04/05/2021","05/11/2021");
      int idExpected = 1;
      int idProject = projectDAO.searchId(projectAuxiliar);
      assertEquals("Test id Project", idExpected, idProject);
    }
    
    @Test
    public void testSearchIdNotFound(){ 
      ProjectDAO projectDAO= new ProjectDAO();
      Project projectAuxiliar;
      projectAuxiliar = new Project("Inteligencia" ,"Descripcion","04/05/2021","05/11/2021");
      int idExpected=0;
      int idProject= projectDAO.searchId(projectAuxiliar);
      assertEquals("Test id Project", idExpected, idProject);
    }
    
    @Test
    public void testFindProjectById(){
        ProjectDAO projectDAO= new ProjectDAO();
        Project projectAuxiliar;
        projectAuxiliar = new Project("Inteligencia artificial" ,"Descripcion","04/05/2021","05/11/2021");
        int idProject= projectDAO.searchId(projectAuxiliar);
        assertTrue(projectDAO.findProjectById(idProject));
    }

    @Test
    public void testFindProjectByIdNotFound(){
        ProjectDAO projectDAO= new ProjectDAO();
        Project projectAuxiliar;
        projectAuxiliar = new Project("Inteligencia" ,"Descripcion","04/05/2021","05/11/2021");
        int idProject = projectDAO.searchId(projectAuxiliar);
        assertFalse(projectDAO.findProjectById(idProject));
    }

    @Test
    public void testSave(){
        ProjectDAO projectDAO= new ProjectDAO();
        Project projectAuxiliar;
        projectAuxiliar = new Project("Inteligencia artificial" ,"Descripcion","04/05/2021","05/11/2021");
        try {
            projectDAO.save(projectAuxiliar);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        int id = projectDAO.searchId(projectAuxiliar);
        //assertTrue(projectDAO.findProjectById(id));
    }
    
    @Test
    public void testGetProjectById(){
        ProjectDAO projectDAO= new ProjectDAO();
        Project projectExpected = new Project(1,"Inteligencia artificial" ,"Descripcion","04/05/2021","05/11/2021");
        Project projectActual = projectDAO.getProjectById(1);
        assertTrue(projectExpected.equals(projectActual));
    }
    
    @Test
    public void testGetProjects() {
        System.out.println("getProjects");
        ProjectDAO instance = new ProjectDAO();
        ArrayList<Project> result = instance.getProjects();
        assertNotNull(result);
    }  
}