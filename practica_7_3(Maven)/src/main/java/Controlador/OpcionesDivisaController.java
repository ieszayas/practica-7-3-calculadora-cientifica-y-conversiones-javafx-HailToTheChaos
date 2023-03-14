/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jaime
 */
public class OpcionesDivisaController implements Initializable {

    private static ObservableList<String> lista;

    @FXML
    private CheckBox ch_eur;
    @FXML
    private CheckBox ch_dol;
    @FXML
    private CheckBox ch_lib;
    @FXML
    private CheckBox ch_yen;
    @FXML
    private Button bt_ok;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public static ObservableList<String> getDatos() {
        return lista;
    }

    @FXML
    private void ok(ActionEvent event) {
        lista = FXCollections.observableArrayList();
        
        if (ch_eur.isSelected()) {
            lista.add("EUR");
        }
        if (ch_dol.isSelected()) {
            lista.add("USD");
        }
        if (ch_lib.isSelected()) {
            lista.add("GBP");
        }
        if (ch_yen.isSelected()) {
            lista.add("YJP");
        }

// Obtener el Stage de la ventana actual
        Stage stage = (Stage) bt_ok.getScene().getWindow();
// Cerrar la ventana
        stage.close();
    }

}
