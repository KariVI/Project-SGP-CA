
package GUI;

import businessLogic.GroupAcademicDAO;
import businessLogic.LGACDAO;
import businessLogic.MemberDAO;
import businessLogic.PreliminarProjectDAO;
import businessLogic.ReceptionWorkDAO;
import businessLogic.StudentDAO;
import domain.LGAC;
import domain.Member;
import domain.PreliminarProject;
import domain.ReceptionWork;
import domain.Student;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import log.BusinessException;
import log.Log;


public class ReceptionWorkRegisterController implements Initializable {

    @FXML private TextFieldLimited tfTitle;
    @FXML private TextArea taDescription;
    @FXML private TextFieldLimited tfNumberStudents;
    @FXML private Button btOk;
    @FXML private Button btSave;
    @FXML private Button btExit;
    @FXML private ScrollPane spStudent = new ScrollPane();
    @FXML private ScrollPane spLgacs;
    @FXML private ComboBox cbType;
    @FXML private ComboBox cbPreliminarProject;
    @FXML private ComboBox cbState;
    @FXML DatePicker dpStartDate;
    @FXML DatePicker dpEndDate;
    private ObservableList<String> types;
    private ObservableList<String> states;
    @FXML private ComboBox cbDirector;
    @FXML private ComboBox cbCodirectors;
    @FXML private TableColumn tcCodirector;
    @FXML private Button btAddCodirector;
    @FXML private Button btDelete;
    @FXML private TableView<Member> tvCodirectors;
    private ListChangeListener<Member> tableCodirectorsListener;
    private int indexCodirectors;
    private ObservableList<Member> codirectors ;
    private ObservableList<Member> members ;
    private ObservableList<PreliminarProject> preliminarProjects;
    private ReceptionWork receptionWork = new ReceptionWork();
    private String keyGroupAcademic;
    private Member member;

