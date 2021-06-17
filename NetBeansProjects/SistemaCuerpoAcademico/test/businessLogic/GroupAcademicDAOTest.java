
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
    public void testSave() throws BusinessException {
        System.out.println("save");
        GroupAcademic groupAcademic;
        groupAcademic= new GroupAcademic("CA424", "Didáctica y Aplicaciones de la Matemática","Diseñar e implementar la metodología estadística en procesos de investigación y estudios técnicos, para resolver problemas concretos de otras áreas del conocimiento."
        , "En consolidación", "Se define primeramente en el marco de la orientación del plan de estudios y los objetivos de la carrera.",
        "Se busca impulsar el desarrollo académico de la Licenciatura en Estadística y de la Especialización en Métodos Estadísticos");
        GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
        assertTrue( groupAcademicDAO.savedSucessful(groupAcademic));
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
        groupAcademicExpected= new GroupAcademic("EIJ804", "Tecnología Computacional y Educativa", "Desarrollar métodos, técnicas y herramientas para el desarrollo de software con un enfoque sistemático, disciplinado y cuantificable y apegado a estándares de calidad"
        , "En consolidacion", " Generar conocimiento y formar recursos humanos en Ingeniería de Software que contribuyan al desarrollo de software de calidad; a través de proyectos de investigación cuyos resultados se trasladen a  la docencia y la sociedad; y se difundan en foros especializados y de divulgación, fortaleciendo la vinculación academia-industria",
        "El Cuerpo Académico se encuentra consolidado y es líder en Ingeniería de Software y áreas relacionadas; todos los miembros trabajan colaborativamente en actividades de docencia, vinculación, generación y aplicación del conocimiento en las que participan activamente estudiantes de licenciatura y posgrado");
        GroupAcademic groupAcademicActual= groupAcademicDAO.getGroupAcademicById("JDOEIJ804");
        assertFalse( groupAcademicExpected.equals(groupAcademicActual));
    }
    
    @Test
    public void testAddLGAC() throws BusinessException{ 
        System.out.println("addLGAC");
        GroupAcademic groupAcademic;
        groupAcademic= new GroupAcademic("JDOEIJ804", "Tecnología Computacional y Educativa", "Desarrollar métodos, técnicas y herramientas para el desarrollo de software con un enfoque sistemático, disciplinado y cuantificable y apegado a estándares de calidad"
        , "En consolidacion", " Generar conocimiento y formar recursos humanos en Ingeniería de Software que contribuyan al desarrollo de software de calidad; a través de proyectos de investigación cuyos resultados se trasladen a  la docencia y la sociedad; y se difundan en foros especializados y de divulgación, fortaleciendo la vinculación academia-industria",
        "El Cuerpo Académico se encuentra consolidado y es líder en Ingeniería de Software y áreas relacionadas; todos los miembros trabajan colaborativamente en actividades de docencia, vinculación, generación y aplicación del conocimiento en las que participan activamente estudiantes de licenciatura y posgrado");
        LGAC lgac=new LGAC("Gestión, modelado y desarrollo de software", "Se orienta al estudio de los diversos métodos y enfoques para la gestión, modelado y desarrollo de software, de manera que se obtenga software de calidad. Gestión de las diversas etapas del proceso de desarrollo, incluyendo hasta la medición del proceso y artefactos. Modelado de los diversos artefactos en las distintas etapas del proceso de desarrollo.");
        GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
        assertTrue(groupAcademicDAO.addedLGACSucessful(groupAcademic, lgac));
    
    }
    
    @Test
    public void testUpdate() throws BusinessException{    
        GroupAcademic groupAcademic;
        groupAcademic= new GroupAcademic( "UVERCA12","Metodología Cuantitativa y Aplicaciones Diversas de la Estadística",
        "Diseñar e implementar la metodología estadística en procesos de investigación y estudios técnicos, para resolver problemas concretos de otras áreas del conocimiento.", "En formación", 
        "El CA adscrito a la FEI de la UV tiene como misión desarrollar las áreas métodos estadísticos","Se visualiza un CA integrado y consistente, con líneas definidas de generación y aplicación del conocimiento, que produce soluciones a problemas y conocimientos básicos de las áreas de estadistica");
        GroupAcademicDAO groupAcademicDAO= new GroupAcademicDAO();
        assertTrue(groupAcademicDAO.updatedSucessful("UVERCA12", groupAcademic));

    }
    
    @Test 
    public void testGetlgacs() throws BusinessException{    
        ArrayList<LGAC> lgacsExpected = new ArrayList<LGAC>();
        lgacsExpected.add(new LGAC("Metodología de la investigación y la estadística", " Se orienta al estudio de los procesos de aplicación de la metodología estadística, considerando los diferentes enfoques, dependiendo de la disciplina de donde proviene el problema"));
        lgacsExpected.add(new LGAC ("Encuestas y estudios de opinión", "  Estudia el diseño de muestreos y sondeos, así como el proceso del levantamiento de encuestas, el análisis estadístico de los datos y la elaboración de reportes."));
        GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
        ArrayList<LGAC> lgacsResult =  groupAcademicDAO.getLGACs("UVERCA12");


         assertEquals(lgacsExpected, lgacsResult);
    }
    
    @Test 
    public void testDeletedLGCASucessful () throws BusinessException{   
        LGAC lgac= new LGAC ("Aplicaciones de las técnicas estadísticas","Aplicación de técnicas");
        GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
        assertTrue(groupAcademicDAO.deletedLGACSuccesful("UVCA107", lgac));
    }

}
