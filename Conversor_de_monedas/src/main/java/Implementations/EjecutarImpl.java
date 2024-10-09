package Implementations;

import Entities.Monedas;
import Exceptions.EntradaDeDatosInvalidaException;
import Exceptions.LecturaDeArchivoException;
import Exceptions.ParseJsonException;
import Services.ApiExchange;
import Services.Ejecutar;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class EjecutarImpl implements Ejecutar {

    private final Scanner entrada;
    private final ApiExchange apiExchange;

    @Override
    public void iniciarConversion() {
        System.out.println("A continuación se presenta la lista de monedas\n");
        Map<Integer, String> listaCodigos= new HashMap<Integer,String>();
        String salida;
        Stream.of(Monedas.values()).forEach(monedas -> {
            System.out.println((monedas.ordinal()+1) + ". " + monedas.toString());
            listaCodigos.put(monedas.ordinal()+1, monedas.name());
        });
        while(true){
            try{
                //Se piden los datos de entrada
                System.out.print("\nIngresa el número de la moneda base: ");
                int codigoBase = entrada.nextInt();
                System.out.print("Ingresa el número de la moneda a la que desea convertir: ");
                int codigoParaConvertir = entrada.nextInt();
                System.out.print("Ingrese la cantidad de dinero a convertir: ");
                double dinero = entrada.nextDouble();

                //Comprobamos si estamos dentro de los rangos
                if(codigoBase < 1
                        || codigoParaConvertir < 1
                        || codigoBase > Monedas.values().length
                        || codigoParaConvertir > Monedas.values().length){
                    throw new EntradaDeDatosInvalidaException("Uno de los numeros ingresados excede o no está en el límite." +
                            " Intente de nuevo");
                }

                //Se obtiene el valor de la clave de la coleccion map, y se hace el parseo a string del dinero
                String base = listaCodigos.get(codigoBase);
                String cambio = listaCodigos.get(codigoParaConvertir);
                String dineroTotal = Double.toString(dinero);

                //Se imprime por pantalla el resultado final
                System.out.println(apiExchange.convertirCantidad(base,cambio,dineroTotal));
            }catch (EntradaDeDatosInvalidaException | ParseJsonException | LecturaDeArchivoException e){
                System.err.println(e.getMessage());
            }catch (NumberFormatException | InputMismatchException e) {
                System.err.println("Formato de numero inválido. Intente de nuevo");
            }

            //Comprobamos si el usuario desea continuar
            System.out.print("\nDesea Salir? [Digite la letra Y. En caso contrario, digite cualquier tecla para continuar]: ");
            entrada.nextLine(); //Se agrega entrada.nextLine() por el buffer de lectura del escaner en la siguiente línea que termina en \n
            salida = entrada.nextLine();
            if(salida.equalsIgnoreCase("y")) break;
        }
    }
}