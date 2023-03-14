package Controlador;

import Modelo.Calculos;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrimaryController implements Initializable {

    private Modelo.Calculos calculos;
    private HistorialController hc;

    @FXML
    private Label resultado;

    private String num1 = "0";
    private String operacion;
    private String memoria = "0";

    private boolean flagDecimal = false;
    private boolean flagResultado = false;
    private boolean flagOpInicial = true;
    private boolean flagExponencial = false;
    @FXML
    private Label operaciones;
    private boolean flagOperacion = false;

    /**
     * Evento que recoge el numero marcado y lo muestra por pantalla
     *
     * @param event
     */
    @FXML
    private void pulsarBoton(ActionEvent event) {
        if (flagResultado) {
            borrarPantalla();
        }
        String valor = ((Button) event.getSource()).getText();
        mostrarPorPantalla(valor, false);
    }

    /**
     * Poner decimales
     * @param event 
     */
    @FXML
    private void pulsarDecimal(ActionEvent event) {
        ponerDecimal();
    }
    
    /**
     * Poner decimales
     */
    private void ponerDecimal(){
        if (flagDecimal == false) {
            if (resultado.getText().isEmpty()) {
                mostrarPorPantalla("0.", false);
            } else {
                mostrarPorPantalla(".", false);
            }
            flagDecimal = true;
        }
    }

    /**
     * Cambio de signo
     * @param event 
     */
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

    /**
     * 
     * @param event 
     */
    @FXML
    private void operacion(ActionEvent event) {
        operar(((Button) event.getSource()).getText());
    }

    /**
     * Método para las operaciones
     * @param operacion 
     */
    private void operar(String operacion) {        
        flagDecimal = false;
        if (flagResultado) {
            borrarPantalla();
        }

        num1 = resultado.getText();

        if (flagOpInicial) {
            calculos.setMemoria(num1);
            borrarPantalla();
            flagOpInicial = false;
        } else {
            num1 = resultado.getText();
            borrarPantalla();
            mostrarPorPantalla(calculos.calculo(num1, this.operacion), true);
        }
        this.operacion = operacion;
        mostrarOperacion(num1 + operacion, false);
    }

    /**
     * 
     * @param event 
     */
    @FXML
    private void memoria(ActionEvent event) {
        String boton = ((Button) event.getSource()).getText();
        String numero = resultado.getText();

        switch (boton) {
            case "M+":
                memoria = calculos.calculo(numero, "+");
                break;
            case "M-":
                memoria = calculos.calculo(numero, "-");
                break;
        }
        mostrarPorPantalla(memoria, true);
    }

    /**
     * Borra todo
     */
    private void clearAll() {
        borrarPantalla();
        calculos.setMemoria("0");
        operaciones.setText("");
        memoria = "0";
        flagDecimal = false;
        flagOpInicial = true;
    }

    /**
     * 
     * @param event 
     */
    @FXML
    private void limpiar(ActionEvent event) {
        clear();
    }

    /**
     * Método para quitar digitos
     */
    private void clear() {
        String datos = resultado.getText();
        if (!datos.equals("")) {
            String nDatos = datos.substring(0, datos.length() - 1);
            if (!nDatos.contains(".")) {
                flagDecimal = false;
            }
            resultado.setText(nDatos);
        }
    }

    /**
     * 
     * @param event 
     */
    @FXML
    private void igual(ActionEvent event) {
        equal();
    }

    /**
     * Método que hace los cálculos despues de darle a igual
     */
    private void equal() {
        System.out.println(this.operacion);
        if (flagExponencial) {
            String res = calculos.calculoExp(resultado.getText());
            borrarPantalla();
            mostrarPorPantalla(res, true);
            flagExponencial = false;
        } else {
            num1 = resultado.getText();
            if (num1.equals("")) {
                num1 = "0";
            }
            borrarPantalla();            
            mostrarPorPantalla(calculos.calculo(num1, this.operacion), true);
            mostrarOperacion(num1, false);
        }
    }
    
    /**
     * Método para calcular el porcentaje
     * @param event 
     */
    @FXML
    private void porcentaje(ActionEvent event) {
        resultado.setText(calculos.calcularPorcentaje(resultado.getText()));
    }

    /**
     * Método que muestra los datos que le van entrando
     * @param valor
     * @param flag 
     */
    private void mostrarPorPantalla(String valor, boolean flag) {
        resultado.setText(resultado.getText() + valor);
        this.flagResultado = flag;
        
    }
    
    /**
     * Método que muestra las operaciones
     * @param valor
     * @param flag 
     */
    private void mostrarOperacion(String valor, boolean flag){
        operaciones.setText(operaciones.getText() + valor);        
        this.flagOperacion = flag;
    }

    /**
     * Método que borra el label resultado
     */
    private void borrarPantalla() {
        resultado.setText("");
        flagResultado = false;
        flagOperacion = false;
    }

    /**
     * Método para usar el teclado como input
     * @param event 
     */
    @FXML
    private void escribir(KeyEvent event) {
        if (flagResultado) {
            borrarPantalla();
        }

        switch (event.getCode()) {
            case DELETE:
                clearAll();
                break;
            case DECIMAL:
                ponerDecimal();
                break;
            case BACK_SPACE:
                clear();
                break;
            case ENTER:
                equal();
                break;
            case ADD:
                operar("+");
                break;
            case MINUS:
                operar("-");
                break;
            case MULTIPLY:
                operar("*");
                break;
            case DIVIDE:
                operar("/");
                break;
            default:
                String entrada = event.getText();
                if (calculos.isNumeric(entrada)) {
                    mostrarPorPantalla(entrada, false);
                }
        }

    }
    
    /**
     * Método para hacer operaciones trigonometricas
     * @param event 
     */
    @FXML
    private void operacionTrig(ActionEvent event) {
        String numero = resultado.getText();
        String operacion = ((Button) event.getSource()).getText();
        borrarPantalla();
        mostrarPorPantalla(calculos.calculoTrig(numero, operacion), true);
    }

    /**
     * Método para hacer calculos exponenciales
     * @param event 
     */
    @FXML
    private void exponencial(ActionEvent event) {
        flagExponencial = true;
        calculos.setMemoriaExp(resultado.getText());
        borrarPantalla();
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("/Vista/secondary");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        calculos = new Calculos();
    }

    /**
     * Muestra la ventana historial
     * 
     * @param event 
     */
    @FXML
    private void mostrarHistorial(ActionEvent event) {
        try {
            //Cargo el FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Historial.fxml"));
            Pane root = (Pane) loader.load();
            //Obtengo el controlador
            hc = loader.getController();
            hc.setCalculos(calculos, "cientifica");
            Stage stage = new Stage();
            //Le añado un título
            stage.setTitle("Historial");
            //Establezco la escena
            stage.setScene(new Scene(root));
            //Le añado modalidad
            stage.initModality(Modality.WINDOW_MODAL);
            //La muestro
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Borra el label de operaciones
     */
    private void borrarOperacion() {
        operaciones.setText("");
        flagOperacion = false;
    }

    @FXML
    private void limpiarTodo(ActionEvent event) {
        clearAll();
    }

}
