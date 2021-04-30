/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import domain.Member;
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
        assertTrue(memberDAO.saveMember(member));
    }

 
    @Test
    public void testSearchProfessionalLicense() throws BusinessException {
      MemberDAO memberDAO= new MemberDAO();
      String professionalLicenseExpected = "4065161";
      String professionalLicense =  memberDAO.searchProfessionalLicense("Maria de los Angeles Arenas Valdes");
      assertEquals (professionalLicense, professionalLicenseExpected);
    }

    @Test
    public void testGetMemberByLicense() throws BusinessException {
        System.out.println("getMemberByLicense");
        String professionalLicenseMember = "4065161";
        MemberDAO memberDAO = new MemberDAO();
        Member memberExpected = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999);
        Member member = memberDAO.getMemberByLicense(professionalLicenseMember);
        assertEquals(memberExpected, member);
    }


    @Test
    public void testFindMemberByLicense() {
        System.out.println("findMemberByLicense");
        String professionalLicense = "4065161";
        MemberDAO memberDAO = new MemberDAO();
        boolean result = memberDAO.findMemberByLicense(professionalLicense);
        assertTrue(result);
    }
    
    @Test 
    public void testUpdate() throws BusinessException {
        System.out.println("update");
        Member newMember = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"inactivo","1491");
        MemberDAO memberDAO = new MemberDAO();
        assertTrue(memberDAO.update(newMember));
    }
    
    @Test
    public void testDesactivateMember() throws BusinessException {
        System.out.println("DesactivateMember");
        Member member = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"inactivo","1491");
        MemberDAO memberDAO = new MemberDAO();
        assertTrue(memberDAO.desactivateMember(member));
    }
    
    @Test
    public void testActivateMember() throws BusinessException {
        System.out.println("ActivateMember");
        Member member = new Member("4065161", "Maria de los Angeles Arenas Valdes","Colaborador","Maestria","Maestria en ciencias de la computacion","Fundacion Arturo Rosenbulth",1999,"inactivo","1491");
        MemberDAO memberDAO = new MemberDAO();
        assertTrue(memberDAO.activateMember(member));
    }
}
