package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modelo.Calculos;
import static modelo.Practica_7_3_calculadora_cientifica.getStg;

/**
 *
 * @author Jaime
 */
public class FXMLContrCientifica implements Initializable {

    @FXML
    private Label resultado;

    private String num1 = "0";
    private String operacion;
    private String memoria = "0";

    private boolean flagDecimal = false;
    private boolean flagResultado = false;
    private boolean flagOpInicial = true;

    @FXML
    private void pulsarBoton(ActionEvent event) {
        if (flagResultado) {
            borrarPantalla();
        }
        String valor = ((Button) event.getSource()).getText();
        mostrarPorPantalla(valor, false);
    }

    @FXML
    private void pulsarDecimal(ActionEvent event) {
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
    private void cambiarSigno(ActionEvent event) {
        String aux = resultado.getText();
        if (flagResultado) {
            borrarPantalla();
        }

        if (aux.contains("-")) {
            resultado.setText(aux.substring(1));
        } else {
            resultado.setText("-" + resultado.getText());
        }
    }

    @FXML
    private void operacion(ActionEvent event) {
        flagDecimal = false;
        if (flagResultado) {
            borrarPantalla();
        }
        num1 = resultado.getText();
        if (flagOpInicial) {
            operacion = ((Button) event.getSource()).getText();
            Calculos.setMemoria(num1);
            borrarPantalla();
            flagOpInicial = false;
        } else {
            num1 = resultado.getText();
            borrarPantalla();
            mostrarPorPantalla(Calculos.calculo(num1, operacion), true);
            operacion = ((Button) event.getSource()).getText();
        }
    }

    @FXML
    private void memoria(ActionEvent event) {
        String boton = ((Button) event.getSource()).getText();
        String numero = resultado.getText();

        switch (boton) {
            case "M+" ->
                memoria = Calculos.calculo(numero, "+");
            case "M-" ->
                memoria = Calculos.calculo(numero, "-");
        }

        resultado.setText(memoria);
        flagResultado = true;
    }

    @FXML
    private void clearAll(ActionEvent event) {
        borrarPantalla();
        Calculos.setMemoria("0");
        memoria = "0";
        flagDecimal = false;
        flagOpInicial = true;
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
    private void igual(ActionEvent event) {
        num1 = resultado.getText();
        borrarPantalla();
        mostrarPorPantalla(Calculos.calculo(num1, operacion), true);
        flagResultado = true;
    }

    @FXML
    private void porcentaje(ActionEvent event) {
        resultado.setText(Calculos.calcularPorcentaje(resultado.getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void mostrarPorPantalla(String valor, boolean flag) {
        resultado.setText(resultado.getText() + valor);
        flagResultado = flag;
    }

    private void borrarPantalla() {
        resultado.setText("");
        flagResultado = false;
    }

    private void escribir(KeyEvent event) {
        String datos = resultado.getText();
        if (flagResultado) {
            borrarPantalla();
        }
        String valor = event.getCharacter();
        if (Calculos.isNumeric(valor)) {
            mostrarPorPantalla(valor, false);
        } else if (event.getCode() == KeyCode.BACK_SPACE) {
            resultado.setText(datos.substring(1));
        }
    }

    @FXML
    private void operacionTrig(ActionEvent event) {
        String numero = resultado.getText();
        String operacion = ((Button) event.getSource()).getText();
        borrarPantalla();
        mostrarPorPantalla(Calculos.calculoTrig(numero, operacion), true);
    }

    @FXML
    private void exponencial(ActionEvent event) {
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

}
