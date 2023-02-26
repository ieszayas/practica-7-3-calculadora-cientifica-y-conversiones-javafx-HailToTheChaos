package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.Calculos;
import static modelo.Practica_7_3_calculadora_cientifica.getStg;

/**
 * FXML Controller class
 *
 * @author Jaime
 */
public class FXMLConversionesController implements Initializable {

    private ObservableList<String> SisConversion = FXCollections.observableArrayList("Moneda", "Tiempo");
    private ObservableList<String> Monedas = FXCollections.observableArrayList("Euro", "Dolar","Libra","Yen");
    private boolean flagResultado = false;
    private boolean flagDecimal = false;

    @FXML
    private ComboBox comboSistemas;

    @FXML
    private Label resultado;
    @FXML
    private ComboBox comboDestino;
    @FXML
    private ComboBox comboOrigen;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboSistemas.setValue("Moneda");
        comboSistemas.setItems(SisConversion);
        
        comboOrigen.setItems(Monedas);
        comboDestino.setItems(Monedas);
        
    }

    @FXML
    private void cambiarEscena(ActionEvent event) {
        Parent pane;
        try {
            pane = FXMLLoader.load(getClass().getResource("/vista/FXMLConversiones.fxml"));

            Scene scene = new Scene(pane);
            getStg().setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(FXMLContrCientifica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void pulsarBoton(ActionEvent event) {
        if (flagResultado) {
            borrarPantalla();
        }
        String valor = ((Button) event.getSource()).getText();
        mostrarPorPantalla(valor, false);
    }

    private void mostrarPorPantalla(String valor, boolean flag) {
        resultado.setText(resultado.getText() + valor);
        flagResultado = flag;
    }

    private void borrarPantalla() {
        resultado.setText("");
    }

    @FXML
    private void ponerDecimal(ActionEvent event) {
        if (flagDecimal == false) {
            if (resultado.getText().isBlank()) {
                mostrarPorPantalla("0.", false);
            } else {
                mostrarPorPantalla(".", false);
            }
            flagDecimal = true;
        }
    }

    @FXML
    private void clearAll(ActionEvent event) {
        borrarPantalla();
        Calculos.setMemoria("0");

        flagDecimal = false;
    }

    @FXML
    private void clear(ActionEvent event) {
        String datos = resultado.getText();
        String nDatos = datos.substring(0, datos.length() - 1);

        if (!nDatos.contains(".")) {
            flagDecimal = false;
        }
        resultado.setText(nDatos);
    }

}
