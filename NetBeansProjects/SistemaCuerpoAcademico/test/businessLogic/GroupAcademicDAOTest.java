
package businessLogic;

import domain.GroupAcademic;
import domain.LGAC;
import java.util.ArrayList;

import log.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;

public class GroupAcademicDAOTest {
    
    public GroupAcademicDAOTest() {
    }
    
     @Test
    public void testGetGroupAcademicById() throws BusinessException {
        GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
        GroupAcademic groupAcademicExpected;
        groupAcademicExpected= new GroupAcademic("JDOEIJ804", "Ingenieria y Tecnologias de Software", "Desarrollar métodos, técnicas y herramientas para el desarrollo de software con un enfoque sistemático, disciplinado y cuantificable y apegado a estándares de calidad"
        , "En consolidacion", " Generar conocimiento y formar recursos humanos en Ingeniería de Software que contribuyan al desarrollo de software de calidad; a través de proyectos de investigación cuyos resultados se trasladen a  la docencia y la sociedad; y se difundan en foros especializados y de divulgación, fortaleciendo la vinculación academia-industria",
        "El Cuerpo Académico se encuentra consolidado y es líder en Ingeniería de Software y áreas relacionadas; todos los miembros trabajan colaborativamente en actividades de docencia, vinculación, generación y aplicación del conocimiento en las que participan activamente estudiantes de licenciatura y posgrado");
        GroupAcademic groupAcademicActual= groupAcademicDAO.getGroupAcademicById("JDOEIJ804");
        assertTrue( groupAcademicExpected.equals(groupAcademicActual));
    }
    
    @Test
    public void testGetGroupAcademicByIdNotFound() throws BusinessException {
        GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
        GroupAcademic groupAcademicExpected;
        groupAcademicExpected= new GroupAcademic("JDOEIJ804", "Tecnología Computacional y Educativa", "Desarrollar métodos, técnicas y herramientas para el desarrollo de software con un enfoque sistemático, disciplinado y cuantificable y apegado a estándares de calidad"
        , "En consolidacion", " Generar conocimiento y formar recursos humanos en Ingeniería de Software que contribuyan al desarrollo de software de calidad; a través de proyectos de investigación cuyos resultados se trasladen a  la docencia y la sociedad; y se difundan en foros especializados y de divulgación, fortaleciendo la vinculación academia-industria",
        "El Cuerpo Académico se encuentra consolidado y es líder en Ingeniería de Software y áreas relacionadas; todos los miembros trabajan colaborativamente en actividades de docencia, vinculación, generación y aplicación del conocimiento en las que participan activamente estudiantes de licenciatura y posgrado");
        GroupAcademic groupAcademicActual= groupAcademicDAO.getGroupAcademicById("JDOEIJ804");
        assertFalse( groupAcademicExpected.equals(groupAcademicActual));
    }
    

    @Test
    public void testSave() throws BusinessException {
        System.out.println("save");
        GroupAcademic groupAcademic;
        groupAcademic= new GroupAcademic("JDOEIJ804", "Ingenieria y Tecnologias de Software", "Desarrollar métodos, técnicas y herramientas para el desarrollo de software con un enfoque sistemático, disciplinado y cuantificable y apegado a estándares de calidad"
        , "En consolidacion", " Generar conocimiento y formar recursos humanos en Ingeniería de Software que contribuyan al desarrollo de software de calidad; a través de proyectos de investigación cuyos resultados se trasladen a  la docencia y la sociedad; y se difundan en foros especializados y de divulgación, fortaleciendo la vinculación academia-industria",
        "El Cuerpo Académico se encuentra consolidado y es líder en Ingeniería de Software y áreas relacionadas; todos los miembros trabajan colaborativamente en actividades de docencia, vinculación, generación y aplicación del conocimiento en las que participan activamente estudiantes de licenciatura y posgrado");
        GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
        assertTrue( groupAcademicDAO.savedSucessful(groupAcademic));
    }
    
