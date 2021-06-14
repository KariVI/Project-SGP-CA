package businessLogic;

import domain.LGAC;
import domain.Member;
import domain.Project;
import domain.ReceptionWork;
import domain.Student;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import log.BusinessException;

public class ProjectDaoTest {
    @Test
    public void testSave() throws BusinessException{
        ProjectDAO projectDAO= new ProjectDAO();
        Project projectAuxiliar = new Project("Inteligencia artificial" ,"La “facilidad” con la que el software puede tener errores es una de las causas por las que los proyectos fracasan, aunque no la única. Para 2005, el 70% de los proyectos de software fallaban, considerando que la mayoría de los proyectos de las TIC dependen del software, los defectos afectan a una gran variedad de estos. Otra de las causas por la que el software puede llegar a fallar es por la administración errónea de los proyectos, el mal cálculo de costos y tiempo incorrecto, estas equivocaciones no sólo causan perdidas monetarias y de productos finales con errores, sino que también llegan a afectar psicológicamente y por consecuente el rendimiento de las personas que trabajan en él, sin contar que tomará más tiempo terminar el proyecto.  Estos factores que impactan en el tiempo y confiabilidad del proyecto, pueden deberse al factor humano que pudieran ser solventados desarrollando muchas tareas de manera automática y tomando decisiones autónomas,  haciendo más eficiente la labor de un Ingeniero de Software y evitando el sesgo del humano. Estas situaciones se pueden reducir con la ayuda de ciertas herramientas y técnicas que se pasan por alto debido a la falta de conocimiento de su existencia.\n" +
        "\n" +
        "Es por esto por lo que, debido al éxito que ha tenido la aplicación de la Inteligencia Artificial a problemas y procesos de otras ingenierías, la Ingeniería de Software, al ser una ingeniería, consecuentemente debería tener herramientas y técnicas que auxilien a las actividades de la Ingeniería de Software durante la producción del software, y al notar la gran cantidad de proyectos fallidos, todas estas técnicas y herramientas tienen algo que aportar a la disciplina.\n" +
        "\n" +
        "El objetivo de este proyecto es desarrollar colaboración entre la Ingeniería de Software y la Inteligencia Artificial, para contribuir al desarrollo de ambas disciplinas, mediante la aplicación de técnicas de Inteligencia Artificial que aporten soluciones a problemas de procesos y del producto de software, así como la aplicación de estrategias, métodos y procesos que soporten a la investigación, desarrollo, y experimentación en el ámbito de la inteligencia Artificial.\n" +
         "\n" +
         "Este proyecto se encuentra alineado con las actividades que se han venido realizando en la Facultad de Estadística e Informática de la Universidad Veracruzana, particularmente en el Cuerpo Académico (CA) “Ingeniería de Tecnología de Software”, reconocido por PRODEP en nivel de “En consolidación”. También se ha colaborado junto con el Instituto de Investigación en Inteligencia Artificial de la Universidad Veracruzana.","04/05/2021","05/11/2021","JDOEIJ804");
        assertTrue(projectDAO.save(projectAuxiliar));
    }
  
    @Test
    public void testSearchId() throws BusinessException{ 
      ProjectDAO projectDAO= new ProjectDAO();
      Project projectAuxiliar = new Project("Inteligencia artificial" ,"Descripcion","04/05/2021","05/11/2021","JDOEIJ804");
      int idProject = projectDAO.searchId(projectAuxiliar);
      assertEquals("Test id Project", 1, idProject);
    }
    
    @Test(expected = BusinessException.class)
    public void testSearchIdNotFound() throws BusinessException{ 
      ProjectDAO projectDAO= new ProjectDAO();
      Project projectAuxiliar = new Project("Inteligencia" ,"Descripcion","04/05/2021","05/11/2021","JDOEIJ804");
      projectDAO.searchId(projectAuxiliar);
    }


    
    @Test 
    public void testUpdate() throws BusinessException {
        System.out.println("update");
        Project newProject = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021","JDOEIJ804");
        ProjectDAO projectDAO = new ProjectDAO();
        assertTrue(projectDAO.update(newProject));
    }
    
    @Test
    public void testGetProjects() throws BusinessException {
        ProjectDAO projectDAO = new ProjectDAO();
        ArrayList<Project> result = projectDAO.getProjects();
        ArrayList<Project> resultExpected = new ArrayList<Project>();
         Project project = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021","JDOEIJ804");
         Project project1 = new Project(2," Hacia un Modelo de Campus Accesible: Facultad de Estadística e Informática" ,"Actualmente la democratización de la educación representa un reto para cualquier Institución de Educación Superior, al mismo tiempo que el término “Institución Incluyente” cobra mayor sentido como\n" +
        "parte de esta democratización. ","04/05/2021","05/11/2021","JDOEIJ804");
        resultExpected.add(project);
        resultExpected.add(project1);
        assertEquals(result,resultExpected);
    }  
    
    @Test
    public void testGetProjectById() throws BusinessException{
        ProjectDAO projectDAO= new ProjectDAO();
        Project projectExpected = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021","JDOEIJ804");
        Project projectActual = projectDAO.getProjectById(1);
        assertTrue(projectExpected.equals(projectActual));
    }
    
