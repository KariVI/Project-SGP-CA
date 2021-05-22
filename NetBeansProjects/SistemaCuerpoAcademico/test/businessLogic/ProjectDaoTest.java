package businessLogic;

import domain.LGAC;
import domain.Member;
import domain.Project;
import domain.ReceptionWork;
import domain.Student;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import log.BusinessException;

public class ProjectDaoTest {
   
  
    @Test
    public void testSearchId() throws BusinessException{ 
      ProjectDAO projectDAO= new ProjectDAO();
      Project projectAuxiliar;
      projectAuxiliar = new Project("Inteligencia artificial" ,"Descripcion","04/05/2021","05/11/2021");
      int idProject = projectDAO.searchId(projectAuxiliar);
      assertEquals("Test id Project", 1, idProject);
    }
    
    @Test
    public void testSearchIdNotFound() throws BusinessException{ 
      ProjectDAO projectDAO= new ProjectDAO();
      Project projectAuxiliar;
      projectAuxiliar = new Project("Inteligencia" ,"Descripcion","04/05/2021","05/11/2021");
      int idExpected=0;
      int idProject= projectDAO.searchId(projectAuxiliar);
      assertEquals("Test id Project", idExpected, idProject);
    }
    
    @Test
    public void testFindProjectById() throws BusinessException{
        ProjectDAO projectDAO= new ProjectDAO();
        Project projectAuxiliar;
        projectAuxiliar = new Project("Inteligencia artificial" ,"Descripcion","04/05/2021","05/11/2021");
        int idProject= projectDAO.searchId(projectAuxiliar);
        assertTrue(projectDAO.findProjectById(idProject));
    }

    @Test
    public void testFindProjectByIdNotFound() throws BusinessException{
        ProjectDAO projectDAO= new ProjectDAO();
        Project projectAuxiliar;
        projectAuxiliar = new Project("Inteligencia" ,"Descripcion","04/05/2021","05/11/2021");
        int idProject = projectDAO.searchId(projectAuxiliar);
        assertFalse(projectDAO.findProjectById(idProject));
    }

    @Test
    public void testSave() throws BusinessException{
        ProjectDAO projectDAO= new ProjectDAO();
        Project projectAuxiliar;
        projectAuxiliar = new Project("Inteligencia artificial" ,"Descripcion","04/05/2021","05/11/2021");
        assertTrue(projectDAO.save(projectAuxiliar));
    }
    
    @Test
    public void testGetProjectById() throws BusinessException{
        ProjectDAO projectDAO= new ProjectDAO();
        Project projectExpected = new Project(1,"Inteligencia artificial" ,"Descripcion","04/05/2021","05/11/2021");
        Project projectActual = projectDAO.getProjectById(1);
        assertTrue(projectExpected.equals(projectActual));
    }
    
    @Test
    public void testGetProjects() throws BusinessException {
        ProjectDAO projectDAO = new ProjectDAO();
        ArrayList<Project> result = projectDAO.getProjects();
        assertNotNull(result);
    }  
    
    @Test 
    public void testUpdate() throws BusinessException {
        System.out.println("update");
        Project newProject = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021");
        ProjectDAO projectDAO = new ProjectDAO();
        assertTrue(projectDAO.update(newProject));
    }
     
    @Test
    public void testAddStudents() throws BusinessException {
        Project project = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021");
        Student student1 = new Student("S19014013", "Mariana Yazmin Vargas Segura");
        Student student2 = new Student("S19014023", "Karina Valdes Iglesias");
        project.setStudent(student1);
        project.setStudent(student2);
        ProjectDAO projectDAO= new ProjectDAO();
        assertTrue(projectDAO.addStudents(project));
    }
    
    @Test
    public void testAddColaborators() throws BusinessException {
        Project project = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021");
        Member member = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"1491");
        project.setMember(member);
        ProjectDAO projectDAO= new ProjectDAO();
        assertTrue(projectDAO.addColaborators(project));
    }
    
    @Test
    public void testAddLGAC() throws BusinessException {
        Project project = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021");
        LGAC lgac = new LGAC("Tecnologías de software", "Se orienta al estudio de diversas propiedades, enfoques, métodos de modelado y herramientas que conforman cada una de las diversas tecnologías aplicables al desarrollo del software con vistas a su adaptación, mejora y sustitución en el medio nacional");
        project.setLGAC(lgac);
        ProjectDAO projectDAO= new ProjectDAO();
        assertTrue(projectDAO.addLGAC(project));
    }
    
    @Test
    public void testAddReceptionWork() throws BusinessException {
        Project project = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021");
        ReceptionWork receptionWork = new ReceptionWork("VaraAppX: Aplicación móvil para registro de datos detallados sobre varamientos de mamíferos marinos",
        "Práctico técnico","Aplicación enfocada al registro de datos sobre varamientos mamiferos",
        "20/12/2019","20/08/2020", "Concluido");
        receptionWork.setKey(1);
        project.setReceptionWork(receptionWork);
        ProjectDAO projectDAO= new ProjectDAO();
        assertTrue(projectDAO.addReceptionWork(project));
    }
    
    @Test
    public void testGetReceptionWork() throws BusinessException {
        Project project = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021");
        ReceptionWork receptionWork = new ReceptionWork("VaraAppX: Aplicación móvil para registro de datos detallados sobre varamientos de mamíferos marinos",
        "Práctico técnico","Aplicación enfocada al registro de datos sobre varamientos mamiferos",
        "20/12/2019","20/08/2020", "Concluido");
        receptionWork.setKey(1);
        project.setReceptionWork(receptionWork);
        ProjectDAO projectDAO= new ProjectDAO();
        assertEquals(projectDAO.getReceptionWorks(project), project.getReceptionWorks());
    }
    
    @Test
    public void testGetLGAC() throws BusinessException {
        Project project = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021");
        LGAC lgac = new LGAC("Tecnologías de software", "Se orienta al estudio de diversas propiedades, enfoques, métodos de modelado y herramientas que conforman cada una de las diversas tecnologías aplicables al desarrollo del software con vistas a su adaptación, mejora y sustitución en el medio nacional");
        project.setLGAC(lgac);
        ProjectDAO projectDAO= new ProjectDAO();
        assertEquals(projectDAO.getLGACs(project),project.getLGACs());
    }
    
    @Test
        public void testGetColaborators() throws BusinessException {
        Project project = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021");
        Member member = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"1491");
        project.setMember(member);
        ProjectDAO projectDAO= new ProjectDAO();
        assertEquals(projectDAO.getColaborators(project), project.getMembers());
    }
        
    @Test
    public void testGetStudents() throws BusinessException {
        Project project = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021");
        Student student1 = new Student("S19014013", "Mariana Yazmin Vargas Segura");
        Student student2 = new Student("S19014023", "Karina Valdes Iglesias");
        project.setStudent(student1);
        project.setStudent(student2);
        ProjectDAO projectDAO= new ProjectDAO();
        assertEquals(projectDAO.getStudents(project),project.getStudents());
    }
}