    public void setKeyGroupAcademic(String keyGroupAcademic) {
        this.keyGroupAcademic = keyGroupAcademic;
        initializeMembers();
        cbDirector.getSelectionModel().selectFirst();
       cbCodirectors.getSelectionModel().selectFirst();
        try {
            getlgacs();
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }

    public void setMember(Member member) {
        this.member = member;
    }
    
    
    public void setPreliminarProjects(ObservableList<PreliminarProject> preliminarProjects) {  
         for( int i = 0; i<preliminarProjects.size(); i++) {
                  this.preliminarProjects.add(preliminarProjects.get(i));
            }
        cbPreliminarProject.getSelectionModel().selectFirst();

    }
    

    @FXML
    private void addStudents(ActionEvent actionEvent){
         GridPane gridPane= new GridPane();
        Validation validation =new Validation();
        if(!tfNumberStudents.getText().isEmpty() && (validation.validateNumberField(tfNumberStudents.getText()))){
            Integer students=Integer.parseInt(tfNumberStudents.getText());  
            gridPane.setHgap (5);
            gridPane.setVgap (5);
            int i=0;
            int sizeRows=3;
           while (i < ( students * sizeRows)){  
                TextField tfEnrollmentStudent = new TextField();
                tfEnrollmentStudent .setPromptText("Matricula: ");   
                TextField tfNameStudent = new TextField();
                tfNameStudent.setPrefWidth(200);
                tfNameStudent.setPromptText("Nombre: ");
                Label label = new Label("Estudiante");
                gridPane.add(label,1,i);
                gridPane.add(tfEnrollmentStudent,1,(i + 1));
                gridPane.add(tfNameStudent,1, (i + 2));
                i=i+3;
           }  
        }
        spStudent.setContent(gridPane);
    }
    
    
    @FXML
    private void actionSave(ActionEvent actionEvent){
      String title =tfTitle.getText();
        String description= taDescription.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String type = (String) cbType.getSelectionModel().getSelectedItem();
        String state = (String) cbState.getSelectionModel().getSelectedItem();
        PreliminarProject preliminarProject = (PreliminarProject) cbPreliminarProject.getSelectionModel().getSelectedItem();        
        String startDate;
        String endDate;
        if((!validateFieldEmpty()) && validateInformationField() ){
            if( validateDates()){ 
                startDate = dpStartDate.getValue().format(formatter);
                endDate = dpEndDate.getValue().format(formatter);
                receptionWork.setTitle(title);
                receptionWork.setDescription(description);
                receptionWork.setDateStart(startDate);
                receptionWork.setDateEnd(endDate);
                receptionWork.setType(type);
               // receptionWork.setPreliminarProject(preliminarProject);
                receptionWork.setActualState(state);
                receptionWork.setKeyGroupAcademic(keyGroupAcademic);

                if(!searchRepeateReceptionWork ()){    
                    saveReceptionWork ();
                }else{  
                    sendAlert();
                }
            }else{  
                sendAlert();
            }
        }else{
            sendAlert();
        }
    }
    
    @FXML
    private void actionExit(ActionEvent actionEvent){
        Stage stage = (Stage) btExit.getScene().getWindow();
        stage.close();
       openReceptionWorkList();
    }
    
    private void openReceptionWorkList(){   
         try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/receptionWorkList.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              ReceptionWorkListController receptionWorkListController =loader.getController();     
              receptionWorkListController.setKeyGroupAcademic(keyGroupAcademic);
              receptionWorkListController.setMember(member);
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
    private void actionAddCodirector(ActionEvent actionEvent){    
        Member codirector = (Member) cbCodirectors.getSelectionModel().getSelectedItem();    
        if(!repeatedCodirector(codirector)){
           codirectors.add(codirector);
        }else{  
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.showAlertValidateFailed("Codirector repetido");
        }
    }
    
    @FXML
    private void actionDelete(ActionEvent event){
        codirectors.remove(indexCodirectors);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            tfTitle.setMaxLength(200);
            tfNumberStudents.setMaxLength(2);
            types=FXCollections.observableArrayList();
            states = FXCollections.observableArrayList();
            types.add("Práctico técnico");
            types.add("Práctico");
            types.add("Tesis");
            types.add("Tesina");
            types.add("Monografía");
            states.add("Concluido");
            states.add("Abandonado");
            states.add("Registrado");
            cbType.setItems(types);
            cbState.setItems(states);
            cbType.getSelectionModel().selectFirst();
            cbState.getSelectionModel().selectFirst();
            preliminarProjects=FXCollections.observableArrayList();
            cbPreliminarProject.setItems(preliminarProjects);
            cbPreliminarProject.getSelectionModel().selectFirst();
             tcCodirector.setCellValueFactory(new PropertyValueFactory<Member,String>("name"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dpStartDate.setConverter(new LocalDateStringConverter(formatter, null));
            dpEndDate.setConverter(new LocalDateStringConverter(formatter, null));
            members = FXCollections.observableArrayList();
            codirectors= FXCollections.observableArrayList();
            cbDirector.setItems(members);
            cbCodirectors.setItems(members);
            tvCodirectors.setItems(codirectors);
            tvCodirectors.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                     setSelectedCodirector();
                 }
                }
            );

            tableCodirectorsListener = new ListChangeListener<Member>(){
                @Override
                public void onChanged(ListChangeListener.Change<? extends Member> codirector) {
                    setSelectedCodirector();
                }
            };
        
    }    
    
     private Member getSelectedCodirector(){
        Member codirector = null;
        int tamTable = 1;
        if(tvCodirectors != null){
            List<Member> codirectorTable = tvCodirectors.getSelectionModel().getSelectedItems();
            if(codirectorTable.size() == tamTable){
                codirector = codirectorTable.get(0);
            }
        }
        return codirector;
    }
    
    private void setSelectedCodirector(){
        Member codirector = getSelectedCodirector();
        indexCodirectors = codirectors.indexOf(codirector);
            if(codirector != null){
                cbCodirectors.getSelectionModel().select(codirector);
            }
    }
    
    
    private void saveReceptionWork(){   
         ReceptionWorkDAO receptionWorkDAO =  new ReceptionWorkDAO();
        try{  
            if(validateColaborators()){
                if(receptionWorkDAO.savedSucessful(receptionWork)){  
                    receptionWork.setKey(receptionWorkDAO.getId(receptionWork));
                    saveColaborators();
                   if((! tfNumberStudents.getText().isEmpty())){   
                        recoverStudents();
                    }
                    recoverLgacs();
                    AlertMessage alertMessage = new AlertMessage();
                    alertMessage.showAlertSuccesfulSave("Trabajo recepcional");
                    Stage stage = (Stage) btSave.getScene().getWindow();
                    stage.close();
                    openReceptionWorkList();
                }
           }else{    
          AlertMessage alertMessage = new AlertMessage();
          alertMessage.showAlertValidateFailed("El director y el codirector no pueden ser el mismo");
      }
        } catch (BusinessException ex){ 
            if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }
    }
    
    private boolean validateDates(){
        boolean value=false;
        
        if(dpStartDate.getValue()!=null && dpEndDate.getValue()!=null){
           LocalDate dateStart= dpStartDate.getValue();
            LocalDate dateEnd = dpEndDate.getValue();
          if(dateEnd.isAfter(dateStart) ){ 
               value=true;
         }
        }
        return value;
    }
    
    public boolean validateColaborators(){  
        boolean value = true;
        Member director = (Member) cbDirector.getSelectionModel().getSelectedItem();
        if(repeatedCodirector(director)){
                 value=false; 
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("El director y el codirector no pueden ser el mismo");  
            }
        return value;
    }
   
    
    private boolean saveColaborators(){    
       boolean value=false;
       ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
       MemberDAO memberDAO = new MemberDAO();
       ArrayList<Member> members = new ArrayList<Member>();
        try {
            Member director=(Member) cbDirector.getSelectionModel().getSelectedItem();
            director.setRole("Director");
            receptionWork.addMember(director);
            for(int i=0; i < codirectors.size(); i++){  
                codirectors.get(i).setRole("Codirector");
               receptionWork.addMember(codirectors.get(i));
            }
            receptionWorkDAO.addedSucessfulColaborators(receptionWork);
        } catch (BusinessException ex) {
            if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }
        return value;
    }
    
    
    
    private void recoverStudents() throws BusinessException{   
        GridPane gridPane= (GridPane) spStudent.getContent();
        ArrayList<Student> students = new ArrayList<Student>();
            int i=1;
            Validation validation = new Validation();
             if(validation.validateNumberField(tfNumberStudents.getText())){
                Integer lgacs=Integer.parseInt(tfNumberStudents.getText());  
                int sizeRows=3;
               while (i < (sizeRows * lgacs)){
                   TextField enrollment = (TextField) getNodeFromGridPane( gridPane, 1, i);
                   TextField name = (TextField) getNodeFromGridPane( gridPane, 1, (i + 1));
                   i=i+3;
                   if(validateFieldsStudent(enrollment,name)){         
                     String enrollmentStudent= enrollment.getText();
                     String nameStudent= name.getText(); 
                     Student student = new Student(enrollmentStudent,nameStudent);
                      if(!findRepeteadedStudents(students,student)) {  
                        students.add(student);
                        saveStudent(student);
                      }
                   }
               }
               receptionWork.setStudents(students);
               addStudentsInReceptionWork();
             }
    }
    
     public boolean findRepeteadedStudents(ArrayList<Student> students,Student student){
       boolean value=false; 
            
            int i =0;
            while(i < students.size() && value==false){ 
                if(students.get(i).equals(student)){    
                    value=true;
                }
                i++;
            }
        return value;
    }
     
     private boolean validateFieldsStudent(TextField enrollment, TextField name){
        boolean value=true;
        AlertMessage alertMessage =new AlertMessage();
        Validation validation=new Validation();
        if( validation.findInvalidField(name.getText()) || validation.findInvalidKeyAlphanumeric(enrollment.getText())){    
            value=false;
            alertMessage.showAlertValidateFailed("Existen campos con caracteres invalidos");
        }else if( name.getText().isEmpty()|| enrollment.getText().isEmpty()  ){ 
            alertMessage.showAlertValidateFailed("Existen campos vacios");
            value=false;
        }
     return value;
    }
    
    private void saveStudent(Student student ) throws BusinessException{   
        if(!searchRepeateStudent(student)){
           StudentDAO studentDAO = new StudentDAO();
           studentDAO.savedSucessful(student);
        }
         
    }
    
    private void sendAlert(){ 
          AlertMessage alertMessage= new AlertMessage();
          if(validateFieldEmpty() ){  
              alertMessage.showAlertValidateFailed("No se han llenado todos los campos");
          }
          if(!validateInformationField()){
             alertMessage.showAlertValidateFailed("Existen campos con caracteres invalidos");
          }

          if(searchRepeateReceptionWork()){
              alertMessage.showAlertValidateFailed("El trabajo recepcional ya se encuentra registrado");
          }
          
          if(!validateDates()){
            alertMessage.showAlertValidateFailed("La fecha de fin debe ser mayor a la de inicio");

          }
      }
    
    private void addStudentsInReceptionWork(){ 
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        try {
           receptionWorkDAO.addedSucessfulStudents(receptionWork);
        } catch (BusinessException ex) {
            if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage= new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }
    }
       
    private boolean searchRepeateStudent(Student student){ 
        boolean value=false;
        StudentDAO studentDAO = new StudentDAO();
        try{    
            if(student.equals(studentDAO.getByEnrollment(student.getEnrollment()))){ 
                value=true;
            }
        }catch(BusinessException ex){   
            Log.logException(ex);
        }
        return value;
    }
     
    
    private Node getNodeFromGridPane(GridPane gridPane, int column, int row) {
        Node primaryNode=null;
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == row) {
                primaryNode=node;
            }
        }
        return primaryNode;
   }
    
   
     
     private boolean validateFieldEmpty(){ 
          boolean value=false;
          if(tfTitle.getText().isEmpty() 
           || taDescription.getText().isEmpty()  || dpStartDate == null 
            || dpEndDate==null  || cbPreliminarProject.getSelectionModel().getSelectedItem() == null
           ){
              value=true;
          }
          return value;
    }
     
    private boolean validateInformationField(){ 
         boolean value=true;
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String startDate = dpStartDate.getValue().format(formatter);
        String endDate = dpEndDate.getValue().format(formatter);
        Validation validation=new Validation();
        if(validation.findInvalidField(tfTitle.getText())
        || validation.findInvalidField(taDescription.getText()) || (!validation.validateDate(startDate))
        || (!validation.validateDate(endDate)) ){   
            value=false;
        }  
        return value;
    }
    
     public boolean repeatedCodirector(Member codirector){
        Boolean value = false;
        int i = 0;
        while((value==false) && (i<codirectors.size())){
            String enrollmentCodirector= codirectors.get(i).getProfessionalLicense();
            if(enrollmentCodirector.equals(codirector.getProfessionalLicense())){
                value = true;
            }
            i++;
        }
       return value;
    }
     
    
    private void getlgacs() throws BusinessException{    
        
         GridPane gridPane= new GridPane();
         GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO ();
            gridPane.setHgap (2);
            gridPane.setVgap (2);
            int i=0;
            gridPane.add(new Label ("Selecciona LGAC relacionadas: "),1,0);
            int indexGridPane=1;
            ArrayList <LGAC> lgacs = groupAcademicDAO.getLGACs(keyGroupAcademic);
           while (i < lgacs.size()){  
                CheckBox checkBox = new CheckBox(lgacs.get(i).getName());
                gridPane.add(checkBox,1,indexGridPane);
                i++;
                indexGridPane++;
           }  
        

           spLgacs.setContent(gridPane);
    }
    
    private void recoverLgacs() throws BusinessException{    
        GridPane gridPane= (GridPane) spLgacs.getContent();
        ArrayList<LGAC> lgacs = new ArrayList<LGAC>();
        LGACDAO lgacDAO = new LGACDAO();
            int i=1;
            int indexLGACs =0;
            GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO ();
            ArrayList <LGAC> lgacsAuxiliar = groupAcademicDAO.getLGACs(keyGroupAcademic);
           while (i ==lgacsAuxiliar.size() ){
               CheckBox checkBox = (CheckBox) getNodeFromGridPane( gridPane, 1, i);
               if(checkBox.isSelected()){   
                 LGAC lgac= lgacDAO.getLgacByName(checkBox.getText());
                 lgacs.add(lgac);
               }
               i++;
           }
            ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
          
           
    }
    
    
     private boolean searchRepeateReceptionWork()   { 
       boolean value=false; 
        try {   
            ReceptionWorkDAO receptionWorkDAO= new ReceptionWorkDAO();
           receptionWorkDAO.getId(receptionWork);
            value=true;
        }catch (BusinessException ex){ 
            if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }
        return value;
    }
     
    public void initializePreliminarProjects() throws BusinessException{   
            PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
                ArrayList <PreliminarProject> preliminarProjectList = preliminarProjectDAO.getPreliminarProjects(keyGroupAcademic) ;
               
            for( int i = 0; i<preliminarProjectList.size(); i++) {
                  preliminarProjects.add(preliminarProjectList.get(i));
            }
    }
    
     private void initializeMembers() {
        try {
            MemberDAO memberDAO = new MemberDAO();
            ArrayList <Member> memberList = new ArrayList<Member>();
            memberList = memberDAO.getMembers(keyGroupAcademic);
            for( int i = 0; i<memberList.size(); i++) {
                members.add(memberList.get(i));
            }
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
}
