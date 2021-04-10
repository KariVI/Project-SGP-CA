package businessLogic;

import domain.Member;
import domain.GroupAcademic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
/**
 *
 * @author Laura Rodríguez
 */
public class MemberDAOTest {
    
    @Test
    public void testSaveMember() {

    }

    @Test
    public void testSearchProfessionalLicense() {
        MemberDAO memberDAO= new MemberDAO();
        Member memberAuxiliar;
       // memberAuxiliar = new Member("12345678", "Karen Cortés Verdin", "Responsable", "Doctorado", "kcortes@uv.mx");
        String licenseExpected;
        licenseExpected = "12345678";
        //String licenseMember = memberDAO.searchProfessionalLicense(memberAuxiliar);
        //assertEquals("Primera prueba de la cedula del miembro", licenseExpected, licenseMember);
    }

    @Test
    public void testGetMemberByLicense() {
        MemberDAO memberDAO= new MemberDAO();
        Member memberExpected;
       // memberExpected = new Member("12345678", "Karen Cortés Verdin", "Responsable", "Doctorado", "kcortes@uv.mx");
        Member memberResult = memberDAO.getMemberByLicense("12345678");
        //assertTrue(memberExpected.equals(memberResult));
    }
/*
    @Test
    public void testFindMemberByLicense() {
        MemberDAO memberDAO= new MemberDAO();
        Member memberAuxiliar;
        memberAuxiliar = new Member("12345678", "Karen Cortés Verdin", "Responsable", "Doctorado", "kcortes@uv.mx");
        String licenseMember = memberDAO.searchProfessionalLicense(memberAuxiliar);
        assertTrue(memberDAO.findMemberByLicense(licenseMember));
    }*/
}