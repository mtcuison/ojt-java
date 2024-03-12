/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import mainproject.tbl_ColorDetails;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author use
 */
public class Frm_ColorDetailsController implements Initializable {

    @FXML
    private TextField color_id;
    @FXML
    private TextField main_color;
    @FXML
    private TextField color_details;
    @FXML
    private Button close;
    @FXML
    private Button save;
    @FXML
    private Button deactivate;
    @FXML
    private CheckBox status;
    public int id=0;
    @FXML
    private TableView<tbl_ColorDetails> table;

    //Columns
    @FXML
    private TableColumn<tbl_ColorDetails, String> row;
    @FXML
    private TableColumn<tbl_ColorDetails, String> mainColor;
    @FXML
    private TableColumn<tbl_ColorDetails, String> colorDetails;
    @FXML
    private Button add;
    @FXML
    private TableColumn<tbl_ColorDetails, String> status_col;
 
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         save.setDisable(true);
        
//       row.setReorderable(false);
//       mainColor.setReorderable(false);
//       colorDetails.setReorderable(false);
//       status_col.setReorderable(false);
        
        

      table.getColumns().addListener(new ListChangeListener() {
        public boolean suspended;
        @Override
        public void onChanged(ListChangeListener.Change change) {
            change.next();
            if (change.wasReplaced() && !suspended) {
                this.suspended = true;
                table.getColumns().setAll(row, mainColor,colorDetails,status_col);
                this.suspended = false;
            }
        }
    });

        
        status_col.setVisible(false);

