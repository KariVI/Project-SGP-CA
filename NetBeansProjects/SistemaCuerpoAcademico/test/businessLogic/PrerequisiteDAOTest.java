
package businessLogic;

import domain.Member;
import domain.Prerequisite;
import java.util.ArrayList;
import log.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;


public class PrerequisiteDAOTest {
    
    public PrerequisiteDAOTest() {
    }


  
    
    @Test
    public void testsearchId() throws BusinessException {
        System.out.println("searchId");
        int idExpected = 1;
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        Prerequisite prerequisite=  new Prerequisite("Verificar situacion de FEIBook");
        int result = prerequisiteDAO.getId(prerequisite,1 );
        assertEquals(idExpected, result);
    }
    
    @Test
    public void testsearchNotFoundId() throws BusinessException {
        System.out.println("searchIdNotFound");
        int idExpected = 1;
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        Prerequisite prerequisite=  new Prerequisite("Situacion preinscripciones");
        int result = prerequisiteDAO.getId(prerequisite,1 );
        assertNotEquals(idExpected, result);
    }
    
    @Test
    public void testSavePrerequisites() throws BusinessException {
        System.out.println("savedSucessfulPrerequisites");
        Prerequisite prerequisite = new Prerequisite ("Presentacion de anteproyectos");
        Member assistant= new Member("7938268", "Maria Karen Cortes Verdin", "Responsable");
        prerequisite.setMandated(assistant);
        int idReunion = 2;
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        assertTrue(prerequisiteDAO.savedSucessfulPrerequisites(prerequisite,idReunion) );
        
        
    }

    @Test
       public void testUpdatePrerequisites() throws BusinessException {
        System.out.println("updatedSucessfulPrerequisites");
        Prerequisite prerequisite = new Prerequisite ("Aprobar GastroCafe");
        Member assistant= new Member("7938268", "Maria Karen Cortes Verdin", "Responsable");
        prerequisite.setMandated(assistant);
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        assertTrue(prerequisiteDAO.updatedSucessfulPrerequisite(3,prerequisite) );
        
    }
       
    @Test
    public void testDelete() throws BusinessException{
        System.out.println("delete");
        int idPrerequisite=4;
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        assertTrue(prerequisiteDAO.deletedSucessful(idPrerequisite));
    }
    
    @Test 
    public void testGetPrerequisites() throws BusinessException{
      System.out.println("getPrerequisites");
      PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
      ArrayList<Prerequisite> prerequisites;
      prerequisites= prerequisiteDAO.getPrerequisites(1);
      int sizeExpected=3;
      assertEquals(prerequisites.size(), sizeExpected);
      
    }
}
