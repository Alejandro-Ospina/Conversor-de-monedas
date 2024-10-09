package Services;

import Exceptions.LecturaDeArchivoException;
import Settings.Configuracion;

/**
 * La interfaz invoca la lógica para cargar el token de Exchange
 */
public interface ConfiguracionService {

    /**
     * El método se encarga de cargar la clave y el dominio desde el archivo application.properties
     * @return configuracion si encuentra la clave
     * @throws LecturaDeArchivoException si no encuentra el archivo o el parámetro dentro del archivo
     */
    Configuracion cargarConfiguracion() throws LecturaDeArchivoException;

}