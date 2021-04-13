package businessLogic;

import domain.Project;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

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
        assertTrue(projectDAO.save(projectAuxiliar));
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