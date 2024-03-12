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
import javafx.scene.control.Alert.AlertType;
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
public class MadeEntryFormController implements Initializable {

    @FXML
    private Button addBtn;
    @FXML
    private TextField madeId;
    @FXML
    private TextField madeName;
    @FXML
    private TableColumn<FormName, Integer> columnRow;
    @FXML
    private TableColumn<FormName, String> columnMadeName;
    @FXML
    private TableView<FormName> tableView;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    public int row = 0;
    @FXML
    private Button closeBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnRow.setCellValueFactory(new PropertyValueFactory<>("Row"));
        columnMadeName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    }

    public void newMadeName() {
        if ("".equals(madeName.getText())) {
            Stage stage = new Stage();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert Message");
            alert.setHeaderText("Please fill up Made Name");
            alert.setContentText("");

            if (alert.showAndWait().get() == ButtonType.OK) {
                System.out.println("success exit");
                stage.close();
            }
        } else {
            FormName newEntry = new FormName(row, madeName.getText());
            newEntry.setRow(row);
            tableView.getItems().add(newEntry);
            System.out.println(madeName.getText());
            row++;
        }
    }

    @FXML
    private void handleAddBtn(ActionEvent event) {
        newMadeName();
        madeId.clear();
        madeName.clear();
    }

    @FXML
    private void handleCloseBtn(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("alert Dialog");
        alert.setHeaderText("");
        alert.setContentText("are you sure you want to close");
     
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();
        }

    }

}
