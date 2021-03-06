
package businessLogic;

import domain.LGAC;
import domain.Member;
import domain.PreliminarProject;
import domain.Student;
import java.util.ArrayList;
import log.BusinessException;
import log.Log;
import org.junit.Test;
import static org.junit.Assert.*;

public class PreliminarProjectDAOTest {
    
    public PreliminarProjectDAOTest() {
    }

    @Test
    public void testSave() throws BusinessException {
        System.out.println("savedSucessful");
        PreliminarProject preliminarProject = new PreliminarProject("Accesibilidad en el ciclo de vida de desarrollo de software", 
        "El trabajo consiste en realizar una revisión sistemática de la literatura que permita identificar las prácticas y/o actividades en las distintas etapas del ciclo de vida de desarrollo de software orientadas a favorecer el desarrollo de software accesible", 
        "21/01/2021", "21/07/2021", "JDOEIJ804");
        PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
        boolean result = preliminarProjectDAO .savedSucessful(preliminarProject);
        assertTrue(result);
    }

    @Test
    public void testGetId() throws Exception {
        System.out.println("getId");
        PreliminarProject preliminarProject = new PreliminarProject ("Revisión de la Literatura acerca de Varamiento de Mamíferos Marinos",
        "La Secretaría de Medio Ambiente y Recursos Naturales, a través de la Subsecretaría de Fomento y Normatividad Ambiental en coordinación con las áreas del sector ambiental y con la Secretaría de Marina (SEMAR)",
        "13/01/2021", "13/07/2021","JDOEIJ804");
        PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
        int expectedResult = 1;
        int result = preliminarProjectDAO.getId(preliminarProject);
        System.out.println(result);
        assertEquals(expectedResult, result);
    }
    
    @Test
      public void testGetIdFailed() throws BusinessException {
        System.out.println("getId");
        boolean value=true;
        PreliminarProject preliminarProject = new PreliminarProject ("Revisión Sistemática de la Literatura de Software para la Gestión automatizada de Restaurantes",
        "La vigilancia tecnológica es importante en el desarrollo de un producto tecnológico ya que\n" +
        "permite identificar amenazas y oportunidades en el desarrollo de una nueva solución. La\n" +
        "vigilancia tecnológica es la identificación y análisis de las tendencias de solución a un\n" +
        "problema; consiste en la síntesis del estado del arte con base en artículos y patentes",
        "13/01/2021", "13/07/2021", "JDOEIJ804");
        PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
        int expectedResult = 8;
        try{    
            int result = preliminarProjectDAO.getId(preliminarProject);
        }catch(BusinessException ex){   
            value=false;
        }
        assertFalse(value);
    }
      
    @Test
    public void testUpdate() throws BusinessException{
        System.out.println("updatedSucessful");
        PreliminarProjectDAO preliminarProjectDAO=new PreliminarProjectDAO();
        PreliminarProject preliminarProject= new PreliminarProject("Métricas de Cohesión y Acoplamiento", "La facilidad de evolución permite al software adaptarse a distintas necesidades conforme pasa el tiempo y suceden cambios tanto en el mercado como en la organización",
        "13/09/2022" , "17/05/2021","JDOEIJ804");
        int id=2;
        assertTrue(preliminarProjectDAO.updatedSucessful(id, preliminarProject));
    }
    
    @Test
    public void testGetPreliminarProjects() throws BusinessException {  
        System.out.println("getPreliminarProjects");
        PreliminarProjectDAO preliminarProjectDAO=new PreliminarProjectDAO();
        ArrayList<PreliminarProject> preliminarProjects;
        preliminarProjects=preliminarProjectDAO.getPreliminarProjects("JDOEIJ804");
        int idExpected=1;
        int result= preliminarProjects.get(0).getKey();
       assertEquals(idExpected, result);
    }
    
    @Test
    public void testGetPreliminarProject() throws BusinessException {
        PreliminarProject preliminarProject = new PreliminarProject ("Revisión de la Literatura acerca de Varamiento de Mamíferos Marinos",
        "Revisión de la Literatura acerca de Varamiento de Mamíferos Marinos",
        "13/01/2021", "13/07/2021","JDOEIJ804");
        preliminarProject.setKey(1);
        PreliminarProjectDAO preliminarProjectDAO=new PreliminarProjectDAO();
        assertTrue(preliminarProject.equals(preliminarProjectDAO.getById(1)));
    }
    
