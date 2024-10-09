package Implementations;

import Entities.Monedas;
import Exceptions.EntradaDeDatosInvalidaException;
import Exceptions.LecturaDeArchivoException;
import Exceptions.ParseJsonException;
import Services.ApiExchange;
import Services.Ejecutar;
import Services.Historial;
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
    private final Historial historial;

    @Override
    public void iniciarConversion() throws LecturaDeArchivoException {
        System.out.println("A continuación se presenta la lista de monedas\n");

        Map<Integer, String> listaCodigos = inicializarListaDeMonedas();

        while (true) {
            try {
                // Se piden los datos de entrada
                System.out.print("\nIngresa el número de la moneda base: ");
                int codigoBase = entrada.nextInt();
                System.out.print("Ingresa el número de la moneda a la que desea convertir: ");
                int codigoParaConvertir = entrada.nextInt();
                System.out.print("Ingrese la cantidad de dinero a convertir: ");
                double dinero = entrada.nextDouble();

                // Comprobamos si estamos dentro de los rangos
                validarRangosDeMonedas(codigoBase, codigoParaConvertir);

                // Se obtiene el valor de la clave del mapa y se convierte el dinero a string
                String base = listaCodigos.get(codigoBase);
                String cambio = listaCodigos.get(codigoParaConvertir);
                String dineroTotal = Double.toString(dinero);

                // Se imprime el resultado final y se guarda en el historial
                String resultado = apiExchange.convertirCantidad(base, cambio, dineroTotal);
                System.out.println(resultado);
                historial.guardarHistorialConsulta(resultado);

            } catch (EntradaDeDatosInvalidaException | ParseJsonException | LecturaDeArchivoException e) {
                System.err.println(e.getMessage());
            } catch (NumberFormatException | InputMismatchException e) {
                System.err.println("Formato de número inválido. Intente de nuevo");
            }

            // Comprobamos si el usuario desea continuar
            if (deseaSalir()) break;
        }

        // Mostrar historial si el usuario lo solicita
        if (deseaVerHistorial()) {
            historial.abrirHistorialConsulta();
        }
    }

    private Map<Integer, String> inicializarListaDeMonedas() {
        Map<Integer, String> listaCodigos = new HashMap<>();
        Stream.of(Monedas.values()).forEach(monedas -> {
            System.out.println((monedas.ordinal() + 1) + ". " + monedas.toString());
            listaCodigos.put(monedas.ordinal() + 1, monedas.name());
        });
        return listaCodigos;
    }

    private void validarRangosDeMonedas(int codigoBase, int codigoParaConvertir) throws EntradaDeDatosInvalidaException {
        if (codigoBase < 1 || codigoParaConvertir < 1 ||
                codigoBase > Monedas.values().length || codigoParaConvertir > Monedas.values().length) {
            throw new EntradaDeDatosInvalidaException("Uno de los números ingresados excede o no está en el límite. Intente de nuevo");
        }
    }

    private boolean deseaSalir() {
        System.out.print("\nDesea salir? [Digite la letra Y para confirmar, cualquier otra tecla para continuar]: ");
        entrada.nextLine(); // Limpia el buffer
        String salida = entrada.nextLine();
        return salida.equalsIgnoreCase("y");
    }

    private boolean deseaVerHistorial() {
        System.out.print("\nDesea ver el historial? [Digite la letra Y para confirmar, cualquier otra tecla para continuar]: ");
        return entrada.next().equalsIgnoreCase("y");
    }

}