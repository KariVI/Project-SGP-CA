
package businessLogic;

import domain.LGAC;
import java.util.ArrayList;
import log.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;

public class LGACDAOTest {
    
    public LGACDAOTest() {
    }

    @Test
    public void testSave() throws BusinessException {
        System.out.println("savedSucessful");
        LGAC lgac;
        lgac = new LGAC("Tecnologías de software", "Se orienta al estudio de diversas propiedades, enfoques, métodos de modelado y herramientas que conforman cada una de las diversas tecnologías aplicables al desarrollo del software con vistas a su adaptación, mejora y sustitución en el medio nacional");
        LGACDAO lgacDAO = new LGACDAO();
        boolean result = lgacDAO.savedSucessful(lgac);
        assertTrue(result);
       
    }

    @Test
    public void testGetLgacByName() throws BusinessException {
        System.out.println("getLgacByName");
        String name = "Gestión, modelado y desarrollo de software";
        LGACDAO lgacDAO = new LGACDAO();
        LGAC expected=new LGAC("Gestión, modelado y desarrollo de software",  "Se orienta al estudio de los diversos métodos y enfoques para la gestión, modelado y desarrollo de software, de manera que se obtenga software de calidad. Gestión de las diversas etapas del proceso de desarrollo, incluyendo hasta la medición del proceso y artefactos. Modelado de los diversos artefactos en las distintas etapas del proceso de desarrollo.");
        LGAC result = lgacDAO.getLgacByName(name);
        assertTrue(expected.equals(result));
       
    }
    
    @Test
    public void testGetLgacByNameFailed() throws BusinessException {
        System.out.println("getLgacByName");
        String name = "Tecnología Computacional en la Educación y la Sociedad";
        LGACDAO lgacDAO = new LGACDAO();
        LGAC expected=new LGAC("Tecnología Computacional en la Educación y la Sociedad", "Tendiente  a  lograr  un  enfoque  transdisciplinar  se  analizan  y  evalúan  fenómenos  complejos  que  mediante  una  variedad  de  técnicas  educativas,  sociales,  emotivas,  cognitivas,  y  computacionales  permitan  plantear  soluciones,  prototipos,  metodologías y servicios para el avance de la ciencia y la sociedad.");
        LGAC result = lgacDAO.getLgacByName(name);
        assertFalse(expected.equals(result));
       
    }
    @Test
    public void testUpdate() throws BusinessException {
      System.out.println("updatedSucessful");
      LGAC lgac;
      lgac = new LGAC("Aplicaciones de las técnicas estadísticas",  "Se orienta al estudio de los diversos métodos para el muestreo");
      LGACDAO lgacDAO = new LGACDAO(); 
      assertTrue(lgacDAO.updatedSucessful("Aplicaciones de las técnicas estadísticas", lgac));
    }
    
    @Test
    public void testGetLGACs()throws BusinessException{
        ArrayList<LGAC> arrayLgac;
        LGACDAO lgacDAO=new LGACDAO();
        arrayLgac=lgacDAO.getLGACs();
        String nameExpected="Evaluación del modelo de calidad de seguridad para arquitecturas de software ";
        String result=arrayLgac.get(0).getName();
        assertEquals(nameExpected, result);
    }
}
