
package Controlador;

import Modelo.Calculos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Jaime
 */
public class HistorialController implements Initializable {

    @FXML
    private ListView<String> lista;
    
    private Calculos calculos;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    /**
     * Método para establecer el historial
     * @param calculos
     * @param tipo 
     */
    public void setCalculos(Calculos calculos, String tipo){
        this.calculos = calculos;        
        lista.setItems(calculos.getHistorial(tipo));
    }

    /**
     * Método para borrar el historial
     * @param event 
     */
    @FXML
    private void borrarHistorial(ActionEvent event) {
        calculos.borrarHistorial();
    }
    
}
