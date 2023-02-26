
package modelo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jaime
 */
public class Practica_7_3_calculadora_cientifica extends Application {
    private static Stage stg;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/FXMLDocument_cientifica.fxml"));
        
        stg = stage;
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("vista/style.css");
        stg.setTitle("Calculadora científica");
        stg.setScene(scene);
        stg.show();
    }

    public static Stage getStg() {
        return stg;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
