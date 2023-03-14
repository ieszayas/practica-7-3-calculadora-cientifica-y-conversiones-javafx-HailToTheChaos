package Controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    /**
     * Método que inicia la aplicación
     * @param stage
     * @throws IOException 
     */
    @Override
    public void start(Stage stage) throws IOException {
        //Creamos la escena con el FXML principal
        scene = new Scene(loadFXML("/Vista/primary"));
        //Establecemos el icono en el stage
        stage.getIcons().add(new Image(App.class.getResource("/Vista/icon.png").toExternalForm()));
        //Establecemos la escena
        stage.setScene(scene);
        //Lo mostramos
        stage.show();
    }

    /**
     * Método que establece la raiz de un Stage
     * @param fxml
     * @throws IOException 
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Método que carga el FXML
     * @param fxml
     * @return fxmlLoader.load()
     * @throws IOException 
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Método main
     * @param args 
     */
    public static void main(String[] args) {
        launch();
    }

}
