
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
    public void testGetPrerequisiteDescription() throws BusinessException {
        System.out.println("getPrerequisiteDescription");
        int id = 2;
        PrerequisiteDAO instance = new PrerequisiteDAO();
        String expected = "Verificar situacion de FEIBook";
        String result = instance.getPrerequisiteDescription(id,1 );
        assertEquals(expected, result);
  
    }
    
    @Test
    public void testGetPrerequisiteDescriptionFailed() throws BusinessException {
        System.out.println("getPrerequisiteDescriptionFailed");
        int id = 3;
        PrerequisiteDAO instance = new PrerequisiteDAO();
        String expected = "Situacion preinscripciones";
        String result = instance.getPrerequisiteDescription(id,1 );
        assertNotEquals(expected, result);
  
    }
    
    @Test
    public void testsearchId() throws BusinessException {
        System.out.println("searchId");
        int idExpected = 2;
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
        int idReunion = 1;
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
      int idExpected=1;
      PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
      ArrayList<Prerequisite> prerequisites;
      prerequisites=prerequisiteDAO.getPrerequisites(1);
      int result=prerequisites.get(0).getKey();
      assertEquals(idExpected, result);
      
    }
}
