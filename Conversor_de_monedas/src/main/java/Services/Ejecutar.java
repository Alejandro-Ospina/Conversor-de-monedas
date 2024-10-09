package Services;


import Exceptions.EntradaDeDatosInvalidaException;
import Exceptions.LecturaDeArchivoException;

public interface Ejecutar {

    /**
     * Este método se encarga de invocar la lógica de conversión de monedas por medio de inyección de dependencias.
     * @throws EntradaDeDatosInvalidaException si se detecta que un formato de entrada de datos no concuerda
     */
    void iniciarConversion() throws EntradaDeDatosInvalidaException, LecturaDeArchivoException;
}