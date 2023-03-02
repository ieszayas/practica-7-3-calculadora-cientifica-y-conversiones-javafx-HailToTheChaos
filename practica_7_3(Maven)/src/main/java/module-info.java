module calculadora {
    requires javafx.controls;
    requires javafx.fxml;

    opens calculadora to javafx.fxml;
    exports calculadora;
    requires org.json;
}
