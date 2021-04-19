package businessLogic;

import domain.Project;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import log.BusinessException;
/**
 *
 * @author Mariana
 */
public class ProjectDaoTest {
   
  
    @Test
    public void testSearchId() throws BusinessException{ 
      ProjectDAO projectDAO= new ProjectDAO();
      Project projectAuxiliar;
      projectAuxiliar = new Project("Inteligencia artificial" ,"Descripcion","04/05/2021","05/11/2021");
      int idExpected = 1;
      int idProject = projectDAO.searchId(projectAuxiliar);
      assertEquals("Test id Project", idExpected, idProject);
    }
    
    @Test
    public void testSearchIdNotFound() throws BusinessException{ 
      ProjectDAO projectDAO= new ProjectDAO();
      Project projectAuxiliar;
      projectAuxiliar = new Project("Inteligencia" ,"Descripcion","04/05/2021","05/11/2021");
      int idExpected=0;
      int idProject= projectDAO.searchId(projectAuxiliar);
      assertEquals("Test id Project", idExpected, idProject);
    }
    
    @Test
    public void testFindProjectById() throws BusinessException{
        ProjectDAO projectDAO= new ProjectDAO();
        Project projectAuxiliar;
        projectAuxiliar = new Project("Inteligencia artificial" ,"Descripcion","04/05/2021","05/11/2021");
        int idProject= projectDAO.searchId(projectAuxiliar);
        assertTrue(projectDAO.findProjectById(idProject));
    }

    @Test
    public void testFindProjectByIdNotFound() throws BusinessException{
        ProjectDAO projectDAO= new ProjectDAO();
        Project projectAuxiliar;
        projectAuxiliar = new Project("Inteligencia" ,"Descripcion","04/05/2021","05/11/2021");
        int idProject = projectDAO.searchId(projectAuxiliar);
        assertFalse(projectDAO.findProjectById(idProject));
    }

    @Test
    public void testSave() throws BusinessException{
        ProjectDAO projectDAO= new ProjectDAO();
        Project projectAuxiliar;
        projectAuxiliar = new Project("Inteligencia artificial" ,"Descripcion","04/05/2021","05/11/2021");
        assertTrue(projectDAO.save(projectAuxiliar));
    }
    
    @Test
    public void testGetProjectById() throws BusinessException{
        ProjectDAO projectDAO= new ProjectDAO();
        Project projectExpected = new Project(1,"Inteligencia artificial" ,"Descripcion","04/05/2021","05/11/2021");
        Project projectActual = projectDAO.getProjectById(1);
        assertTrue(projectExpected.equals(projectActual));
    }
    
    @Test
    public void testGetProjects() throws BusinessException {
        ProjectDAO projectDAO = new ProjectDAO();
        ArrayList<Project> result = projectDAO.getProjects();
        assertNotNull(result);
    }  
}