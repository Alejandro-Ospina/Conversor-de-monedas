package Services;

import Exceptions.LecturaDeArchivoException;

public interface Historial {

    void guardarHistorialConsulta(String conversion) throws LecturaDeArchivoException;

    void abrirHistorialConsulta() throws LecturaDeArchivoException;
}
