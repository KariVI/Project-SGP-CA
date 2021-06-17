
package GUI;

import businessLogic.MemberDAO;
import businessLogic.MinuteDAO;
import domain.Meeting;
import domain.Member;
import domain.Minute;
import domain.MinuteComment;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

public class MinuteCheckCommentController implements Initializable {

    @FXML private ScrollPane spMembersApprove;
    @FXML private ScrollPane spMembersDispprove;
    @FXML private ScrollPane spMinuteComments;
    @FXML private Button btReturn;
    @FXML private Button btUpdate;
    private Minute minute;
    private Meeting meeting;
    private Member member;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void setMinute(Minute minute){
        this.minute = minute;
        setMembersApprove();
        setMembersDisapprove();
        setMinuteComments();
    }
    public void setMeeting(Meeting meeting){
        this.meeting = meeting;
    }
    public void setMember(Member member){
        this.member = member;
    }
    
    private void setMembersApprove(){
        
        try {
            GridPane gridPane= new GridPane();
            MinuteDAO minuteDAO = new MinuteDAO();
            ArrayList<Member> memberListApprove = minuteDAO.getMembersApprove(minute);
            gridPane.setVgap(7);      
            for(int i = 0; i < memberListApprove.size(); i++){
                Label lbMember = new Label(memberListApprove.get(i).getName());
                System.out.println("a"+memberListApprove.get(i).getName());
                gridPane.add(lbMember, 1, i);
            } 
            
            spMembersApprove.setContent(gridPane);
            
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
   
    private void setMembersDisapprove(){
        
        try {
            GridPane gridPane= new GridPane();
            MinuteDAO minuteDAO = new MinuteDAO();
            ArrayList<MinuteComment> memberListDisapprove = minuteDAO.getMinutesComments(minute.getIdMinute());
            gridPane.setVgap(7);
            
            for(int i = 0; i < memberListDisapprove.size(); i++){
                MemberDAO memberDAO = new MemberDAO();
                Member member = memberDAO.getMemberByLicense(memberListDisapprove.get(i).getProfessionalLicense());
                Label lbMember = new Label(member.getName());
                System.out.println(member.getName());
                gridPane.add(lbMember, 1, i);
            }
            
            spMembersDispprove.setContent(gridPane);
            
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
     
    private void setMinuteComments(){      
        try {
            GridPane gridPane= new GridPane();
            MinuteDAO minuteDAO = new MinuteDAO();
            ArrayList<MinuteComment> listDisapprove = minuteDAO.getMinutesComments(minute.getIdMinute());
            gridPane.setVgap(10);
            gridPane.setHgap(10);
            for(int i = 0; i < listDisapprove.size(); i++){
                MemberDAO memberDAO = new MemberDAO();
                Member member = memberDAO.getMemberByLicense(listDisapprove.get(i).getProfessionalLicense());
                TextArea taComment = new TextArea(listDisapprove.get(i).getComment());
                RadioButton rbMember = new RadioButton(member.getName());
                taComment.setEditable(false);
                gridPane.add(rbMember, 1, i);
                gridPane.add(taComment, 1, i+1);
            }
            
            spMinuteComments.setContent(gridPane);
            
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
   
    private ArrayList<Member> recoverMemberApprovedComments(){ 
        ArrayList<Member> members = new ArrayList<Member>();
        try {
            GridPane gridPane = (GridPane) spMinuteComments.getContent();
            
            MemberDAO memberDAO = new MemberDAO();
            int i = 0;
            int indexMembers =0;
            MinuteDAO minuteDAO = new MinuteDAO();
            ArrayList<MinuteComment> listDisapprove = minuteDAO.getMinutesComments(minute.getIdMinute());
            while (i < listDisapprove.size() ){
                RadioButton rbMember = (RadioButton) getNodeFromGridPane(gridPane, 1, i);
                if(rbMember.isSelected()){   
                    String license = memberDAO.searchProfessionalLicenseByName(rbMember.getText());
                    Member member = memberDAO.getMemberByLicense(license);
                    members.add(member);
                }
                i+=2;
            }
            
        } catch (BusinessException ex) {
           Log.logException(ex);
        }
        return members;
    }
    
    @FXML
    public void actionReturn(){ 
        if(deleteComments()){
                   AlertMessage alertMessage = new AlertMessage();
        alertMessage.showAlertSuccesfulSave("Los cambios");
        }
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
        openMinuteView();
    }
    
    public boolean deleteComments(){
        boolean value = false;
        ArrayList<Member> members = recoverMemberApprovedComments();
        MinuteDAO minuteDAO = new MinuteDAO();
        for(int i = 0; i < members.size(); i++){
            try {
                MinuteComment minuteComment = new MinuteComment(members.get(i).getProfessionalLicense(),minute.getIdMinute());
                minuteDAO.deletedSucessfulMinuteComment(minuteComment);
                value = true;
            } catch (BusinessException ex) {
                Log.logException(ex);
            }
        }
        return value;
    }
    
    private void openMinuteView(){ 
        try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/minuteShow.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MinuteShowController minuteShowController =loader.getController();   
              minuteShowController.setMember(member);
              minuteShowController.initializeMinute(meeting);
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);       
            } catch (IOException ex) {
                    Log.logException(ex);
            }
            primaryStage.show();
       } catch (MalformedURLException ex) {
           Log.logException(ex);
       } 
    }
    
    @FXML 
    public void actionUpdate(){
        Stage stage = (Stage) btUpdate.getScene().getWindow();
        stage.close();
        openMinuteModify();
    }
        
    private void openMinuteModify(){  
         try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/minuteModify.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MinuteModifyController minuteModifyController =loader.getController(); 
              minuteModifyController.setMember(member);
              minuteModifyController.initializeMinute(meeting); 
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);
            } catch (IOException ex) {
                    Log.logException(ex);
            }
            primaryStage.show();
       } catch (MalformedURLException ex) {
           Log.logException(ex);
       }
    }
    
   private Node getNodeFromGridPane(GridPane gridPane, int column, int row) {
        Node primaryNode = null;
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == row) {
                primaryNode = node;
            }
        }
        return primaryNode;
   }


}
