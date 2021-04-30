
package businessLogic;

import domain.Member;
import domain.PreliminarProject;
import domain.Student;
import java.util.ArrayList;
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
    
    @Test
      public void testGetIdFailed() throws BusinessException {
        System.out.println("getId");
        PreliminarProject preliminarProject = new PreliminarProject ("Revisión Sistemática de la Literatura de Software para la Gestión automatizada de Restaurantes",
        "La vigilancia tecnológica es importante en el desarrollo de un producto tecnológico ya que\n" +
        "permite identificar amenazas y oportunidades en el desarrollo de una nueva solución. La\n" +
        "vigilancia tecnológica es la identificación y análisis de las tendencias de solución a un\n" +
        "problema; consiste en la síntesis del estado del arte con base en artículos y patentes",
        "13/01/2021", "13/07/2021");
        PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
        int expectedResult = 8;
        int result = preliminarProjectDAO.getId(preliminarProject);
        System.out.println(result);
        assertNotEquals(expectedResult, result);
    }
      
    @Test
    public void testUpdate() throws BusinessException{
        System.out.println("update");
        PreliminarProjectDAO preliminarProjectDAO=new PreliminarProjectDAO();
        PreliminarProject preliminarProject= new PreliminarProject("Métricas de Cohesión y Acoplamiento", "La facilidad de evolución permite al software adaptarse a distintas necesidades conforme pasa el tiempo y suceden cambios tanto en el mercado como en la organización",
        "13/09/2020" , "17/05/2021");
        int id=8;
        assertTrue(preliminarProjectDAO.update(id, preliminarProject));
    }
    
    @Test
    public void testGetPreliminarProjects() throws BusinessException {  
        System.out.println("getPreliminarProjects");
        PreliminarProjectDAO preliminarProjectDAO=new PreliminarProjectDAO();
        ArrayList<PreliminarProject> preliminarProjects;
        preliminarProjects=preliminarProjectDAO.getPreliminarProjects();
        int idExpected=1;
        int result= preliminarProjects.get(0).getKey();
       assertEquals(idExpected, result);
    }
    
    @Test
    public void testGetPreliminarProject() throws BusinessException {
        PreliminarProject preliminarProject = new PreliminarProject ("Revisión de la Literatura acerca de Varamiento de Mamíferos Marinos",
        "Revisión de la Literatura acerca de Varamiento de Mamíferos Marinos",
        "13/01/2021", "13/07/2021");
        preliminarProject.setKey(6);
        PreliminarProjectDAO preliminarProjectDAO=new PreliminarProjectDAO();
        assertTrue(preliminarProject.equals(preliminarProjectDAO.getById(6)));
    }
    
    @Test
    public void testAddColaborator()throws BusinessException{   
        PreliminarProject preliminarProject = new PreliminarProject ("Revisión de la Literatura acerca de Varamiento de Mamíferos Marinos",
        "Revisión de la Literatura acerca de Varamiento de Mamíferos Marinos",
        "13/01/2021", "13/07/2021");
        preliminarProject.setKey(6);
         Member member= new Member("8325134","Juan Carlos Perez Arriaga","Director");
        Member memberAdd= new Member("7938268","Maria Karen Cortes Verdin", "Colaborador");
        preliminarProject.addMember(member);
        preliminarProject.addMember(memberAdd);
  
        PreliminarProjectDAO preliminarProjectDAO= new PreliminarProjectDAO();
        assertTrue(preliminarProjectDAO.addColaborators(preliminarProject));
    }
    
    @Test
    public void testAddStudents()throws BusinessException{   
        PreliminarProject preliminarProject = new PreliminarProject ("Revisión de la Literatura acerca de Varamiento de Mamíferos Marinos",
        "Revisión de la Literatura acerca de Varamiento de Mamíferos Marinos",
        "13/01/2021", "13/07/2021");
        preliminarProject.setKey(6);
        Student student = new Student("S19014013", "Mariana Yazmin Vargas Segura ");
        preliminarProject.addStudent(student);
        PreliminarProjectDAO preliminarProjectDAO= new PreliminarProjectDAO();
        assertTrue(preliminarProjectDAO.addStudents(preliminarProject));
    }
    
    @Test 
    public void testGetColaborators () throws BusinessException {
        ArrayList<Member> colaborators;
        PreliminarProjectDAO preliminarProjectDAO= new PreliminarProjectDAO();
        colaborators=preliminarProjectDAO.getColaborators(7);
        String professionalLicenseExpected="8325134";
        String professionalLicenseResult=colaborators.get(0).getProfessionalLicense();
        assertEquals(professionalLicenseExpected, professionalLicenseResult);
    }
    
    @Test
    public void testDeleteColaborators() throws BusinessException { 
        PreliminarProject preliminarProject= new PreliminarProject("Evaluación del modelo de calidad de seguridad para arquitecturas de software", "Una arquitectura de software define no sólo la estructura o estructuras de un sistema de software, sino las características de calidad del propio sistema. Una característica o atributo de calidad altamente crítico en nuestros días es la seguridad. Esta característica, por supuesto que también es importante considerar en el desarrollo de la plataforma de comunicación y educación",
        "13/11/2019","13/07/2020");
        preliminarProject.setKey(7);
        MemberDAO memberDAO =new MemberDAO();
        preliminarProject.addMember(memberDAO.getMemberByLicense("8325134"));
        PreliminarProjectDAO preliminarProjectDAO= new PreliminarProjectDAO();
        assertTrue(preliminarProjectDAO.deleteColaborators(preliminarProject));
    }
    
    @Test 
    public void testGetStudents () throws BusinessException{
        String enrollmentExpected="S19014013";
        PreliminarProjectDAO preliminarProjectDAO= new PreliminarProjectDAO();
        ArrayList<Student> students= preliminarProjectDAO.getStudents(7);
        String enrollmentResult= students.get(0).getEnrollment();
        assertEquals(enrollmentExpected,enrollmentResult);
    }
    
    @Test 
    public void testDeleteStudents () throws BusinessException{
        PreliminarProject preliminarProject= new PreliminarProject("Evaluación del modelo de calidad de seguridad para arquitecturas de software", "Una arquitectura de software define no sólo la estructura o estructuras de un sistema de software, sino las características de calidad del propio sistema. Una característica o atributo de calidad altamente crítico en nuestros días es la seguridad. Esta característica, por supuesto que también es importante considerar en el desarrollo de la plataforma de comunicación y educación",
        "13/11/2019","13/07/2020");
        preliminarProject.setKey(7);
        Student student= new Student("S19014013", "Mariana Yazmin Vargas Segura");
        preliminarProject.addStudent(student);
        PreliminarProjectDAO preliminarProjectDAO= new PreliminarProjectDAO();
        assertTrue(preliminarProjectDAO.deleteStudents(preliminarProject));
    }
}
