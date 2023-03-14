package Controlador;

import Modelo.Calculos;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SecondaryController implements Initializable {

    private final ObservableList<String> SisConversion = FXCollections.observableArrayList("Moneda", "Longitud", "Tiempo");
    private final ObservableList<String> Monedas = FXCollections.observableArrayList("EUR", "USD", "GBP", "YPJ");
    private final ObservableList<String> Longitud = FXCollections.observableArrayList("mm", "dm", "cm", "m", "km");
    private final ObservableList<String> Tiempo = FXCollections.observableArrayList("milisegundos", "segundos", "minutos", "horas", "dias", "semanas", "años");
    private boolean flagResultado = false;
    private boolean flagDecimal = false;
    private String seleccion = "";
    private Calculos calculos;
    private HistorialController hc;
    private OpcionesDivisaController odc;

    @FXML
    private ComboBox comboSistemas;
    @FXML
    private Label resultado;
    @FXML
    private ComboBox comboDestino;
    @FXML
    private ComboBox comboOrigen;

    @FXML
    private ProgressIndicator progreso;

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

    /**
     * Poner valores
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
     * Mostrar input
     * @param valor
     * @param flag 
     */
    private void mostrarPorPantalla(String valor, boolean flag) {
        resultado.setText(resultado.getText() + valor);
        flagResultado = flag;
    }

    /**
     * Borra el label
     */
    private void borrarPantalla() {
        resultado.setText("");
    }

    /**
     * pone decimales
     * @param event 
     */
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

    /**
     * Quita todos los datos
     * @param event 
     */
    @FXML
    private void clearAll(ActionEvent event) {
        borrarPantalla();
        calculos.setMemoria("0");

        flagDecimal = false;
    }

    /**
     * 
     * @param event 
     */
    @FXML
    private void clear(ActionEvent event) {
        String datos = resultado.getText();
        String nDatos = datos.substring(0, datos.length() - 1);

        if (!nDatos.contains(".")) {
            flagDecimal = false;
        }
        resultado.setText(nDatos);
    }

    /**
     * Selector de conversiones
     * @param event 
     */
    @FXML
    private void onSelectItem(ActionEvent event) {
        //Falta corregir que no este vacio el comboBox
        this.seleccion = comboSistemas.getValue().toString();
        if (!seleccion.isEmpty()) {
            switch (seleccion) {
                case "Moneda":
                    comboOrigen.setItems(Monedas);
                    comboOrigen.setValue("EUR");
                    comboDestino.setItems(Monedas);
                    comboDestino.setValue("USD");
                    break;
                case "Longitud":
                    comboOrigen.setItems(Longitud);
                    comboOrigen.setValue("m");
                    comboDestino.setItems(Longitud);
                    comboDestino.setValue("km");
                    break;
                case "Tiempo":
                    comboOrigen.setItems(Tiempo);
                    comboOrigen.setValue("segundos");
                    comboDestino.setItems(Tiempo);
                    comboDestino.setValue("horas");
                    break;
            }
        }
    }

    /**
     * Muestra resultado
     * @param event 
     */
    @FXML
    private void igual(ActionEvent event) {
        progreso.setVisible(true);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String aux = resultado.getText();
            seleccion = comboSistemas.getValue().toString();
            if (!aux.isEmpty()) {
                String origen = comboOrigen.getValue().toString();
                String destino = comboDestino.getValue().toString();

                String res = calculos.conversion(seleccion, aux, origen, destino);
                Platform.runLater(() -> {
                    resultado.setText(res);
                    progreso.setVisible(false);
                });
            }
            progreso.setVisible(false);
        });
        executor.shutdown();
    }

    /**
     * Cambio de escena
     * @throws IOException 
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("/Vista/primary");
    }

    /**
     * Abre el historial
     * @param event 
     */
    @FXML
    private void abrirHistorial(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Historial.fxml"));
            Pane root = (Pane) loader.load();
            hc = loader.getController();
            hc.setCalculos(calculos, "conversion");
            Stage stage = new Stage();
            stage.setTitle("Historial");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Abre opciones de divisa
     * @param event 
     */
    @FXML
    private void abrirOpcionesDiv(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/opcionesDivisa.fxml"));
            Pane root = (Pane) loader.load();
            this.odc = loader.getController();            
            Stage stage = new Stage();
            stage.setTitle("Opcines de divisa");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            Monedas.clear();
            Monedas.addAll(odc.getDatos());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
