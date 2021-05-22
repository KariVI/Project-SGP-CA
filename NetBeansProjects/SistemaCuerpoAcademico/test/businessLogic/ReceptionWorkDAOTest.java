
package businessLogic;

import domain.Member;
import domain.PreliminarProject;
import domain.ReceptionWork;
import domain.Student;
import domain.LGAC;
import java.util.ArrayList;
import log.BusinessException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ReceptionWorkDAOTest {
    
    public ReceptionWorkDAOTest() {
    }

    @Test
    public void testSave() throws BusinessException {
        System.out.println("savedSucessful");
        ReceptionWork receptionWork = new ReceptionWork("VaraAppX: Aplicación móvil para registro de datos detallados sobre varamientos de mamíferos marinos",
        "Práctico técnico","Aplicación enfocada al registro de datos sobre varamientos mamiferos",
        "20/12/2019","20/08/2020", "Concluido");
        PreliminarProject preliminarProject = new PreliminarProject ("Revisión de la Literatura acerca de Varamiento de Mamíferos Marinos",
        "La Secretaría de Medio Ambiente y Recursos Naturales, a través de la Subsecretaría de Fomento y Normatividad Ambiental en coordinación con las áreas del sector ambiental y con la Secretaría de Marina (SEMAR)",
        "13/01/2021", "13/07/2021");
        preliminarProject.setKey(6);
        receptionWork.setPreliminarProject(preliminarProject);
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        boolean result = receptionWorkDAO .savedSucessful(receptionWork);
        assertTrue(result);
        
        
    }

    @Test
    public void testUpdate() throws BusinessException {
        System.out.println("updatedSucessful");
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
        boolean result = instance.updatedSucessful(id, receptionWork);
        assertTrue( result);
    }
    
    @Test
    public void testGetReceptionWorkById() throws BusinessException{
        int id=2;
        ReceptionWork receptionWork = new ReceptionWork("Análisis comparativo de métodos de evaluación de arquitecturas de software guiado por ESSENCE 1.2",
        "Práctico técnico","Resultados de la investigación y aplicación de ESSENCE 1.2 en distintas arquitecturas de software enfocadas a proyectos guiados",
        "14/06/2020","01/02/2021", "Concluido");
        receptionWork.setKey(id);
        ReceptionWorkDAO instance = new ReceptionWorkDAO();  
        assertTrue(receptionWork.equals(instance.getReceptionWorkById(id)));
    
    }
    
    @Test
     public void testGetReceptionWorks() throws BusinessException {  
        System.out.println("getPreliminarProjects");
        ReceptionWorkDAO receptionWorkDAO=new ReceptionWorkDAO();
        ArrayList<ReceptionWork> receptionWorks;
        receptionWorks=receptionWorkDAO.getReceptionWorks();
        int idExpected=1;
        int result= receptionWorks.get(0).getKey();
        assertEquals(idExpected, result);
    }
    @Test
    public void testGetId() throws BusinessException{
        int id=2;
        ReceptionWork receptionWork = new ReceptionWork("Análisis comparativo de métodos de evaluación de arquitecturas de software guiado por ESSENCE 1.2",
        "Práctico técnico","Resultados de la investigación y aplicación de ESSENCE 1.2 en distintas arquitecturas de software enfocadas a proyectos guiados",
        "14/06/2020","01/02/2021", "Concluido");
        receptionWork.setKey(id);
        ReceptionWorkDAO instance = new ReceptionWorkDAO();  
        int  result=instance.getId(receptionWork);
        assertEquals(id,result);
    
    }
     @Test
    public void testGetIdFailed() throws BusinessException{
        int id=50;
        ReceptionWork receptionWork = new ReceptionWork("VaraAppX: Aplicación móvil para registro de datos detallados sobre varamientos de mamíferos marinos",
        "Práctico técnico","Aplicación enfocada al registro de datos sobre varamientos mamiferos",
        "20/12/2019","20/08/2020", "Concluido");
        receptionWork.setKey(id);
        ReceptionWorkDAO instance = new ReceptionWorkDAO();  
        int  result=instance.getId(receptionWork);
        assertNotEquals(id,result);
    
    }
    
    @Test
    public void testAddColaborator()throws BusinessException{     
        ReceptionWork receptionWork = new ReceptionWork ("Propuesta de Aplicación de Aprendizaje Máquina y Cómputo Evolutivo en la Clasificación de Requisitos de Calidad", "Tesis",
        "Propuesta de uso de aprendizaje máquina en la clasificación de requisitos", "14/11/2019", "14/05/2021", "Concluido");
        receptionWork.setKey(3);
        Member member= new Member("8325134","Juan Carlos Perez Arriaga","Director");
        receptionWork.addMember(member);  
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO(); 
        assertTrue(receptionWorkDAO.addedSucessfulColaborators(receptionWork));
    }
    
    @Test
    public void testAddStudents()throws BusinessException{   
        ReceptionWork receptionWork = new ReceptionWork ("Propuesta de Aplicación de Aprendizaje Máquina y Cómputo Evolutivo en la Clasificación de Requisitos de Calidad", "Tesis",
        "Propuesta de uso de aprendizaje máquina en la clasificación de requisitos", "14/11/2019", "14/05/2021", "Concluido");
        receptionWork.setKey(3);
        Student student = new Student("S19014013", "Mariana Yazmin Vargas Segura ");
        receptionWork.addStudent(student);
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO(); 
        assertTrue(receptionWorkDAO .addedSucessfulStudents(receptionWork));
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
         ReceptionWork receptionWork = new ReceptionWork ("Propuesta de Aplicación de Aprendizaje Máquina y Cómputo Evolutivo en la Clasificación de Requisitos de Calidad", "Tesis",
        "Propuesta de uso de aprendizaje máquina en la clasificación de requisitos", "14/11/2019", "14/05/2021", "Concluido");
        receptionWork.setKey(3);
        MemberDAO memberDAO =new MemberDAO();
        receptionWork.addMember(memberDAO.getMemberByLicense("8325134"));
        ReceptionWorkDAO receptionWorkDAO= new ReceptionWorkDAO();
        assertTrue(receptionWorkDAO.deletedSucessfulColaborators(receptionWork));
    }
    
    @Test 
    public void testGetStudents () throws BusinessException{
        String enrollmentExpected="S19014013";
        ReceptionWorkDAO receptionWorkDAO= new ReceptionWorkDAO();
        ArrayList<Student> students= receptionWorkDAO.getStudents(3);
        String enrollmentResult= students.get(0).getEnrollment();
        assertEquals(enrollmentExpected,enrollmentResult);
    }
    
    @Test 
    public void testDeleteStudents () throws BusinessException{
       ReceptionWork receptionWork = new ReceptionWork ("Propuesta de Aplicación de Aprendizaje Máquina y Cómputo Evolutivo en la Clasificación de Requisitos de Calidad", "Tesis",
        "Propuesta de uso de aprendizaje máquina en la clasificación de requisitos", "14/11/2019", "14/05/2021", "Concluido");
        receptionWork.setKey(3);
        Student student= new Student("S19014013", "Mariana Yazmin Vargas Segura");
        receptionWork.addStudent(student);
        ReceptionWorkDAO receptionWorkDAO= new ReceptionWorkDAO();
        assertTrue(receptionWorkDAO.deletedSucessfulStudents(receptionWork));
    }
 
        @Test
    public void testAddLGAC() throws BusinessException {
        ReceptionWork receptionWork = new ReceptionWork("VaraAppX: Aplicación móvil para registro de datos detallados sobre varamientos de mamíferos marinos",
        "Práctico técnico","Aplicación enfocada al registro de datos sobre varamientos mamiferos",
        "20/12/2019","20/08/2020", "Concluido");
        receptionWork.setKey(1);
        LGAC lgac = new LGAC("Tecnologías de software", "Se orienta al estudio de diversas propiedades, enfoques, métodos de modelado y herramientas que conforman cada una de las diversas tecnologías aplicables al desarrollo del software con vistas a su adaptación, mejora y sustitución en el medio nacional");
        receptionWork.addLGAC(lgac);
        ReceptionWorkDAO receptionWorkDAO= new ReceptionWorkDAO();
        assertTrue(receptionWorkDAO.addLGACs(receptionWork));
    }
    
    @Test
    public void testGetLGAC() throws BusinessException {
        ReceptionWork receptionWork = new ReceptionWork("VaraAppX: Aplicación móvil para registro de datos detallados sobre varamientos de mamíferos marinos",
        "Práctico técnico","Aplicación enfocada al registro de datos sobre varamientos mamiferos",
        "20/12/2019","20/08/2020", "Concluido");
        int idReceptionWork = 1;
        LGAC lgac = new LGAC("Tecnologías de software", "Se orienta al estudio de diversas propiedades, enfoques, métodos de modelado y herramientas que conforman cada una de las diversas tecnologías aplicables al desarrollo del software con vistas a su adaptación, mejora y sustitución en el medio nacional");
        receptionWork.addLGAC(lgac);
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO ();
        assertEquals(receptionWorkDAO.getLGACs(idReceptionWork),receptionWork.getLGACs());
    }
}
