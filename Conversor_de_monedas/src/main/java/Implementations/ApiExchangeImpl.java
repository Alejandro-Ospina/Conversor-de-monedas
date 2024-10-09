package Implementations;

import Entities.MonedaDTO;
import Exceptions.LecturaDeArchivoException;
import Exceptions.ParseJsonException;
import Services.ApiExchange;
import Services.ConfiguracionService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class ApiExchangeImpl implements ApiExchange {

    private final ConfiguracionService configuracion;

    @Override
    public String convertirCantidad(String codigoMonedaBase, String codigoMonedaConvertida, String cantidadConvertir)
            throws LecturaDeArchivoException, ParseJsonException {
        //Se crea una instancia que captura la hora actual de en la que el usuario realiza la conversión
        String horaConsulta = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"));

        //Solicitud get a la API parametrizada
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(configuracion.cargarConfiguracion().getDominio()
                        + configuracion.cargarConfiguracion().getClave()
                        + "/pair/"
                        + codigoMonedaBase
                        + "/"
                        + codigoMonedaConvertida
                        + "/"
                        + cantidadConvertir))
                .build();

        //Se intenta parsear la respuesta json a un string
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

            //Se deserailiza el json y se guarda el parámetro conversion_result
            MonedaDTO monedaDTO = gson.fromJson(json, MonedaDTO.class);

            //Se crea nueva instancia para grabar el objeto con la fecha de consulta, util para grabar registro
            MonedaDTO monedaDTOFecha = monedaDTO.withHoraRegistro(horaConsulta);

            //Se retorna el string con la información de conversión
            return  "______________________________________________________________\n"
                    + "["
                    + monedaDTOFecha.horaRegistro()
                    + "]: "
                    + cantidadConvertir
                    + " "
                    + codigoMonedaBase
                    + " equivale a "
                    + monedaDTOFecha.conversion_result()
                    + " "
                    + codigoMonedaConvertida;

        } catch (IOException | InterruptedException | NullPointerException e) {
            throw new ParseJsonException("No se pudo obtener el json debido a un error de parseo. Verifique " +
                    "su datos e intente de nuevo.", e);
        }
    }
}