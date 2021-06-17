/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import domain.Member;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import log.BusinessException;
/**
 *
 * @author Mariana
 */
public class MemberDAOTest {
    
   
    @Test
    public void testSaveMember() throws BusinessException {
        System.out.println("saveMember");
        Member member = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"1491");
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
        Member memberExpected = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"1491");
        assertTrue(member.equals(memberExpected));
        
    }
    
    
    @Test (expected = BusinessException.class)
    public void getMemberByLicenseNotFound() throws BusinessException {
        System.out.println("findMemberByLicense");
        MemberDAO memberDAO = new MemberDAO();
        String professionalLicense = "4065162";       
        Member memberExpected = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"1491");
        Member member = memberDAO.getMemberByLicense(professionalLicense);
       member.equals(memberExpected);
        
    }
    
    @Test 
    public void testUpdate() throws BusinessException {
        System.out.println("update");
        Member newMember = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"inactivo","1491");
        MemberDAO memberDAO = new MemberDAO();
        assertTrue(memberDAO.updatedSucessful(newMember));
    }
    
    @Test
    public void testDesactivateMember() throws BusinessException {
        System.out.println("DesactivateMember");
        Member member = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth.",1999,"activo","1491");
        MemberDAO memberDAO = new MemberDAO();
        assertTrue(memberDAO.desactivateMember(member));
    }
    
    @Test
    public void testActivateMember() throws BusinessException {
        System.out.println("ActivateMember");
        Member member = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth.",1999,"activo","1491");
        MemberDAO memberDAO = new MemberDAO();
        assertTrue(memberDAO.activateMember(member));
    }
    
    

     @Test
    public void testGetMembers() throws BusinessException {
        System.out.println("GetMembers");
        ArrayList<Member> memberList = new ArrayList<Member>();
        Member member = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"Activo","1491");
        Member member2 = new Member("8325134", "Juan Carlos Perez Arriaga","Integrante","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",2013,"Activo","1491");
        memberList.add(member);
        memberList.add(member2);
        MemberDAO memberDAO = new MemberDAO();
        assertEquals(memberDAO.getMembers("JDOEIJ804"),memberList);
    }
}
