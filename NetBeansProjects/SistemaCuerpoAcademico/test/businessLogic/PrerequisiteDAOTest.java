/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import domain.Member;
import domain.Prerequisite;
import org.junit.Test;
import static org.junit.Assert.*;


public class PrerequisiteDAOTest {
    
    public PrerequisiteDAOTest() {
    }


    
     @Test
    public void testGetPrerequisiteDescription() {
        System.out.println("getPrerequisiteDescription");
        int id = 2;
        PrerequisiteDAO instance = new PrerequisiteDAO();
        String expected = "Verificar situacion de FEIBook";
        String result = instance.getPrerequisiteDescription(id,1 );
        assertEquals(expected, result);
  
    }
    
    @Test
    public void testGetPrerequisiteDescriptionFailed() {
        System.out.println("getPrerequisiteDescriptionFailed");
        int id = 3;
        PrerequisiteDAO instance = new PrerequisiteDAO();
        String expected = "Situacion preinscripciones";
        String result = instance.getPrerequisiteDescription(id,1 );
        assertNotEquals(expected, result);
  
    }
    
    @Test
    public void testsearchId() {
        System.out.println("searchId");
        int idExpected = 2;
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        Prerequisite prerequisite=  new Prerequisite("Verificar situacion de FEIBook");
        int result = prerequisiteDAO.getId(prerequisite,1 );
        assertEquals(idExpected, result);
    }
    
    @Test
    public void testsearchNotFoundId() {
        System.out.println("searchIdNotFound");
        int idExpected = 1;
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        Prerequisite prerequisite=  new Prerequisite("Situacion preinscripciones");
        int result = prerequisiteDAO.getId(prerequisite,1 );
        assertNotEquals(idExpected, result);
    }
    
    @Test
    public void testSavePrerequisites() {
        System.out.println("savePrerequisites");
        Prerequisite prerequisite = new Prerequisite ("Presentacion de anteproyectos");
        Member assistant= new Member("7938268", "Maria Karen Cortes Verdin", "Responsable");
        prerequisite.setMandated(assistant);
        int idReunion = 1;
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        assertTrue(prerequisiteDAO.savePrerequisites(prerequisite,idReunion) );
        
        
    }

    @Test
       public void testUpdatePrerequisites() {
        System.out.println("updatePrerequisites");
        Prerequisite prerequisite = new Prerequisite ("Aprobar GastroCafe");
        Member assistant= new Member("7938268", "Maria Karen Cortes Verdin", "Responsable");
        prerequisite.setMandated(assistant);
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        assertTrue(prerequisiteDAO.updatePrerequisite(3,prerequisite) );
        
    }
    

}
