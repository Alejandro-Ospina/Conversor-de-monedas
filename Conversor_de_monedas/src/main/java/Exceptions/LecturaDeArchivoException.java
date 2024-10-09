package Exceptions;

import lombok.Getter;

@Getter
public class LecturaDeArchivoException extends Exception{

    public LecturaDeArchivoException(String message) {
        super(message);
    }

    public LecturaDeArchivoException(String message, Throwable cause) {
        super(message, cause);
    }
}