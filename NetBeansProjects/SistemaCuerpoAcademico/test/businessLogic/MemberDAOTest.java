package businessLogic;

import domain.Member;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import log.BusinessException;

public class MemberDAOTest {
    
   
    @Test
    public void testSaveMember() throws BusinessException {
        System.out.println("saveMember");
        Member member = new Member("11472543", "Ángel Juan Sánchez García","Integrante","Doctorado","Doctorado en inteligencia artificial","Universidad Veracruzana",2018,"JDOEIJ804");
        MemberDAO memberDAO = new MemberDAO();
        assertTrue(memberDAO.savedSucessfulMember(member));
    }

 
    @Test
    public void testSearchProfessionalLicense() throws BusinessException {
      MemberDAO memberDAO= new MemberDAO();
      String professionalLicenseExpected = "4065161";
      String nameMember = "Maria de los Angeles Arenas Valdes";
      String professionalLicense =  memberDAO.searchProfessionalLicenseByName(nameMember);
      assertEquals (professionalLicense, professionalLicenseExpected);
    }



    @Test
    public void getMemberByLicense() throws BusinessException {
        System.out.println("findMemberByLicense");
        MemberDAO memberDAO = new MemberDAO();
        String professionalLicense = "4065161";
        Member member = memberDAO.getMemberByLicense(professionalLicense);
        Member memberExpected = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"JDOEIJ804");
        assertTrue(member.equals(memberExpected));
        
    }
    
    
    @Test (expected = BusinessException.class)
    public void getMemberByLicenseNotFound() throws BusinessException {
        System.out.println("findMemberByLicense");
        MemberDAO memberDAO = new MemberDAO();
        String professionalLicense = "4065162";       
        Member memberExpected = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"JDOEIJ804");
        Member member = memberDAO.getMemberByLicense(professionalLicense);
       member.equals(memberExpected);
        
    }
    
    @Test 
    public void testUpdate() throws BusinessException {
        System.out.println("update");
        Member newMember = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"inactivo","JDOEIJ804");
        MemberDAO memberDAO = new MemberDAO();
        assertTrue(memberDAO.updatedSucessful(newMember));
    }
    
    @Test
    public void testDesactivateMember() throws BusinessException {
        System.out.println("DesactivateMember");
        Member member = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth.",1999,"activo","JDOEIJ804");
        MemberDAO memberDAO = new MemberDAO();
        assertTrue(memberDAO.desactivateMember(member));
    }
    
    @Test
    public void testActivateMember() throws BusinessException {
        System.out.println("ActivateMember");
        Member member = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth.",1999,"activo","JDOEIJ804");
        MemberDAO memberDAO = new MemberDAO();
        assertTrue(memberDAO.activateMember(member));
    }
    
     @Test
    public void testGetMembers() throws BusinessException {
        System.out.println("GetMembers");
        ArrayList<Member> memberList = new ArrayList<Member>();
        String groupAcademicKey = "JDOEIJ804";
        Member member2 = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"Activo","JDOEIJ804");
        Member member3 = new Member("7938268", "Maria Karen Cortes Verdin", "Responsable", "Doctorado", "Ciencias de la Computación", "Centro de Investigación en Matemáticas A.C", 2005, "Activo","JDOEIJ804");
        Member member4 = new Member("8325134", "Juan Carlos Perez Arriaga","Integrante","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",2013,"Activo","JDOEIJ804");
        Member member1 = new Member("11472543", "Ángel Juan Sánchez García","Integrante","Doctorado","Doctorado en inteligencia artificial","Universidad Veracruzana",2018,"JDOEIJ804");
        memberList.add(member1);
        memberList.add(member2);
        memberList.add(member3);
        memberList.add(member4);
        MemberDAO memberDAO = new MemberDAO();
        assertEquals(memberDAO.getMembers(groupAcademicKey),memberList);
    }
}
