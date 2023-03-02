package calculadora;

import Modelo.Calculos;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class SecondaryController implements Initializable{

    private ObservableList<String> SisConversion = FXCollections.observableArrayList("Moneda", "Longitud");
    private ObservableList<String> Monedas = FXCollections.observableArrayList("EUR", "USD", "GBP", "YPJ");
    private ObservableList<String> Longitud = FXCollections.observableArrayList("mm", "dm", "cm", "m", "km");
    private boolean flagResultado = false;
    private boolean flagDecimal = false;
    private String seleccion = "";

    @FXML
    private ComboBox comboSistemas;
    @FXML
    private Label resultado;
    @FXML
    private ComboBox comboDestino;
    @FXML
    private ComboBox comboOrigen;

    Calculos calculos;

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
        comboOrigen.setValue("EUR");
        comboDestino.setItems(Monedas);
        comboDestino.setValue("USD");
        calculos = new Calculos();
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
            if (resultado.getText().isEmpty()) {
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

    @FXML
    private void onSelectItem(ActionEvent event) {
        //Falta corregir que no este vacio el comboBox
        this.seleccion = comboSistemas.getValue().toString();
        if (!seleccion.isEmpty()) {
            if (seleccion.equals("Moneda")) {
                comboOrigen.setItems(Monedas);
                comboDestino.setItems(Monedas);
            }
            if (seleccion.equals("Longitud")) {
                comboOrigen.setItems(Longitud);
                comboDestino.setItems(Longitud);
            }
        }
    }

    @FXML
    private void igual(ActionEvent event) {
        String aux = resultado.getText();
        seleccion = comboSistemas.getValue().toString();
        if (!aux.isEmpty()) {
            String origen = comboOrigen.getValue().toString();
            String destino = comboDestino.getValue().toString();

            String res = calculos.conversion(seleccion, aux, origen, destino);
            resultado.setText(res);
        }
    }
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}