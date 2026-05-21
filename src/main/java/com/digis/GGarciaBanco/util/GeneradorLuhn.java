package com.digis.GGarciaBanco.util;

import com.digis.GGarciaBanco.dto.Result;
import java.util.Random;

public class GeneradorLuhn {

    public static Result generarTarjeta(String prefijo, int longitud) {
        if (prefijo == null || !prefijo.matches("\\d+")) {
            return Result.error("ERR_PREFIJO", "El prefijo solo contiene numeros");
        }
        if (longitud < 12 || longitud > 19) {
            return Result.error("ERR_LONGITUD", "La longitud estándar de una tarjeta debe estar entre 12 y 19 dígitos.");
        }

        if (prefijo.length() >= longitud) {
            return Result.error("ERR_LOGICA", "El prefijo no puede ser mayor o igual a la longitud total requerida.");
        }

        try {
            Random random = new Random();
            StringBuilder numeroParcial = new StringBuilder(prefijo);

            while (numeroParcial.length() < longitud - 1) {
                numeroParcial.append(random.nextInt(10));
            }

            int digitoControl = calcularDigitoControl(numeroParcial.toString());

            numeroParcial.append(digitoControl);

            return Result.ok(numeroParcial.toString());
        } catch (Exception e) {
            return Result.error("ERR_INTERNO", "Ocurrió un error inesperado al generar la tarjeta.", e);
        }
    }

    private static int calcularDigitoControl(String numero) {
        int suma = 0;
        boolean alternar = true;
        for (int i = numero.length(); i >= 0; i--) {
            int n = Character.getNumericValue(numero.charAt(i));
            if (alternar) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            suma += n;
            alternar = !alternar;
        }
        return (10 - (suma % 10)) % 10;
    }

    public static Result generarPorBanco(BancoBIN banco) {
        if (banco == null) {
            return Result.error("ERR_BANCO_NULL", "Se debe de especificar el banco");
        }
        return generarTarjeta(banco.getBin(), banco.getLongitd());
    }
}
