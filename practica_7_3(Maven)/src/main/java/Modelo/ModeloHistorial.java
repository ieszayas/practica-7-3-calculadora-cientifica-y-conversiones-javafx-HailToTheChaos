
package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jaime
 */
public class ModeloHistorial {

    private ObservableList<String> historial;

    /**
     * Constructor del modelo historial
     */
    public ModeloHistorial() {
        //Inicializo el observable list
        historial = FXCollections.observableArrayList();

        BufferedReader br = null;
        File fichero = null;
        String salida = "";
        try {
            //Obtengo el fichero
            fichero = new File("\\historial.txt");
            //Lo creo si no existe (para que no salte ninguna excepcion)
            fichero.createNewFile();
            br = new BufferedReader(new FileReader(fichero));
            //Lo leo y lo añado a la lista
            while ((salida = br.readLine()) != null) {
                historial.add(salida);
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    //Cierro el buffered reader
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Método para guardar el historial de la calculadora cientifica
     * @param item1
     * @param item2
     * @param operacion
     * @param resultado 
     */
    public void guardarHistorial(String item1, String item2, String operacion, String resultado) {
        BufferedWriter bf = null;
        File fichero = null;
        String salida = "";
        try {
            fichero = new File("\\historial.txt");
            if (!fichero.exists()) {
                fichero.createNewFile();
                //Oculto el fichero
                Files.setAttribute(Paths.get(fichero.toURI()), "dos:hidden", true);
            }

            bf = new BufferedWriter(new FileWriter(fichero, true));
            //Meto el historial
            salida = item1 + operacion + item2 + " = " + resultado;
            historial.add(salida);
            //Añado salto de linea
            bf.write(salida + "\n");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Mismo método que el anterior pero con salida distinta
     * @param modo
     * @param valor
     * @param origen
     * @param destino
     * @param resultado 
     */
    public void guardarHistorial(String modo, String valor, String origen, String destino, String resultado) {
        BufferedWriter bf = null;
        File fichero = null;
        String salida = "";
        try {
            fichero = new File("\\historial.txt");
            if (!fichero.exists()) {
                fichero.createNewFile();
                Files.setAttribute(Paths.get(fichero.toURI()), "dos:hidden", true);
            }

            bf = new BufferedWriter(new FileWriter(fichero, true));

            salida = modo + " -> " + valor + " " + origen + " a " + destino + " = " + resultado;
            historial.add(salida);
            bf.write(salida + "\n");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Para obtener el historial
     * @param tipo
     * @return 
     */
    public ObservableList<String> getHistorial(String tipo) {
        return historial;
    }

    /**
     * Borra el fichero del historial
     */
    public void borrarHistorial() {
        historial.clear();
        File fichero = new File("\\historial.txt");
        if (fichero.exists()) {
            fichero.delete();
        }
    }

}
