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
public class RelationShipEntryFormController implements Initializable {

    @FXML
    private Button addBtn;
    @FXML
    private Button closeBtn;
    @FXML
    private TextField relationId;
    @FXML
    private TextField relationDesc;
    @FXML
    private TableView<FormName> relTable;
    @FXML
    private TableColumn<FormName, Integer> rowCol;
    @FXML
    private TableColumn<FormName, String> colRelDesc;

    public int row = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rowCol.setCellValueFactory(new PropertyValueFactory<>("Row"));
        colRelDesc.setCellValueFactory(new PropertyValueFactory<>("Name"));
    }

    public void loadRelation() {
        if ("".equals(relationId.getText()) || "".equals(relationDesc.getText())) {
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
            FormName newEntry = new FormName(row, relationDesc.getText());
            newEntry.setRow(row);
            relTable.getItems().add(newEntry);
            System.out.println(relationDesc.getText());
            row++;
        }
    }

    @FXML
    private void handleAddBtn(ActionEvent event) {
        loadRelation();
        relationId.clear();
        relationDesc.clear();
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
