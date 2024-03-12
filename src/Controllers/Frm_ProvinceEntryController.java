/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;
import mainproject.tbl_ProvinceEntry;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class Frm_ProvinceEntryController implements Initializable {
    
    @FXML
    private TextField provinceID;
    @FXML
    private TextField provinceName;
    @FXML
    private Button close;
    @FXML
    private TableView<tbl_ProvinceEntry> table;
    @FXML
    private TableColumn<tbl_ProvinceEntry, String> row;
    @FXML
    private TableColumn<tbl_ProvinceEntry, String> province_Name;
    @FXML
    private CheckBox status;
    @FXML
    private Button add;
    @FXML
    private TableColumn<tbl_ProvinceEntry, String> status_col;
    
    public int id=0;
    @FXML
    private Button save;

 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
      table.getColumns().addListener(new ListChangeListener() {
        public boolean suspended;
        @Override
        public void onChanged(ListChangeListener.Change change) {
            change.next();
            if (change.wasReplaced() && !suspended) {
                this.suspended = true;
                table.getColumns().setAll(row, province_Name,status_col);
                this.suspended = false;
            }
        }
    });
        
        status_col.setVisible(false);
        row.setCellValueFactory(new PropertyValueFactory<tbl_ProvinceEntry, String>("r1"));
        province_Name.setCellValueFactory(new PropertyValueFactory<tbl_ProvinceEntry, String>("r2"));
        status_col.setCellValueFactory(new PropertyValueFactory<tbl_ProvinceEntry, String>("r3"));
        provinceID.setText(String.valueOf(id));
    }    

    @FXML
    private void btnClose(ActionEvent event) {
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
    }

    @FXML
    private void btnAdd(ActionEvent event) {
        table.getSelectionModel().clearSelection();

        id= Integer.parseInt(provinceID.getText());
        try{
            if(provinceID.getText().length()==0 || provinceName.getText().length()==0 ){
           Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill all the required fields", ButtonType.OK);
          alert.showAndWait();
            }else{
                boolean stat=false;
                if (status.isSelected()) {
    //                    System.out.println("Active: "+"True\n");
                        stat=true;
                } else {
    //                    System.out.println("Active: "+"False\n");
                }
          
            
            
        System.out.println("insert into 'Province' ('provinceID', 'provinceName','status') values ("+provinceID.getText()+","+provinceName.getText()+","+stat+" ) ");
            
        tbl_ProvinceEntry tbl_EntryForm = new tbl_ProvinceEntry(provinceID.getText(),provinceName.getText(),Boolean.toString(stat));
        ObservableList<tbl_ProvinceEntry> tbl_EntryForms = table.getItems();
        tbl_EntryForms.add(tbl_EntryForm);
        table.setItems(tbl_EntryForms);


             id+=1;
             provinceID.setText(String.valueOf(id));
             provinceName.setText("");
             status.setSelected(false);
             
            }
   
        }catch(Exception e){
            System.out.println(e);
        }

    }

    @FXML
    private void btnSave(ActionEvent event) {
            table.getSelectionModel().clearSelection();
        
            add.setDisable(false);
            save.setDisable(true);
        
            if(provinceID.getText().length()==0 || provinceName.getText().length()==0 ){
           Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill all the required fields", ButtonType.OK);
          alert.showAndWait();
            }else{
           ObservableList<tbl_ProvinceEntry> currentTableData = table.getItems();
        int selectedID = table.getSelectionModel().getSelectedIndex();
        
        
            boolean stat=false;
            if (status.isSelected()) {
                    stat=true;
            } 
        
        
        
        for(tbl_ProvinceEntry tbl_EntryForm : currentTableData){
            if(Integer.parseInt(tbl_EntryForm.getR1())==selectedID){
                tbl_EntryForm.setR2(provinceName.getText());
                tbl_EntryForm.setR3(Boolean.toString(stat));
                System.out.println("Check...."+Boolean.toString(stat));
                table.refresh();
                break;
            }

        }
        //SQL STATEMENT
        System.out.println("UPDATE 'Province' SET provinceName='"+provinceName.getText()+"', status='"+Boolean.toString(stat)+"' WHERE provinceID='"+provinceID.getText()+"'");

             provinceID.setText(String.valueOf(id));
             provinceName.setText("");
             status.setSelected(false);
            }
            

    }

    @FXML
    private void btnEdit(ActionEvent event) {
         
        
        int selectedID = table.getSelectionModel().getSelectedIndex();
        if(selectedID <=-1){
            return;
        }else{
            add.setDisable(true);
            save.setDisable(false);
        }
        provinceID.setText(row.getCellData(selectedID).toString());
        provinceName.setText(province_Name.getCellData(selectedID).toString());
        status.setSelected(Boolean.valueOf(status_col.getCellData(selectedID).toString()));
        
        
       
    }

    @FXML
    private void btnCancel(ActionEvent event) {
        table.getSelectionModel().clearSelection();
             add.setDisable(false);
            save.setDisable(true);
             provinceID.setText(String.valueOf(id));
             provinceName.setText("");
             status.setSelected(false);
    }

    @FXML
    private void btnDeactivate(ActionEvent event) {
        
       ObservableList<tbl_ProvinceEntry> currentTableData = table.getItems();
        int selectedID = table.getSelectionModel().getSelectedIndex();
        if(selectedID <=-1){
            return;
        }else{
           for(tbl_ProvinceEntry tbl_EntryForm : currentTableData){
            if(Integer.parseInt(tbl_EntryForm.getR1())==selectedID){
                tbl_EntryForm.setR2(province_Name.getCellData(selectedID).toString());
                if(status_col.getCellData(selectedID).toString()=="true"){
                   tbl_EntryForm.setR3("false");
                   status.setSelected(false);
                }else{
//                   tbl_EntryForm.setR4("true");
//                   status.setSelected(true);
                }
                table.refresh();
                break;
            }
            

          }
       //SQL STATEMENT
        System.out.println("UPDATE 'Province' SET provinceName='"+provinceName.getText()+"', status='"+"false"+"' WHERE provinceID='"+selectedID+"'");      
        }
 
        

    }

 

    @FXML
    private void handleRowSelect(MouseEvent event) {
     provinceID.setText(String.valueOf(id));
     provinceName.setText("");
     status.setSelected(false);
    add.setDisable(false);
 save.setDisable(true);
    }
    
}
