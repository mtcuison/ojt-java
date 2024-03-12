/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;
import mainproject.tbl_MeasureEntry;
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
public class Frm_MeasureEntryController implements Initializable {
    @FXML
    private TextField measureID;
    @FXML
    private TextField measureName;
    @FXML
    private Button close;
    @FXML
    private TableView<tbl_MeasureEntry> table;
    @FXML
    private TableColumn<tbl_MeasureEntry, String> row;
    @FXML
    private TableColumn<tbl_MeasureEntry, String> measure_Name;

    @FXML
    private TableColumn<tbl_MeasureEntry, String> status_col;
    
    @FXML
    private CheckBox status;
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
                table.getColumns().setAll(row, measure_Name,status_col);
                this.suspended = false;
            }
        }
    });
      
      
        status_col.setVisible(false);
        row.setCellValueFactory(new PropertyValueFactory<tbl_MeasureEntry, String>("r1"));
        measure_Name.setCellValueFactory(new PropertyValueFactory<tbl_MeasureEntry, String>("r2"));
        status_col.setCellValueFactory(new PropertyValueFactory<tbl_MeasureEntry, String>("r3"));
        measureID.setText(String.valueOf(id));
    }    

    @FXML
    private void btnClose(ActionEvent event) {
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
    }

    @FXML
    private void btnAdd(ActionEvent event) {
         table.getSelectionModel().clearSelection();
        id= Integer.parseInt(measureID.getText());
        try{
            if(measureID.getText().length()==0 || measureName.getText().length()==0 ){
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
          
            
            
        System.out.println("insert into 'Measure' ('measureID', 'measureName','status') values ("+measureID.getText()+","+measureName.getText()+","+stat+" ) ");
            
        tbl_MeasureEntry tbl_MeasureEntry = new tbl_MeasureEntry(measureID.getText(),measureName.getText(),Boolean.toString(stat));
        ObservableList<tbl_MeasureEntry> tbl_MeasureEntrys = table.getItems();
        tbl_MeasureEntrys.add(tbl_MeasureEntry);
        table.setItems(tbl_MeasureEntrys);


             id+=1;
             measureID.setText(String.valueOf(id));
             measureName.setText("");
             status.setSelected(false);
             
            }
   
        }catch(Exception e){
            System.out.println(e);
        }

    }

    @FXML
    private void btnSave(ActionEvent event) {
                add.setDisable(false);
                save.setDisable(true);

        
            if(measureID.getText().length()==0 || measureName.getText().length()==0 ){
           Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill all the required fields", ButtonType.OK);
          alert.showAndWait();
            }else{
           ObservableList<tbl_MeasureEntry> currentTableData = table.getItems();
        int selectedID = table.getSelectionModel().getSelectedIndex();
        
        
            boolean stat=false;
            if (status.isSelected()) {
                    stat=true;
            } 
        
        
        
        for(tbl_MeasureEntry tbl_EntryForm : currentTableData){
            if(Integer.parseInt(tbl_EntryForm.getR1())==selectedID){
                tbl_EntryForm.setR2(measureName.getText());
                tbl_EntryForm.setR3(Boolean.toString(stat));
                System.out.println("Check...."+Boolean.toString(stat));
                table.refresh();
                break;
            }

        }
        //SQL STATEMENT
        System.out.println("UPDATE 'Measure' SET measureName='"+measureName.getText()+"', status='"+Boolean.toString(stat)+"' WHERE measureID='"+measureID.getText()+"'");

             measureID.setText(String.valueOf(id));
             measureName.setText("");
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
        measureID.setText(row.getCellData(selectedID).toString());
        measureName.setText(measure_Name.getCellData(selectedID).toString());
        status.setSelected(Boolean.valueOf(status_col.getCellData(selectedID).toString()));
        
        
       
    }

    @FXML
    private void btnCancel(ActionEvent event) {
              add.setDisable(false);
             save.setDisable(true);
             measureID.setText(String.valueOf(id));
             measureName.setText("");
             status.setSelected(false);
    }

    @FXML
    private void btnDeactivate(ActionEvent event) {
        
       ObservableList<tbl_MeasureEntry> currentTableData = table.getItems();
        int selectedID = table.getSelectionModel().getSelectedIndex();
                if(selectedID <=-1){
            return;
        }else{
            for(tbl_MeasureEntry tbl_EntryForm : currentTableData){
            if(Integer.parseInt(tbl_EntryForm.getR1())==selectedID){
                tbl_EntryForm.setR2(measure_Name.getCellData(selectedID).toString());
                if(status_col.getCellData(selectedID).toString()=="true"){
                   tbl_EntryForm.setR3("false");
                   status.setSelected(false);
                }else{
//                   tbl_MeasureEntry.setR4("true");
//                   status.setSelected(true);
                }
                table.refresh();
                break;
            }
          }
       //SQL STATEMENT
        System.out.println("UPDATE 'Measure' SET measureName='"+measureName.getText()+"', status='"+"false"+"' WHERE measureID='"+selectedID+"'");       
        }
 
        

    }

 

    @FXML
    private void handleRowSelect(MouseEvent event) {
     measureID.setText(String.valueOf(id));
     measureName.setText("");
     status.setSelected(false);
    add.setDisable(false);
    save.setDisable(true);
    }
    
}
