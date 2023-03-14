
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

    public ModeloHistorial() {
        historial = FXCollections.observableArrayList();

        BufferedReader br = null;
        File fichero = null;
        String salida = "";
        try {
            fichero = new File("\\historial.txt");
            fichero.createNewFile();
            br = new BufferedReader(new FileReader(fichero));

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
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void guardarHistorial(String item1, String item2, String operacion, String resultado) {
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
            bf = new BufferedWriter(new FileWriter(fichero, true));

            salida = item1 + operacion + item2 + " = " + resultado;
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

    public ObservableList<String> getHistorial(String tipo) {
        return historial;
    }

    public void borrarHistorial() {
        historial.clear();
        File fichero = new File("\\historial.txt");
        if (fichero.exists()) {
            fichero.delete();
        }
    }

}
