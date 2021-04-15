
package businessLogic;

import domain.PreliminarProject;
import log.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;

public class PreliminarProjectDAOTest {
    
    public PreliminarProjectDAOTest() {
    }

    @Test
    public void testSave() throws BusinessException {
        System.out.println("save");
        PreliminarProject preliminarProject = new PreliminarProject("Accesibilidad en el ciclo de vida de desarrollo de software", 
        "El trabajo consiste en realizar una revisión sistemática de la literatura que permita identificar las prácticas y/o actividades en las distintas etapas del ciclo de vida de desarrollo de software orientadas a favorecer el desarrollo de software accesible", 
        "21/01/2021", "21/07/2021 ");
        PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
        boolean result = preliminarProjectDAO .save(preliminarProject);
        assertTrue(result);
    }

    @Test
    public void testGetId() throws Exception {
        System.out.println("getId");
        PreliminarProject preliminarProject = new PreliminarProject ("Revisión de la Literatura acerca de Varamiento de Mamíferos Marinos",
        "La Secretaría de Medio Ambiente y Recursos Naturales, a través de la Subsecretaría de Fomento y Normatividad Ambiental en coordinación con las áreas del sector ambiental y con la Secretaría de Marina (SEMAR)",
        "13/01/2021", "13/07/2021");
        PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
        int expectedResult = 6;
        int result = preliminarProjectDAO.getId(preliminarProject);
        System.out.println(result);
        assertEquals(expectedResult, result);
    }
}
