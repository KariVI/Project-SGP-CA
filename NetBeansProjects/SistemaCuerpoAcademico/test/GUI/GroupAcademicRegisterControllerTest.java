/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import domain.GroupAcademic;
import java.net.URL;
import java.util.ResourceBundle;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kari
 */
public class GroupAcademicRegisterControllerTest {
    
    public GroupAcademicRegisterControllerTest() {
    }

    @Test
    public void testValidateRepeateGroupAcademic() {
        System.out.println("validateRepeateGroupAcademic");
        GroupAcademicRegisterController instance = new GroupAcademicRegisterController();
        String key="JDOEIJ804";
        boolean result = instance.searchRepeateGroupAcademic(key);
        assertFalse(result);
    }

    
}
