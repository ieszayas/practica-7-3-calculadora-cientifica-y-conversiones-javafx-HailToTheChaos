package Modelo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import javafx.collections.ObservableList;
import org.json.JSONObject;

/**
 *
 * @author Jaime
 */
public class Calculos {

    private HashMap<String, Double> unidadesLongitud;
    HashMap<String, Double> unidadesTiempo;
    private String memoria = "0";
    private String memoriaExp;
    private ModeloHistorial historial;

    public Calculos() {
        unidadesLongitud = new HashMap<>();
        unidadesLongitud.put("mm", 0.001);
        unidadesLongitud.put("cm", 0.01);
        unidadesLongitud.put("dm", 0.1);
        unidadesLongitud.put("dm", 0.1);
        unidadesLongitud.put("m", 1.0);
        unidadesLongitud.put("km", 1000.0);

        unidadesTiempo = new HashMap<>();
        unidadesTiempo.put("milisegundos", 1.0);
        unidadesTiempo.put("segundos", 1000.0);
        unidadesTiempo.put("minutos", 60000.0);
        unidadesTiempo.put("horas", 3600000.0);
        unidadesTiempo.put("dias", 86400000.0);
        unidadesTiempo.put("semanas", 604800000.0);
        unidadesTiempo.put("años", 31557600000.0);

        historial = new ModeloHistorial();
    }

    public String calculo(String numero1, String operacion) {        
        Double num1;
        Double numMemoria;
        String res;
        try {
            num1 = Double.valueOf(numero1);
            numMemoria = Double.valueOf(memoria);

            Double resultado;

            switch (operacion) {
                case "+":
                    resultado = numMemoria + num1;
                    break;
                case "-":
                    resultado = numMemoria - num1;
                    break;
                case "*":
                    resultado = numMemoria * num1;
                    break;
                case "/":
                    resultado = numMemoria / num1;
                    break;
                default:
                    resultado = 0.0;
                    break;
            }
            res = String.valueOf(resultado);

            historial.guardarHistorial(memoria, numero1, operacion, res);
            memoria = res;
        } catch (NumberFormatException ex) {

        }
        return memoria;
    }

    public String calculoTrig(String numero, String operacion) {
        Double num = Double.valueOf(numero);

        Double resultado = 0.0;

        switch (operacion) {
            case "sen":
                resultado = Math.sin(num);
                break;
            case "cos":
                resultado = Math.cos(num);
                break;
            case "tan":
                resultado = Math.tan(num);
                break;
        }
        return memoria = String.valueOf(resultado);
    }

    public String calcularPorcentaje(String numero) {
        Double num1 = Double.valueOf(numero);
        return String.valueOf(num1 / 100);
    }

    public boolean isNumeric(String entrada) {
        try {
            Integer.valueOf(entrada);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String conversion(String modo, String valor, String origen, String destino) {
        String resultado = "";

        switch (modo) {
            case "Moneda":
                resultado = calcularDivisa(valor, origen, destino);
                break;
            case "Longitud":
                resultado = conversion(valor, origen, destino, unidadesLongitud);
                break;
            case "Tiempo":
                resultado = conversion(valor, origen, destino, unidadesTiempo);
                break;
        }

        historial.guardarHistorial(modo, valor, origen, destino, resultado);
        return resultado;
    }

    public String getMemoria() {
        return memoria;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

    /**
     * Método para calcular las divisas
     *
     * @param valor
     * @param origen
     * @param destino
     * @return resultado
     */
    private String calcularDivisa(String valor, String origen, String destino) {

        String json = requestAPI(origen);
        Double valorDestino = divisa(json, destino);
        Double valorACalcular = Double.valueOf(valor);

        return String.valueOf(valorACalcular * valorDestino);
    }

    /**
     * Método que obtiene el valor de la divisa de destino
     *
     * @param json
     * @param destino
     * @return solucion
     */
    public Double divisa(String json, String destino) {
        JSONObject obj = new JSONObject(json);
        String result = obj.getJSONObject("conversion_rates").optString(destino);
        double solucion = Double.parseDouble(result);

        return solucion;
    }

    /**
     * Método que hace la request a la API de exchangerate (cambio de divisa)
     *
     * @param origen
     * @return json
     */
    private String requestAPI(String origen) {
        String json = "";
        Scanner sc = null;
        String enlace = "https://v6.exchangerate-api.com/v6/893432d92dd618a412aff045/latest/";

        try {
            URL url = new URL(enlace + origen);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int code = conn.getResponseCode();

            if (code != 200) {
                System.out.println("Request no exitosa");
            } else {
                sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    json += sc.nextLine();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            sc.close();
        }
        return json;
    }

    public void setMemoriaExp(String memoriaEx) {
        memoriaExp = memoriaEx;
    }

    /**
     * Método que hace el cálculo de exponenciales
     *
     * @param exponente
     * @return resulatdo
     */
    public String calculoExp(String exponente) {
        Double num1 = Double.valueOf(memoriaExp);
        Double num2 = Double.valueOf(exponente);

        return String.valueOf(Math.pow(num1, num2));
    }

    /**
     * Método que hace las conversiones
     *
     * @param valor
     * @param origen
     * @param destino
     * @param unidades
     * @return resultado
     */
    private String conversion(String valor, String origen, String destino, HashMap<String, Double> unidades) {
        Double valorEntrada = Double.valueOf(valor);
        Double factorOrigen = unidades.get(origen);
        Double factorDestino = unidades.get(destino);

        // Convertir la cantidad a milisegundos y luego a la unidad de destino
        return String.valueOf((valorEntrada * factorOrigen) / factorDestino);
    }

    public void borrarHistorial() {
        historial.borrarHistorial();
    }

    public ObservableList<String> getHistorial(String tipo) {
        return historial.getHistorial(tipo);
    }

}
