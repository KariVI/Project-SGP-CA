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
        MemberDAO memberDAO = new MemberDAO();
        Member memberAuxiliar = new Member()
        GroupAcademic groupAcademicAuxiliar = new GroupAcademic("JDOEIJ804", "Ingenieria y Tecnologias", "Desarrollar métodos, técnicas y herramientas para el desarrollo de software", 
        "Generar conocimiento y formar recursos humanos en Ingeniería de Software que contribuyan al desarrollo de software de calidad", "El Cuerpo Académico se encuentra consolidado y es líder en Ingeniería de Software y áreas relacionadas",
        "En consolidacion");
        memberAuxiliar = new Member("12345678", "Karen Cortés Verdin", "Responsable", "Doctorado", "kcortes@uv.mx");
        memberDAO.saveMember(memberAuxiliar, groupAcademicAuxiliar);
        System.out.println("Insercion exitosa");
        String professionaLicense = memberDAO.searchProfessionalLicense(memberAuxiliar);
        assertTrue(memberDAO.findMemberByLicense(professionaLicense));
    }

    @Test
    public void testSearchProfessionalLicense() {
        MemberDAO memberDAO= new MemberDAO();
        Member memberAuxiliar;
        memberAuxiliar = new Member("12345678", "Karen Cortés Verdin", "Responsable", "Doctorado", "kcortes@uv.mx");
        String licenseExpected;
        licenseExpected = "12345678";
        String licenseMember = memberDAO.searchProfessionalLicense(memberAuxiliar);
        assertEquals("Primera prueba de la cedula del miembro", licenseExpected, licenseMember);
    }

    @Test
    public void testGetMemberByLicense() {
        MemberDAO memberDAO= new MemberDAO();
        Member memberExpected;
        memberExpected = new Member("12345678", "Karen Cortés Verdin", "Responsable", "Doctorado", "kcortes@uv.mx");
        Member memberResult = memberDAO.getMemberByLicense("12345678");
        assertTrue(memberExpected.equals(memberResult));
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