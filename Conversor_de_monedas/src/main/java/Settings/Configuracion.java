package Settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
/**
 * La clase en cuestión se usa para almacenar la clave o token de la API exchange
 */
public class Configuracion {
    private String clave;
    private String dominio;
}
