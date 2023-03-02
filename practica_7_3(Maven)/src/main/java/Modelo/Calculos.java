package Modelo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import org.json.JSONObject;

/**
 *
 * @author Jaime
 */
public class Calculos {

    private HashMap<String, Double> valoresLongitud;
    private static String memoria = "0";

    public Calculos() {
        valoresLongitud = new HashMap<>();
        valoresLongitud.put("mm", 0.001);
        valoresLongitud.put("cm", 0.01);
        valoresLongitud.put("dm", 0.1);
        valoresLongitud.put("dm", 0.1);
        valoresLongitud.put("m", 1.0);
        valoresLongitud.put("km", 1000.0);
    }

    public static String calculo(String numero1, String operacion) {
        try {
            Double num1 = Double.valueOf(numero1);
            Double numMemoria = Double.valueOf(memoria);

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

            memoria = String.valueOf(resultado);
        } catch (NumberFormatException ex) {

        }
        return memoria;
    }

    public static String calculoTrig(String numero, String operacion) {
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

    public static String calcularPorcentaje(String numero) {
        Double num1 = Double.valueOf(numero);
        return String.valueOf(num1 / 100);
    }

    public static boolean isNumeric(String entrada) {
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
                resultado = calcularLongitud(valor, origen, destino);
                break;
        }
        return resultado;
    }

    public static String getMemoria() {
        return memoria;
    }

    public static void setMemoria(String memoria) {
        Calculos.memoria = memoria;
    }

    private String calcularLongitud(String valor, String origen, String destino) {
        String resultado = "";
        try {
            Double num = Double.valueOf(valor);
            num = num * valoresLongitud.get(origen) / valoresLongitud.get(destino);
            resultado = String.valueOf(num);
        } catch (NumberFormatException ex) {

        }
        return resultado;
    }

    private String calcularDivisa(String valor, String origen, String destino) {
        String json = requestAPI(origen);
        Double valorDestino = divisa(json, destino);
        Double valorACalcular = Double.valueOf(valor);

        return String.valueOf(valorACalcular * valorDestino);
    }

    public Double divisa(String json, String destino) {
        JSONObject obj = new JSONObject(json);
        String result = obj.getJSONObject("conversion_rates").optString(destino);
        double solucion = Double.parseDouble(result);

        return solucion;
    }

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

}
