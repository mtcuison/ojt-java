/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mainproject.tbl_SizeEntry;

/**
 * FXML Controller class
 *
 * @author user
 */
public class Frm_SizeEntryFormController implements Initializable {



    
    @FXML
    private TextField sizeID;
    @FXML
    private TextField sizeName;
        
    @FXML
    private CheckBox status;
    
    @FXML
    private TableColumn<tbl_SizeEntry, String> size_Name;

    @FXML
    private TableView<tbl_SizeEntry> table;
    @FXML
    private TableColumn<tbl_SizeEntry, String> row;
    @FXML
    private TableColumn<tbl_SizeEntry, String> status_col;
    @FXML
    private Button close;
    @FXML
    private Button add;
    
    public int id=0;
    @FXML
    private Button save;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         save.setDisable(true);


      table.getColumns().addListener(new ListChangeListener() {
        public boolean suspended;
        @Override
        public void onChanged(ListChangeListener.Change change) {
            change.next();
            if (change.wasReplaced() && !suspended) {
                this.suspended = true;
                table.getColumns().setAll(row, size_Name,status_col);
                this.suspended = false;
            }
        }
    });
        
        
        status_col.setVisible(false);
        row.setCellValueFactory(new PropertyValueFactory<tbl_SizeEntry, String>("r1"));
        size_Name.setCellValueFactory(new PropertyValueFactory<tbl_SizeEntry, String>("r2"));
        status_col.setCellValueFactory(new PropertyValueFactory<tbl_SizeEntry, String>("r3"));
        sizeID.setText(String.valueOf(id));
    }    

    @FXML
    private void btnClose(ActionEvent event) {
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
    }

    @FXML
    private void btnAdd(ActionEvent event) {
        table.getSelectionModel().clearSelection();
        
        id= Integer.parseInt(sizeID.getText());
        try{
            if(sizeID.getText().length()==0 || sizeName.getText().length()==0 ){
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
          
            
            
        System.out.println("insert into 'Size' ('sizeID', 'sizeName','status') values ("+sizeID.getText()+","+sizeName.getText()+","+stat+" ) ");
            
        tbl_SizeEntry tbl_EntryForm = new tbl_SizeEntry(sizeID.getText(),sizeName.getText(),Boolean.toString(stat));
        ObservableList<tbl_SizeEntry> tbl_EntryForms = table.getItems();
        tbl_EntryForms.add(tbl_EntryForm);
        table.setItems(tbl_EntryForms);


             id+=1;
             sizeID.setText(String.valueOf(id));
             sizeName.setText("");
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
        
            if(sizeID.getText().length()==0 || sizeName.getText().length()==0 ){
           Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill all the required fields", ButtonType.OK);
          alert.showAndWait();
            }else{
           ObservableList<tbl_SizeEntry> currentTableData = table.getItems();
        int selectedID = table.getSelectionModel().getSelectedIndex();
        
        
            boolean stat=false;
            if (status.isSelected()) {
                    stat=true;
            } 
        
        
        
        for(tbl_SizeEntry tbl_EntryForm : currentTableData){
            if(Integer.parseInt(tbl_EntryForm.getR1())==selectedID){
                tbl_EntryForm.setR2(sizeName.getText());
                tbl_EntryForm.setR3(Boolean.toString(stat));
                System.out.println("Check...."+Boolean.toString(stat));
                table.refresh();
                break;
            }

        }
        //SQL STATEMENT
        System.out.println("UPDATE 'Size' SET sizeName='"+sizeName.getText()+"', status='"+Boolean.toString(stat)+"' WHERE sizeID='"+sizeID.getText()+"'");

             sizeID.setText(String.valueOf(id));
             sizeName.setText("");
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
        sizeID.setText(row.getCellData(selectedID).toString());
        sizeName.setText(size_Name.getCellData(selectedID).toString());
        status.setSelected(Boolean.valueOf(status_col.getCellData(selectedID).toString()));
        
        
       
    }

    @FXML
    private void btnCancel(ActionEvent event) {
        table.getSelectionModel().clearSelection();
        
                add.setDisable(false);
            save.setDisable(true);
             sizeID.setText(String.valueOf(id));
             sizeName.setText("");
             status.setSelected(false);
    }

    @FXML
    private void btnDeactivate(ActionEvent event) {
        
       ObservableList<tbl_SizeEntry> currentTableData = table.getItems();
        int selectedID = table.getSelectionModel().getSelectedIndex();
        
        if(selectedID <=-1){
            return;
        }else{
           for(tbl_SizeEntry tbl_EntryForm : currentTableData){
            if(Integer.parseInt(tbl_EntryForm.getR1())==selectedID){
                tbl_EntryForm.setR2(size_Name.getCellData(selectedID).toString());
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
        System.out.println("UPDATE 'Size' SET sizeName='"+sizeName.getText()+"', status='"+"false"+"' WHERE sizeID='"+selectedID+"'");
        }

    }

 

    @FXML
    private void handleRowSelect(MouseEvent event) {
     sizeID.setText(String.valueOf(id));
     sizeName.setText("");
     status.setSelected(false);
    add.setDisable(false);
    save.setDisable(true);
    }
}