        row.setCellValueFactory(new PropertyValueFactory<tbl_ColorDetails, String>("r1"));
        mainColor.setCellValueFactory(new PropertyValueFactory<tbl_ColorDetails, String>("r2"));
        colorDetails.setCellValueFactory(new PropertyValueFactory<tbl_ColorDetails, String>("r3"));
        status_col.setCellValueFactory(new PropertyValueFactory<tbl_ColorDetails, String>("r4"));
        color_id.setText(String.valueOf(id));
        
        
//         color_id.setOnKeyTyped(event ->{
//        int maxCharacters = 10;
//        if(color_id.getText().length() > maxCharacters) event.consume();
//    });
//           
//            UnaryOperator<TextFormatter.Change> integerFilter = change -> {
//            String newText = change.getControlNewText();
//            if (newText.matches("-?([1-9][0-9]*)?")) {
//                return change;
//            }
//            return null;
//        };
//
//        color_id.setTextFormatter(
//                new TextFormatter<Integer>(new IntegerStringConverter(), null, integerFilter));
        
     
           
    }    

    @FXML
    private void btnClose(ActionEvent event) {
        
    Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
    }

    @FXML
    private void btnAdd(ActionEvent event) {
        table.getSelectionModel().clearSelection();
      id= Integer.parseInt(color_id.getText());
        
        try{
            if(color_id.getText().length()==0 || main_color.getText().length()==0||color_details.getText().length()==0 ){
                Alert alert = new Alert(AlertType.WARNING, "Please fill all the required fields", ButtonType.OK);
                alert.showAndWait();
            }else{

                boolean stat=false;
                if (status.isSelected()) {
                stat=true;
                } 
            
                //SQL STATEMENT
                System.out.println("insert into 'Color' ('colorID', 'mainColor','colorDetails','status') values ("+color_id.getText()+","+main_color.getText()+","+color_details.getText()+","+stat+" ) ");

                //SAVE TO TABLE DATA (tbl_ColorDetails class)
                tbl_ColorDetails tbl_ColorDetails = new tbl_ColorDetails(color_id.getText(),main_color.getText(),color_details.getText(),String.valueOf(stat));
                ObservableList<tbl_ColorDetails> tbl_ColorDetailss = table.getItems();
                tbl_ColorDetailss.add(tbl_ColorDetails);
                table.setItems(tbl_ColorDetailss);


                //RESET TEXTFIELDS
                id+=1;
                color_id.setText(String.valueOf(id));
                main_color.setText("");
                color_details.setText("");
                status.setSelected(false);
             
            }
   
        }catch(Exception e){
            System.out.println(e);
        }

    
         }

    @FXML
    private void btnSave(ActionEvent event) {
         table.getSelectionModel().clearSelection();
       save.setDisable(true);
         add.setDisable(false);
         if(color_id.getText().length()==0 || main_color.getText().length()==0||color_details.getText().length()==0 ){
            Alert alert = new Alert(AlertType.WARNING, "Please fill all the required fields", ButtonType.OK);
            alert.showAndWait();
         }else{
            ObservableList<tbl_ColorDetails> currentTableData = table.getItems();
            int selectedID = table.getSelectionModel().getSelectedIndex();
        
            boolean stat=false;
            if (status.isSelected()) {
                    stat=true;
            } 
        
            for(tbl_ColorDetails tbl_ColorDetails : currentTableData){
                if(Integer.parseInt(tbl_ColorDetails.getR1())==selectedID){
            
                    tbl_ColorDetails.setR2(main_color.getText());
                    tbl_ColorDetails.setR3(color_details.getText());
                    tbl_ColorDetails.setR4(Boolean.toString(stat));
                    table.refresh();
                    break;
                }
              }
            
 
            //SQL STATEMENT
            System.out.println("UPDATE 'Color' SET mainColor='"+main_color.getText()+"', color_details='"+color_details.getText()+"' status='"+stat+"' WHERE colorID='"+color_id.getText()+"'");
                    
          

        
        
             //RESET TEXTFIELDS
             color_id.setText(String.valueOf(id));
             main_color.setText("");
             color_details.setText("");
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
        
        //get the status data 
        color_id.setText(row.getCellData(selectedID).toString());
        main_color.setText(mainColor.getCellData(selectedID).toString());
        color_details.setText(colorDetails.getCellData(selectedID).toString());
        status.setSelected(Boolean.valueOf(status_col.getCellData(selectedID).toString()));
    }

    @FXML
    private void btnCancel(ActionEvent event) {
         table.getSelectionModel().clearSelection();
         save.setDisable(true);
             add.setDisable(false);

             color_id.setText(String.valueOf(id));
             main_color.setText("");
             color_details.setText("");
             status.setSelected(false);
        
    }

    @FXML
    private void btnDeactivate(ActionEvent event) {
        
        
       ObservableList<tbl_ColorDetails> currentTableData = table.getItems();
       int selectedID = table.getSelectionModel().getSelectedIndex();
        
        
           if(selectedID <=-1){
            return;
        }else{
                 for(tbl_ColorDetails tbl_ColorDetails : currentTableData){
            if(Integer.parseInt(tbl_ColorDetails.getR1())==selectedID){
                tbl_ColorDetails.setR2(mainColor.getCellData(selectedID).toString());
                tbl_ColorDetails.setR3(colorDetails.getCellData(selectedID).toString());
                if(status_col.getCellData(selectedID).toString()=="true"){
                   tbl_ColorDetails.setR4("false");
                   status.setSelected(false);
                }else{
//                   tbl_ColorDetails.setR4("true");
//                   status.setSelected(true);
                }
                table.refresh();
                break;
            }
          }
           //SQL STATEMENT
           System.out.println("UPDATE 'Color' SET mainColor='"+main_color.getText()+"', color_details='"+color_details.getText()+" status='"+"false"+" WHERE colorID='"+selectedID+"'");      
           }
    }

    @FXML
    private void handleRowSelect(MouseEvent event) {
        
    //RESET TEXTFIELDS
    color_id.setText(String.valueOf(id));
    main_color.setText("");
    color_details.setText("");
    status.setSelected(false);
    add.setDisable(false);
     save.setDisable(true);
    }

   
  
}
