package Implementations;

import Exceptions.LecturaDeArchivoException;
import Services.ConfiguracionService;
import Settings.Configuracion;
import lombok.RequiredArgsConstructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@RequiredArgsConstructor
public class ConfiguracionImpl implements ConfiguracionService{

    private final Configuracion configuracion;

    @Override
    public Configuracion cargarConfiguracion() throws LecturaDeArchivoException {
        Properties propiedades = new Properties();
        try(InputStream entradaArchivo =
                    new FileInputStream("src/main/resources/application.properties")){
            propiedades.load(entradaArchivo);

            //aquí va el nombre de la clave configurado en el archivo application properties, y el dominio web
            String clave = propiedades.getProperty("exchange.api.key");
            String dominio = propiedades.getProperty("dominio.web");

            //Se comprueba si la clave está vacía
            if(clave == null || clave.trim().isEmpty() || dominio == null || dominio.trim().isEmpty()){
                throw new LecturaDeArchivoException("Error: Alguno de los parámetros del application properties " +
                        "no coincide o no existe. Verifique el archivo");
            }

            //Se setea la clave, el dominio; y se carga de manera segura la clave, sin exponerla dentro del código fuente
            configuracion.setClave(clave);
            configuracion.setDominio(dominio);
        }catch (IOException e){
            throw new LecturaDeArchivoException("El archivo no se encuentra en la ruta src/main/resources/");
        }
        return configuracion;
    }
}