
package businessLogic;

import domain.GroupAcademic;
import org.junit.Test;
import static org.junit.Assert.*;

public class GroupAcademicDAOTest {
    
    public GroupAcademicDAOTest() {
    }
    
     @Test
    public void testGetGroupAcademicById() {
        GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
        GroupAcademic groupAcademicExpected;
        groupAcademicExpected= new GroupAcademic("JDOEIJ804", "Ingenieria y Tecnologias de Software", "Desarrollar métodos, técnicas y herramientas para el desarrollo de software con un enfoque sistemático, disciplinado y cuantificable y apegado a estándares de calidad"
        , "En consolidacion", " Generar conocimiento y formar recursos humanos en Ingeniería de Software que contribuyan al desarrollo de software de calidad; a través de proyectos de investigación cuyos resultados se trasladen a  la docencia y la sociedad; y se difundan en foros especializados y de divulgación, fortaleciendo la vinculación academia-industria",
        "El Cuerpo Académico se encuentra consolidado y es líder en Ingeniería de Software y áreas relacionadas; todos los miembros trabajan colaborativamente en actividades de docencia, vinculación, generación y aplicación del conocimiento en las que participan activamente estudiantes de licenciatura y posgrado");
        GroupAcademic groupAcademicActual= groupAcademicDAO.getGroupAcademicById("JDOEIJ804");
        assertTrue( groupAcademicExpected.equals(groupAcademicActual));
    }
    
    @Test
    public void testGetGroupAcademicByIdNotFound() {
        GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
        GroupAcademic groupAcademicExpected;
        groupAcademicExpected= new GroupAcademic("JDOEIJ804", "Tecnología Computacional y Educativa", "Desarrollar métodos, técnicas y herramientas para el desarrollo de software con un enfoque sistemático, disciplinado y cuantificable y apegado a estándares de calidad"
        , "En consolidacion", " Generar conocimiento y formar recursos humanos en Ingeniería de Software que contribuyan al desarrollo de software de calidad; a través de proyectos de investigación cuyos resultados se trasladen a  la docencia y la sociedad; y se difundan en foros especializados y de divulgación, fortaleciendo la vinculación academia-industria",
        "El Cuerpo Académico se encuentra consolidado y es líder en Ingeniería de Software y áreas relacionadas; todos los miembros trabajan colaborativamente en actividades de docencia, vinculación, generación y aplicación del conocimiento en las que participan activamente estudiantes de licenciatura y posgrado");
        GroupAcademic groupAcademicActual= groupAcademicDAO.getGroupAcademicById("JDOEIJ804");
        assertFalse( groupAcademicExpected.equals(groupAcademicActual));
    }
    

    @Test
    public void testSave() {
        System.out.println("save");
        GroupAcademic groupAcademic;
        groupAcademic= new GroupAcademic("JDOEIJ804", "Ingenieria y Tecnologias de Software", "Desarrollar métodos, técnicas y herramientas para el desarrollo de software con un enfoque sistemático, disciplinado y cuantificable y apegado a estándares de calidad"
        , "En consolidacion", " Generar conocimiento y formar recursos humanos en Ingeniería de Software que contribuyan al desarrollo de software de calidad; a través de proyectos de investigación cuyos resultados se trasladen a  la docencia y la sociedad; y se difundan en foros especializados y de divulgación, fortaleciendo la vinculación academia-industria",
        "El Cuerpo Académico se encuentra consolidado y es líder en Ingeniería de Software y áreas relacionadas; todos los miembros trabajan colaborativamente en actividades de docencia, vinculación, generación y aplicación del conocimiento en las que participan activamente estudiantes de licenciatura y posgrado");
        GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
        groupAcademicDAO.save(groupAcademic);
        GroupAcademic groupAcademicExpected= groupAcademicDAO.getGroupAcademicById("JDOEIJ804");
        assertTrue( groupAcademicExpected.equals(groupAcademic));
    }

 

}