    @Test
    public void testAddStudents() throws BusinessException {
        Project project = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021","JDOEIJ804");
        Student student1 = new Student("S19014013", "Mariana Yazmin Vargas Segura");
        Student student2 = new Student("S19014023", "Karina Valdes Iglesias");
        project.setStudent(student1);
        project.setStudent(student2);
        ProjectDAO projectDAO= new ProjectDAO();
        assertTrue(projectDAO.addStudents(project));
    }
    
    @Test
    public void testAddColaborators() throws BusinessException {
        Project project = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021","JDOEIJ804");
        Member member = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"JDOEIJ804");
        project.setMember(member);
        ProjectDAO projectDAO= new ProjectDAO();
        assertTrue(projectDAO.addColaborators(project));
    }
    
    @Test
    public void testAddLGAC() throws BusinessException {
        Project project = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021","JDOEIJ804");
        LGAC lgac = new LGAC("Tecnologías de software", "Se orienta al estudio de diversas propiedades, enfoques, métodos de modelado y herramientas que conforman cada una de las diversas tecnologías aplicables al desarrollo del software con vistas a su adaptación, mejora y sustitución en el medio nacional");
        project.setLGAC(lgac);
        ProjectDAO projectDAO= new ProjectDAO();
        assertTrue(projectDAO.addLGAC(project));
    }
    
    @Test
    public void testAddReceptionWork() throws BusinessException {
        Project project = new Project(1,"Inteligencia artificial" ,"El proyecto es sobre...","04/05/2021","05/11/2021","JDOEIJ804");
        ReceptionWork receptionWork = new ReceptionWork("VaraAppX: Aplicación móvil para registro de datos detallados sobre varamientos de mamíferos marinos",
        "Práctico técnico","Aplicación enfocada al registro de datos sobre varamientos mamiferos",
        "20/12/2019","20/08/2020", "Concluido","JDOEIJ804");
        receptionWork.setKey(1);
        project.setReceptionWork(receptionWork);
        ProjectDAO projectDAO= new ProjectDAO();
        assertTrue(projectDAO.addReceptionWork(project));
    }
    
    @Test
    public void testGetReceptionWork() throws BusinessException {
        Project project = new Project(2," Hacia un Modelo de Campus Accesible: Facultad de Estadística e Informática" ,"Actualmente la democratización de la educación representa un reto para cualquier Institución de Educación Superior, al mismo tiempo que el término “Institución Incluyente” cobra mayor sentido como\n" +
        "parte de esta democratización. ","04/05/2021","05/11/2021","JDOEIJ804");
        ReceptionWork receptionWork = new ReceptionWork("VaraAppX: Aplicación móvil para registro de datos detallados sobre varamientos de mamíferos marinos",
        "Práctico técnico","Aplicación enfocada al registro de datos sobre varamientos mamiferos",
        "20/12/2019","20/08/2020", "Concluido","JDOEIJ804");
        receptionWork.setKey(1);
        project.setReceptionWork(receptionWork);
        ProjectDAO projectDAO= new ProjectDAO();
        assertEquals(projectDAO.getReceptionWorks(project), project.getReceptionWorks());
    }
    
    @Test
    public void testGetLGAC() throws BusinessException {
        Project project = new Project(2," Hacia un Modelo de Campus Accesible: Facultad de Estadística e Informática" ,"Actualmente la democratización de la educación representa un reto para cualquier Institución de Educación Superior, al mismo tiempo que el término “Institución Incluyente” cobra mayor sentido como\n" +
        "parte de esta democratización. ","04/05/2021","05/11/2021","JDOEIJ804");
        LGAC lgac = new LGAC("Tecnologías de software", "Se orienta al estudio de diversas propiedades, enfoques, métodos de modelado y herramientas que conforman cada una de las diversas tecnologías aplicables al desarrollo del software con vistas a su adaptación, mejora y sustitución en el medio nacional");
        project.setLGAC(lgac);
        ProjectDAO projectDAO= new ProjectDAO();
        assertEquals(projectDAO.getLGACs(project),project.getLGACs());
    }
    
    @Test
        public void testGetColaborators() throws BusinessException {
        Project project = new Project(2," Hacia un Modelo de Campus Accesible: Facultad de Estadística e Informática" ,"Actualmente la democratización de la educación representa un reto para cualquier Institución de Educación Superior, al mismo tiempo que el término “Institución Incluyente” cobra mayor sentido como\n" +
        "parte de esta democratización. ","04/05/2021","05/11/2021","JDOEIJ804");
        Member member = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"1491");
        project.setMember(member);
        ProjectDAO projectDAO= new ProjectDAO();
        assertEquals(projectDAO.getColaborators(project).get(0), project.getMembers().get(0));
    }
        
    @Test
    public void testGetStudents() throws BusinessException {
        Project project = new Project(2," Hacia un Modelo de Campus Accesible: Facultad de Estadística e Informática" ,"Actualmente la democratización de la educación representa un reto para cualquier Institución de Educación Superior, al mismo tiempo que el término “Institución Incluyente” cobra mayor sentido como\n" +
        "parte de esta democratización. ","04/05/2021","05/11/2021","JDOEIJ804");
        Student student1 = new Student("S19014013", "Mariana Yazmin Vargas Segura");
        Student student2 = new Student("S19014023", "Karina Valdes Iglesias");
        project.setStudent(student1);
        project.setStudent(student2);
        ProjectDAO projectDAO= new ProjectDAO();
        assertEquals(projectDAO.getStudents(project),project.getStudents());
    }
    
}