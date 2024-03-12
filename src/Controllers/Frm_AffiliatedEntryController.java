/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;
import mainproject.tbl_AffiliatedEntry;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author user
 */
public class Frm_AffiliatedEntryController implements Initializable {
    
    @FXML
    private TextField companyID;
    @FXML
    private TextField companyName;
    @FXML
    private Button close;
    @FXML
    private TableView<tbl_AffiliatedEntry> table;
    @FXML
    private TableColumn<tbl_AffiliatedEntry, String> row;
    @FXML
    private TableColumn<tbl_AffiliatedEntry, String> affiliatedCompany;
    @FXML
    private CheckBox status;
    @FXML
    private Button add;
    @FXML
    private TableColumn<tbl_AffiliatedEntry, String> status_col;
    
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
        public void onChanged(Change change) {
            change.next();
            if (change.wasReplaced() && !suspended) {
                this.suspended = true;
                table.getColumns().setAll(row, affiliatedCompany,status_col);
                this.suspended = false;
            }
        }
    });
      
      
        
        save.setDisable(true);
        
        row.setSortable(false);
        affiliatedCompany.setSortable(false);
        status_col.setSortable(false);
        
        status_col.setVisible(false);
        row.setCellValueFactory(new PropertyValueFactory<tbl_AffiliatedEntry, String>("r1"));
        affiliatedCompany.setCellValueFactory(new PropertyValueFactory<tbl_AffiliatedEntry, String>("r2"));
        status_col.setCellValueFactory(new PropertyValueFactory<tbl_AffiliatedEntry, String>("r3"));
        companyID.setText(String.valueOf(id));
    }    

    @FXML
    private void btnClose(ActionEvent event) {
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
    }

    @FXML
    private void btnAdd(ActionEvent event) {
        table.getSelectionModel().clearSelection();
        
        id= Integer.parseInt(companyID.getText());
        try{
            if(companyID.getText().length()==0 || companyName.getText().length()==0 ){
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
            
        System.out.println("insert into 'Affiliated' ('companyID', 'companyName','status') values ("+companyID.getText()+","+companyName.getText()+","+stat+" ) ");
            
        tbl_AffiliatedEntry tbl_EntryForm = new tbl_AffiliatedEntry(companyID.getText(),companyName.getText(),Boolean.toString(stat));
        ObservableList<tbl_AffiliatedEntry> tbl_EntryForms = table.getItems();
        tbl_EntryForms.add(tbl_EntryForm);
        table.setItems(tbl_EntryForms);

             id+=1;
             companyID.setText(String.valueOf(id));
             companyName.setText("");
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
        
            if(companyID.getText().length()==0 || companyName.getText().length()==0 ){
           Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill all the required fields", ButtonType.OK);
          alert.showAndWait();
            }else{
           ObservableList<tbl_AffiliatedEntry> currentTableData = table.getItems();
        int selectedID = table.getSelectionModel().getSelectedIndex();
        
        
            boolean stat=false;
            if (status.isSelected()) {
                    stat=true;
            } 

            
            for(tbl_AffiliatedEntry tbl_EntryForm : currentTableData){
            if(Integer.parseInt(tbl_EntryForm.getR1())==selectedID){
                tbl_EntryForm.setR2(companyName.getText());
                tbl_EntryForm.setR3(Boolean.toString(stat));
                System.out.println("Check...."+Boolean.toString(stat));
                table.refresh();
                break;
            }

        }
        //SQL STATEMENT
        System.out.println("UPDATE 'Affiliated' SET companyName='"+companyName.getText()+"', status='"+Boolean.toString(stat)+"' WHERE companyID='"+companyID.getText()+"'");

             companyID.setText(String.valueOf(id));
             companyName.setText("");
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
        companyID.setText(row.getCellData(selectedID).toString());
        companyName.setText(affiliatedCompany.getCellData(selectedID).toString());
        status.setSelected(Boolean.valueOf(status_col.getCellData(selectedID).toString()));
       
    }

    @FXML
    private void btnCancel(ActionEvent event) {
         table.getSelectionModel().clearSelection();
        
                add.setDisable(false);
                save.setDisable(true);
             companyID.setText(String.valueOf(id));
             companyName.setText("");
             status.setSelected(false);
    }

    @FXML
    private void btnDeactivate(ActionEvent event) {
//        table.setSelectionModel(null);
       
        
       ObservableList<tbl_AffiliatedEntry> currentTableData = table.getItems();
        int selectedID = table.getSelectionModel().getSelectedIndex();
        
        if(selectedID <=-1){
            return;
        }else{
                  for(tbl_AffiliatedEntry tbl_EntryForm : currentTableData){
            if(Integer.parseInt(tbl_EntryForm.getR1())==selectedID){
                tbl_EntryForm.setR2(affiliatedCompany.getCellData(selectedID).toString());
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
        System.out.println("UPDATE 'Affiliated' SET companyName='"+companyName.getText()+"', status='"+"false"+"' WHERE companyID='"+selectedID+"'");     
        }
    }

 

    @FXML
    private void handleRowSelect(MouseEvent event) {
//   table.getSelectionModel().clearSelection();
        
     companyID.setText(String.valueOf(id));
     companyName.setText("");
     status.setSelected(false);
    add.setDisable(false);
    save.setDisable(true);
    
}
    
}