    @Test
    public void testAddLGAC() throws BusinessException{ 
        System.out.println("addLGAC");
        GroupAcademic groupAcademic;
        groupAcademic= new GroupAcademic("JDOEIJ804", "Ingenieria y Tecnologias de Software", "Desarrollar métodos, técnicas y herramientas para el desarrollo de software con un enfoque sistemático, disciplinado y cuantificable y apegado a estándares de calidad"
        , "En consolidacion", " Generar conocimiento y formar recursos humanos en Ingeniería de Software que contribuyan al desarrollo de software de calidad; a través de proyectos de investigación cuyos resultados se trasladen a  la docencia y la sociedad; y se difundan en foros especializados y de divulgación, fortaleciendo la vinculación academia-industria",
        "El Cuerpo Académico se encuentra consolidado y es líder en Ingeniería de Software y áreas relacionadas; todos los miembros trabajan colaborativamente en actividades de docencia, vinculación, generación y aplicación del conocimiento en las que participan activamente estudiantes de licenciatura y posgrado");
        LGAC lgac=new LGAC("Evaluación del modelo de calidad de seguridad para arquitecturas de software ", "Se orienta al estudio de los diversos métodos y enfoques para la gestión, modelado y desarrollo de software, de manera que se obtenga software de calidad. Gestión de las diversas etapas del proceso de desarrollo, incluyendo hasta la medición del proceso y artefactos. Modelado de los diversos artefactos en las distintas etapas del proceso de desarrollo.");
        GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
        assertTrue(groupAcademicDAO.addedLGACSucessful(groupAcademic, lgac));
    
    }
    
    @Test
    public void testUpdate() throws BusinessException{    
        GroupAcademic groupAcademic;
        groupAcademic= new GroupAcademic( "UVCA1843","Tecnología Computacional y Educativa",
        "Desarrollar aplicaciones en torno a la educación empleando enfoques novedosos ( lenguajes de programación, bases de datos, tutores inteligentes, laboratorios virtuales) para obtener nuevos productos.", "Consolidado", 
        "El CA adscrito a la FEI de la UV tiene como misión desarrollar las áreas de sistemas operativos, lenguajes de programación, tecnología educativa, bases de datos, multimedia que permiten fortalecer la formación de los estudiantes de los programas educativo de la FEI, particularmente de la Licenciatura de Informática","Se visualiza un CA integrado y consistente, con líneas definidas de generación y aplicación del conocimiento, que produce soluciones a problemas y conocimientos básicos de las áreas de redes, sistemas operativos, bases de datos, lenguajes de programación");
        GroupAcademicDAO groupAcademicDAO= new GroupAcademicDAO();
        assertTrue(groupAcademicDAO.updatedSucessful("UVCA1843", groupAcademic));

    }
    
    @Test 
    public void testGetlgacs() throws BusinessException{    
        ArrayList<LGAC> lgacsExpected = new ArrayList<LGAC>();
        lgacsExpected.add(new LGAC ("Evaluación del modelo de calidad de seguridad para arquitecturas de software ", "Se orienta al estudio de los diversos métodos y enfoques para la gestión, modelado y desarrollo de software, de manera que se obtenga software de calidad. Gestión de las diversas etapas del proceso de desarrollo, incluyendo hasta la medición del proceso y artefactos. Modelado de los diversos artefactos en las distintas etapas del proceso de desarrollo."));
        GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
        ArrayList<LGAC> lgacsResult =  groupAcademicDAO.getLGACs("JDOEIJ804");
         assertEquals(lgacsExpected, lgacsResult);
    }
    
    @Test 
    public void testDeletedLGCASucessful () throws BusinessException{   
        LGAC lgac= new LGAC ("Evaluación del modelo de calidad de seguridad para arquitecturas de software ", "Se orienta al estudio de los diversos métodos y enfoques para la gestión, modelado y desarrollo de software, de manera que se obtenga software de calidad. Gestión de las diversas etapas del proceso de desarrollo, incluyendo hasta la medición del proceso y artefactos. Modelado de los diversos artefactos en las distintas etapas del proceso de desarrollo.");
        GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
        assertTrue(groupAcademicDAO.deletedLGACSuccesful("JDOEIJ804", lgac));
    }

}
