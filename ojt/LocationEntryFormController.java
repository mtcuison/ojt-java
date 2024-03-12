/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ojt;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class LocationEntryFormController implements Initializable {

    @FXML
    private Button addBtn;
    @FXML
    private Button closeBtn;
    @FXML
    private TextField locationId;
    @FXML
    private TextField locationDesc;
    @FXML
    private TextField locationBriefDesc;
    @FXML
    private TableView<FormName> LocationTable;
    @FXML
    private TableColumn<FormName, Integer> rowCol;
    @FXML
    private TableColumn<FormName, String> locationDescCol;

    public int row = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        rowCol.setCellValueFactory(new PropertyValueFactory<>("Row"));
        locationDescCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
    }

    public void loadLocation(){
        if ("".equals(locationId.getText()) || "".equals(locationDesc.getText()) || "".equals(locationBriefDesc)) {
            Stage stage = new Stage();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert Message");
            alert.setHeaderText("Please fill up blank Field");
            alert.setContentText("");

            if (alert.showAndWait().get() == ButtonType.OK) {
                System.out.println("success exit");
                stage.close();
            }
        } else {
            FormName newEntry = new FormName(row, locationDesc.getText());
            newEntry.setRow(row);
            LocationTable.getItems().add(newEntry);
            System.out.println(locationDesc.getText());
            row++;
        }
    }
    @FXML
    private void handleAddBtn(ActionEvent event) {
        loadLocation();
        locationId.clear();
        locationDesc.clear();
        locationBriefDesc.clear();

    }
    @FXML
    private void handleCloseBtn(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("alert Dialog");
        alert.setHeaderText("");
        alert.setContentText("are you sure you want to close");

        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();
        }
    }

}