    @Test 
    public void testGetColaborators () throws BusinessException {
        ArrayList<Member> colaboratorsExpected=new ArrayList<Member>();
        MemberDAO memberDAO = new MemberDAO();
         Member member = memberDAO.getMemberByLicense("8325134");
         member.setRole("Codirector");
        colaboratorsExpected.add(member);
        PreliminarProjectDAO preliminarProjectDAO= new PreliminarProjectDAO();
        ArrayList<Member> colaboratorsResult=preliminarProjectDAO.getColaborators(1);
        try{
            assertEquals(colaboratorsExpected, colaboratorsResult);
        }catch(NullPointerException ex){    
            Log.logException(ex);
        }
    }
    
    @Test
    public void testAddColaborator()throws BusinessException{   
        PreliminarProject preliminarProject = new PreliminarProject ("Revisión de articulos sobre microservicios ",
        "Revisión de articulos relacionados al apartado de microservicios",
        "13/01/2021", "13/07/2021","JDOEIJ804");
        preliminarProject.setKey(3);
         Member member= new Member("8325134","Juan Carlos Perez Arriaga","Director");
        Member memberAdd= new Member("7938268","Maria Karen Cortes Verdin", "Codirector");
        preliminarProject.addMember(member);
        preliminarProject.addMember(memberAdd);
  
        PreliminarProjectDAO preliminarProjectDAO= new PreliminarProjectDAO();
        assertTrue(preliminarProjectDAO.addedSucessfulColaborators(preliminarProject));
    }
    
    @Test
    public void testAddStudents()throws BusinessException{   
        PreliminarProject preliminarProject = new PreliminarProject ("Revisión de articulos sobre microservicios ",
        "Revisión de articulos relacionados al apartado de microservicios",
        "13/01/2021", "13/07/2021","JDOEIJ804");
        preliminarProject.setKey(3);
        Student student = new Student("S19014013", "Mariana Yazmin Vargas Segura");
        preliminarProject.addStudent(student);
        PreliminarProjectDAO preliminarProjectDAO= new PreliminarProjectDAO();
        assertTrue(preliminarProjectDAO.addedSucessfulStudents(preliminarProject));
    }
    
    
    
    @Test
    public void testDeleteColaborators() throws BusinessException { 
        PreliminarProject preliminarProject= new PreliminarProject("Evaluación del modelo de calidad de seguridad para arquitecturas de software", "Una arquitectura de software define no sólo la estructura o estructuras de un sistema de software, sino las características de calidad del propio sistema. Una característica o atributo de calidad altamente crítico en nuestros días es la seguridad. Esta característica, por supuesto que también es importante considerar en el desarrollo de la plataforma de comunicación y educación",
        "13/11/2019","13/07/2020","JDOEIJ804");
        preliminarProject.setKey(4);
        MemberDAO memberDAO =new MemberDAO();
        preliminarProject.addMember(memberDAO.getMemberByLicense("8325134"));
        PreliminarProjectDAO preliminarProjectDAO= new PreliminarProjectDAO();
        assertTrue(preliminarProjectDAO.deletedSucessfulColaborators(preliminarProject));
    }
    
    @Test 
    public void testGetStudents () throws BusinessException{
        ArrayList<Student> studentsExpected= new ArrayList <Student>();
         studentsExpected.add(new Student("S19014023", "Karina Valdes Iglesias" ));
        PreliminarProjectDAO preliminarProjectDAO= new PreliminarProjectDAO();
        ArrayList<Student> studentsResult= preliminarProjectDAO.getStudents(1);

        assertEquals(studentsExpected,studentsResult);
    }
    
    @Test 
    public void testDeleteStudents () throws BusinessException{
        PreliminarProject preliminarProject= new PreliminarProject("Evaluación del modelo de calidad de seguridad para arquitecturas de software", "Una arquitectura de software define no sólo la estructura o estructuras de un sistema de software, sino las características de calidad del propio sistema. Una característica o atributo de calidad altamente crítico en nuestros días es la seguridad. Esta característica, por supuesto que también es importante considerar en el desarrollo de la plataforma de comunicación y educación",
        "13/11/2019","13/07/2020","JDOEIJ804");
        preliminarProject.setKey(4);
        Student student= new Student("S19014013", "Mariana Yazmin Vargas Segura");
        preliminarProject.addStudent(student);
        PreliminarProjectDAO preliminarProjectDAO= new PreliminarProjectDAO();
        assertTrue(preliminarProjectDAO.deletedSucessfulStudents(preliminarProject));
    }

    
    
}
