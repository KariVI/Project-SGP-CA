
package businessLogic;

import domain.LGCA;
import java.util.ArrayList;
import log.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;

public class LGACDAOTest {
    
    public LGACDAOTest() {
    }

    @Test
    public void testSave() throws BusinessException {
        System.out.println("save");
        LGCA lgac;
        lgac = new LGCA("Tecnologías de software", "Se orienta al estudio de diversas propiedades, enfoques, métodos de modelado y herramientas que conforman cada una de las diversas tecnologías aplicables al desarrollo del software con vistas a su adaptación, mejora y sustitución en el medio nacional");
        LGCADAO lgacDAO = new LGCADAO();
        boolean result = lgacDAO.save(lgac);
        assertTrue(result);
       
    }

    @Test
    public void testGetLgacByName() throws BusinessException {
        System.out.println("getLgacByName");
        String name = "Gestión, modelado y desarrollo de software";
        LGCADAO lgacDAO = new LGCADAO();
        LGCA expected=new LGCA("Gestión, modelado y desarrollo de software",  "Se orienta al estudio de los diversos métodos y enfoques para la gestión, modelado y desarrollo de software, de manera que se obtenga software de calidad. Gestión de las diversas etapas del proceso de desarrollo, incluyendo hasta la medición del proceso y artefactos. Modelado de los diversos artefactos en las distintas etapas del proceso de desarrollo.");
        LGCA result = lgacDAO.getLgacByName(name);
        assertTrue(expected.equals(result));
       
    }
    
    @Test
    public void testGetLgacByNameFailed() throws BusinessException {
        System.out.println("getLgacByName");
        String name = "Tecnología Computacional en la Educación y la Sociedad";
        LGCADAO lgacDAO = new LGCADAO();
        LGCA expected=new LGCA("Tecnología Computacional en la Educación y la Sociedad", "Tendiente  a  lograr  un  enfoque  transdisciplinar  se  analizan  y  evalúan  fenómenos  complejos  que  mediante  una  variedad  de  técnicas  educativas,  sociales,  emotivas,  cognitivas,  y  computacionales  permitan  plantear  soluciones,  prototipos,  metodologías y servicios para el avance de la ciencia y la sociedad.");
        LGCA result = lgacDAO.getLgacByName(name);
        assertFalse(expected.equals(result));
       
    }
    @Test
    public void testUpdate() throws BusinessException {
      System.out.println("update");
      LGCA lgac;
      lgac = new LGCA("Aplicaciones de las técnicas estadísticas",  "Se orienta al estudio de los diversos métodos para el muestreo");
      LGCADAO lgacDAO = new LGCADAO(); 
      assertTrue(lgacDAO.update("Aplicaciones de las técnicas estadísticas", lgac));
    }
    
    @Test
    public void testGetLGACs()throws BusinessException{
        ArrayList<LGCA> arrayLgac;
        LGCADAO lgacDAO=new LGCADAO();
        arrayLgac=lgacDAO.getLGACs();
        String nameExpected="Evaluación del modelo de calidad de seguridad para arquitecturas de software ";
        String result=arrayLgac.get(0).getName();
        assertEquals(nameExpected, result);
    }
}