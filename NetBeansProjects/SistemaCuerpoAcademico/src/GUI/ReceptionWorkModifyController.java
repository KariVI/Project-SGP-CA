
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import log.BusinessException;
import log.Log;

public class ReceptionWorkModifyController implements Initializable {

    @FXML private TextField tfTitle;
    @FXML private TextField tfDirector;
    @FXML private TextArea taDescription;
    @FXML private TextArea taCodirectors;
    @FXML private Button btSave;
    @FXML private Button btExit;
    @FXML private Pane paneStudent;
    @FXML private Pane lgacsPane;
    @FXML private ComboBox cbType;
    @FXML private ComboBox cbPreliminarProject;
    @FXML private ComboBox cbState;
    @FXML private DatePicker dpStartDate;
    @FXML private  DatePicker dpEndDate;
    private ObservableList<String> types;
    private ObservableList<String> states;
    private ObservableList<PreliminarProject> preliminarProjects;
    private ObservableList<Member> codirectors ;
    private ObservableList<Member> members ;
    private ObservableList <Member> codirectorsNew;
    @FXML private ComboBox cbDirector;
    @FXML private ComboBox cbCodirectors;
    @FXML private TableColumn tcCodirector;
    @FXML private Button btAddCodirector;
    @FXML private Button btDelete;
    @FXML private TableView<Member> tvCodirectors;
    private ListChangeListener<Member> tableCodirectorsListener;
    private int indexCodirectors;
    private ReceptionWork receptionWorkRecover;
    private ReceptionWork receptionWorkNew;

    
    
    
    
     public void setReceptionWork(ReceptionWork receptionWork){
        this.receptionWorkRecover= receptionWork;
    }
    
