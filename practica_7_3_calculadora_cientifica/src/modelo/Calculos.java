package modelo;

import java.util.Stack;

/**
 *
 * @author Jaime
 */
public class Calculos {

    private static String memoria = "0";

    public static String calculo(String numero1, String operacion) {
        try {
            Double num1 = Double.valueOf(numero1);
            Double numMemoria = Double.valueOf(memoria);

            Double resultado;

            switch (operacion) {
                case "+" ->
                    resultado = numMemoria + num1;
                case "-" ->
                    resultado = numMemoria - num1;
                case "*" ->
                    resultado = numMemoria * num1;
                case "/" ->
                    resultado = numMemoria / num1;
                default ->
                    resultado = 0.0;
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

    public static String getMemoria() {
        return memoria;
    }

    public static void setMemoria(String memoria) {
        Calculos.memoria = memoria;
    }

}
