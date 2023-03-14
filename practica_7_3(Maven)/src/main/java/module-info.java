module calculadora {
    requires javafx.controls;
    requires javafx.fxml;

    opens Controlador to javafx.fxml;
    exports Controlador;
    requires org.json;
    requires javafx.graphics;
    requires java.base;
}
