package businessLogic;
import domain.Member;
import domain.GroupAcademic;

public interface IMemberDAO {
    public void saveMember(Member member, GroupAcademic groupAcademic);
    public String searchProfessionalLicense(Member member);
    public Member getMemberByLicense(String license);
    public boolean findMemberByLicense(String license);  
}