    public void initializeReceptionWork(){
       tfTitle.setText("Título: "+ receptionWorkRecover.getTitle());
       LocalDate localStartDate = LocalDate.parse(receptionWorkRecover.getDateStart(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
       LocalDate localEndDate = LocalDate.parse(receptionWorkRecover.getDateEnd(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
       dpStartDate.setValue(localStartDate);
       dpEndDate.setValue(localEndDate);
       cbType.setValue(receptionWorkRecover.getType());
       cbState.setValue(receptionWorkRecover.getActualState());
       taDescription.setText("Descripción: " + receptionWorkRecover.getDescription());
           try {
               initializeColaborators();
               getStudents();
               getLGACS();
           } catch (BusinessException ex) {
               Log.logException(ex);
           }
       
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
                receptionWorkNew.setKey(receptionWorkRecover.getKey());
                receptionWorkNew.setTitle(title);
                receptionWorkNew.setDescription(description);
                receptionWorkNew.setDateStart(startDate);
                receptionWorkNew.setDateEnd(endDate);
                receptionWorkNew.setType(type);
                receptionWorkNew.setPreliminarProject(preliminarProject);
                receptionWorkNew.setActualState(state);
                receptionWorkNew.setKeyGroupAcademic(receptionWorkRecover.getKeyGroupAcademic());
                updateReceptionWork ();                
            }else{  
                sendAlert();
            }
        }else{
           sendAlert();
        }
    }
    
      private void updateReceptionWork (){   
        ReceptionWorkDAO receptionWorkDAO =  new ReceptionWorkDAO();
        try{  
            deleteColaborators();
            deleteStudents();
           if(receptionWorkDAO.updatedSucessful(receptionWorkRecover.getKey(), receptionWorkNew)){  
               receptionWorkNew.setKey(receptionWorkDAO.getId(receptionWorkNew));
               saveColaborators();
               recoverStudents();
               AlertMessage alertMessage = new AlertMessage();
               alertMessage.showUpdateMessage();
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
    
    
    
    @FXML 
    private void actionExit(ActionEvent actionEvent) throws BusinessException{  
        Stage stage = (Stage) btExit.getScene().getWindow();
        stage.close();
        openShowView();
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
   
     private boolean validateFieldEmpty(){ 
          boolean value=false;
          if(tfTitle.getText().isEmpty() 
           || taDescription.getText().isEmpty()  || dpStartDate == null 
            || dpEndDate==null  
           ){
              value=true;
          }
          return value;
    }
     
    private boolean validateInformationField(){ 
         boolean value=true;
        Validation validation=new Validation();
        if(validation.findInvalidField(tfTitle.getText())
        || validation.findInvalidField(taDescription.getText())  ){   
            value=false;
        }  
        return value;
    }
    
    
    private void openShowView() throws BusinessException{
        try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/ReceptionWorkShow.fxml").toURI().toURL();
        try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
                ReceptionWorkShowController receptionWorkShowController =loader.getController();
                ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
                ReceptionWork receptionWorkAuxiliar = receptionWorkDAO.getReceptionWorkById(receptionWorkRecover.getKey());
                receptionWorkShowController.setReceptionWork(receptionWorkAuxiliar);
                receptionWorkShowController.initializeReceptionWork();
                Parent root = loader.getRoot();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
           }catch (IOException ex) {
                    Log.logException(ex);
            }
            primaryStage.show();
       } catch (MalformedURLException ex) {
                Log.logException(ex);
        }
    }
    
    
    
    
    private void getStudents() throws BusinessException{
        ReceptionWorkDAO receptionWorkDAO =new ReceptionWorkDAO();
         receptionWorkRecover.setStudents(receptionWorkDAO.getStudents(receptionWorkRecover.getKey()));
         ArrayList<Student> students= receptionWorkRecover.getStudents();
         int i=0;
        int numberStudent=0;
        int numberRows=2;
        GridPane gridPane= new GridPane();
        gridPane.setHgap (5);
        gridPane.setVgap (5);
        if(students.size()> 0){
            while (i < ( students.size() * numberRows)){ 
                    TextField tfEnrollmentStudent = new TextField( students.get(numberStudent).getEnrollment());
                    TextField tfNameStudent = new TextField(students.get(numberStudent).getName());
                    gridPane.add(tfEnrollmentStudent,1,i);
                    gridPane.add(tfNameStudent,1, (i + 1));
                    i=i+2;
                    numberStudent++;
            }
            paneStudent.getChildren().add(gridPane);
        }
    }
    
    
      private void getLGACS() throws BusinessException{
        ReceptionWorkDAO receptionWorkDAO =new ReceptionWorkDAO();
         receptionWorkRecover.setLGACs(receptionWorkDAO.getLGACs(receptionWorkRecover.getKey()));
         ArrayList<LGAC> lgacs= receptionWorkRecover.getLGACs();
         int i=0;
         int indexGridPane=1;
        GridPane gridPane= new GridPane();
        gridPane.setHgap (2);
        gridPane.setVgap (2);
        gridPane.add(new Label("LGACs relacionadas: "),1,0);

        if(lgacs.size()> 0){
            while (i <lgacs.size()){ 
                    CheckBox checkBoxLGAC = new CheckBox(lgacs.get(i).getName());
                    gridPane.add(checkBoxLGAC,1,indexGridPane);
                    i++;
                    indexGridPane++;
                    
            }
            lgacsPane.getChildren().add(gridPane);
        }
    }
      
      
    private boolean deleteColaborators() throws BusinessException{  
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        ArrayList<Member> colaborators = receptionWorkDAO.getColaborators(receptionWorkRecover.getKey());
       receptionWorkRecover.setMembers(colaborators);
       return  receptionWorkDAO.deletedSucessfulColaborators(receptionWorkRecover);
    }
    
    private boolean deleteStudents() throws BusinessException{  
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        ArrayList<Student> students = receptionWorkDAO.getStudents(receptionWorkRecover.getKey());
        receptionWorkRecover.setStudents(students);
    
        return receptionWorkDAO.deletedSucessfulColaborators(receptionWorkRecover);
    }
    
     private boolean saveColaborators(){    
        boolean value=false;
       ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
       MemberDAO memberDAO = new MemberDAO();
       ArrayList<Member> members = new ArrayList<Member>();
        try {
            Member director=(Member) cbDirector.getSelectionModel().getSelectedItem();
            director.setRole("Director");
            receptionWorkNew.addMember(director);
            for(int i=0; i < codirectors.size(); i++){   
               receptionWorkNew.addMember(codirectors.get(i));
            }
            receptionWorkDAO.addedSucessfulColaborators(receptionWorkNew);
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
    
    public void setPreliminarProjects(ObservableList<PreliminarProject> preliminarProjects) {  
         for( int i = 0; i<preliminarProjects.size(); i++) {
                  this.preliminarProjects.add(preliminarProjects.get(i));
            }
        cbPreliminarProject.getSelectionModel().selectFirst();

    }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
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
            preliminarProjects = FXCollections.observableArrayList();
            cbPreliminarProject.setItems(preliminarProjects);
            cbPreliminarProject.getSelectionModel().selectFirst();
            tcCodirector.setCellValueFactory(new PropertyValueFactory<Member,String>("name"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dpStartDate.setConverter(new LocalDateStringConverter(formatter, null));
            dpEndDate.setConverter(new LocalDateStringConverter(formatter, null));
            members = FXCollections.observableArrayList();
            codirectors= FXCollections.observableArrayList();
            codirectorsNew= FXCollections.observableArrayList();
            initializeMembers();
            cbDirector.setItems(members);
            cbDirector.getSelectionModel().selectFirst();
            cbCodirectors.setItems(members);
            cbCodirectors.getSelectionModel().selectFirst();
            tvCodirectors.setItems(codirectorsNew);
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
                addlgacs();
            } catch (BusinessException ex) {
                Log.logException(ex);
            }
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
        indexCodirectors = codirectorsNew.indexOf(codirector);
            if(codirector != null){
                cbCodirectors.getSelectionModel().select(codirector);
            }
    }
    
     public boolean repeatedCodirector(Member codirector){
        Boolean value = false;
        int i = 0;
        while((value==false) && (i<codirectorsNew.size())){
            String enrollmentCodirector= codirectorsNew.get(i).getProfessionalLicense();
            if(enrollmentCodirector.equals(codirector.getProfessionalLicense())){
                value = true;
            }
            i++;
        }
       return value;
    }
     
    private void initializeMembers() {
        try {
            MemberDAO memberDAO = new MemberDAO();
            ArrayList <Member> memberList = new ArrayList<Member>();
            memberList = memberDAO.getMembers();
            for( int i = 0; i<memberList.size(); i++) {
                members.add(memberList.get(i));
            }
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
    private void addlgacs() throws BusinessException{    
        
         GridPane gridPane= new GridPane();
         GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO ();
            gridPane.setHgap (2);
            gridPane.setVgap (2);
            int i=0;
            gridPane.add(new Label ("Selecciona LGAC relacionadas: "),1,0);
            int indexGridPane=1;
            ArrayList <LGAC> lgacs = groupAcademicDAO.getLGACs("JDOEIJ804");
           while (i < lgacs.size()){  
                CheckBox checkBox = new CheckBox(lgacs.get(i).getName());
                gridPane.add(checkBox,1,indexGridPane);
                i++;
                indexGridPane++;
           }  
    }
    
    
     private void initializeColaborators() throws BusinessException{  
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        receptionWorkRecover.setMembers(receptionWorkDAO.getColaborators(receptionWorkRecover.getKey()));
        ArrayList<Member> members = receptionWorkRecover.getMembers();
        int i=0;
        while(i< members.size()){  
            if(members.get(i).getRole().equals("Director")){
                cbDirector.setValue(members.get(i));
            }else{  
                codirectors.add(members.get(i));
                codirectorsNew.add(members.get(i));
            }
            
            i++;
        }
        
    }
    
    private void recoverStudents() throws BusinessException{   
        GridPane gridPane= (GridPane) paneStudent.getChildren().get(0);
        ArrayList<Student> students = new ArrayList<Student>();
            int i=1;
            
            int sizeRows=3;
           while (i < (sizeRows * students.size())){
               TextField enrollment = (TextField) getNodeFromGridPane( gridPane, 1, i);
               TextField name = (TextField) getNodeFromGridPane( gridPane, 1, (i + 1));
               i=i+3;
               if(validateFieldsStudent(enrollment,name)){         
                 String enrollmentStudent= enrollment.getText();
                 String nameStudent= name.getText(); 
                 Student student = new Student(enrollmentStudent,nameStudent);
                 students.add(student);
                 saveStudent(student);
               }
           }
           receptionWorkNew.setStudents(students);
           addStudentsInReceptionWork();
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
    
    private void addStudentsInReceptionWork(){ 
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        try {
           receptionWorkDAO.addedSucessfulStudents(receptionWorkNew);
        } catch (BusinessException ex) {
            if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage= new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
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
          if(!validateDates()){
            alertMessage.showAlertValidateFailed("La fecha de fin debe ser mayor a la de inicio");

          }
          
          
      }
    
}
