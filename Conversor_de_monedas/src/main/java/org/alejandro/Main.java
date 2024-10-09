package org.alejandro;

import Exceptions.EntradaDeDatosInvalidaException;
import Exceptions.LecturaDeArchivoException;
import Exceptions.ParseJsonException;
import Implementations.ApiExchangeImpl;
import Implementations.ConfiguracionImpl;
import Implementations.EjecutarImpl;
import Services.ApiExchange;
import Services.ConfiguracionService;
import Services.Ejecutar;
import Settings.Configuracion;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in).useDelimiter("\n");
        try{
            Configuracion configuracion = new Configuracion();
            ConfiguracionService configuracionService = new ConfiguracionImpl(configuracion);
            ApiExchange apiExchange = new ApiExchangeImpl(configuracionService);
            Ejecutar ejecutar = new EjecutarImpl(entrada, apiExchange);
            ejecutar.iniciarConversion();
        } catch (EntradaDeDatosInvalidaException e) {
            System.out.println(e.getMessage());;
        }finally {
            entrada.close();
        }
    }
}