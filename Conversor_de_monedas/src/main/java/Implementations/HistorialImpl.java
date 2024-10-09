package Implementations;

import Exceptions.LecturaDeArchivoException;
import Services.Historial;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HistorialImpl implements Historial {

    @Override
    public void guardarHistorialConsulta(String conversion) throws LecturaDeArchivoException {
        //Se hace un try with resources para crear aun archivo txt de historial
        try(FileWriter crearArchivo = new FileWriter("src/main/resources/historial_conversiones.txt", true)){
            crearArchivo.write(conversion + System.lineSeparator());
        }catch(IOException e){
            throw new LecturaDeArchivoException("Hubo problemas para crear o rastrear el archivo historial_conversiones.txt", e);
        }
    }

    @Override
    public void abrirHistorialConsulta() throws LecturaDeArchivoException {
        //Se hace un try with resources para intentar abrir el historial e imprimirlo en la consola
        try(BufferedReader leerHistorial =
                    new BufferedReader(new FileReader("src/main/resources/historial_conversiones.txt"))){
            String linea;
            while((linea = leerHistorial.readLine()) != null){
                System.out.println(linea);
            }
        } catch (IOException e) {
            throw new LecturaDeArchivoException("Hubo problemas para abrir el archivo historial_conversiones.txt", e);
        }
    }
}
