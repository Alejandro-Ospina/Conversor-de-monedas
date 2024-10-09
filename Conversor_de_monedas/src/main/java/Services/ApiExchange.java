package Services;

import Exceptions.LecturaDeArchivoException;
import Exceptions.ParseJsonException;

public interface ApiExchange {

    /**
     * El método devuelve un string con la información de conversión de moneda en tiempo real
     * @param codigoMonedaBase corresponde al parámetro de moneda que se quiere convertir
     * @param codigoMonedaConvertida formato final de moneda
     * @param cantidadConvertir numero de dinero a convertir
     * @return retorna un String con la información de conversión
     * @throws LecturaDeArchivoException si el archivo de application properties no está cargado o no se encuentran parámetros
     * @throws ParseJsonException si no se hace un parseo correcto del json, o se encuentra un campo nulo
     */
    String convertirCantidad (String codigoMonedaBase, String codigoMonedaConvertida, String cantidadConvertir)
            throws LecturaDeArchivoException, ParseJsonException;

}