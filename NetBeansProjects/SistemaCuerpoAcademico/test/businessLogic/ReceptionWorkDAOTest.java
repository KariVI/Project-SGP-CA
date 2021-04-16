
package businessLogic;

import domain.PreliminarProject;
import domain.ReceptionWork;
import log.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReceptionWorkDAOTest {
    
    public ReceptionWorkDAOTest() {
    }

    @Test
    public void testSave() throws BusinessException {
        System.out.println("save");
        ReceptionWork receptionWork = new ReceptionWork("VaraAppX: Aplicación móvil para registro de datos detallados sobre varamientos de mamíferos marinos",
        "Práctico técnico","Aplicación enfocada al registro de datos sobre varamientos mamiferos",
        "20/12/2019","20/08/2020", "Concluido");
        PreliminarProject preliminarProject = new PreliminarProject ("Revisión de la Literatura acerca de Varamiento de Mamíferos Marinos",
        "La Secretaría de Medio Ambiente y Recursos Naturales, a través de la Subsecretaría de Fomento y Normatividad Ambiental en coordinación con las áreas del sector ambiental y con la Secretaría de Marina (SEMAR)",
        "13/01/2021", "13/07/2021");
        preliminarProject.setKey(6);
        receptionWork.setPreliminarProject(preliminarProject);
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        boolean result = receptionWorkDAO .save(receptionWork);
        assertTrue(result);
        
        
    }

    @Test
    public void testUpdate() throws BusinessException {
        System.out.println("update");
        int id = 2;
        ReceptionWork receptionWork = new ReceptionWork("Análisis comparativo de métodos de evaluación de arquitecturas de software guiado por ESSENCE 1.2",
        "Práctico técnico","Resultados de la investigación y aplicación de ESSENCE 1.2 en distintas arquitecturas de software enfocadas a proyectos guiados",
        "14/06/2020","01/02/2021", "Concluido");
        PreliminarProject preliminarProject = new PreliminarProject ("Evaluación del modelo de calidad de seguridad para arquitecturas de software",
        "Una arquitectura de software define no sólo la estructura o estructuras de un sistema de software, sino las características de calidad del propio sistema. Una característica o atributo de calidad altamente crítico en nuestros días es la seguridad. Esta característica, por supuesto que también es importante considerar en el desarrollo de la plataforma de comunicación y educación", 
        "13/11/2019", "13/07/2020");
        preliminarProject.setKey(7);
        receptionWork.setPreliminarProject(preliminarProject);
        ReceptionWorkDAO instance = new ReceptionWorkDAO();
        boolean result = instance.update(id, receptionWork);
        assertTrue( result);
        
    }
    
}
