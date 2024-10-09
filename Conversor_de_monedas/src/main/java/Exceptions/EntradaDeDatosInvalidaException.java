package Exceptions;

import lombok.Getter;

@Getter
public class EntradaDeDatosInvalidaException extends Exception{

    public EntradaDeDatosInvalidaException() {
        super();
    }

    public EntradaDeDatosInvalidaException(String message) {
        super(message);
    }
}
