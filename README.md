# Conversor de monedas
A continuación, se presenta el código fuente del conversor de monedas (desafío backend de alura). Esta guía contiene una explicación sobre el funcionamiento del programa, configuración y aspectos relevantes del código en general.

## Índice
- [Tecnologías usadas](#tecnologias_usadas)
- [Configuración](#configuracion)
- [Paso a paso](#paso_paso)
- [Conclusión](#conclusion)

## Tecnologías usadas 
***
La lista de tecnologías usadas dentro del proyecto se muestran a continuación:
* [java](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html): Version 17
* [maven](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html): Version 4.0.0
* [lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok): Version RELEASE
* [gson](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.10.1): Version 2.10.1

## Configuración
***
Antes de explicar el proceso de configuración del programa, es importante entender por qué se usa maven y lombok como parte del desarrollo del proyecto.

- **Maven:** Es un gestor de dependencias y librerias que facilita La configuración de un proyecto a través de xml, facilitando el proceso de compilación de un software y su despliegue. Las dependencias y librerias se encuentran en el [repositorio maven](https://mvnrepository.com/).
- **Lombok:** Es una libreria de java que facilita y automatiza la generación de código para clases, como: getters, setters, constructores, entre otros; mediante anotaciones de clase, atributo y método. Con esta libreria se evita el uso de código boilerplate (repetitivo), garantizando simplicidad y fácil lectura de código.

Dado lo anterior, se procede con la explicación sobre la configuración del proyecto:
**Nota:** Es imperativo tener previamente un proyecto creado con maven desde el IDE respectivo.

### Configuración del archivo pom.xml (Instalación de dependencias y librerias)

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.alejandro</groupId>
    <artifactId>Conversor_de_monedas</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>RELEASE</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.10.1</version>
        </dependency>
    </dependencies>
</project>
```
### Definición del archivo application.properties dentro del proyecto
El archivo es bastante útil dentro de proyectos con maven ya que facilita la configuración de parámetros críticos como: 

- Urls de conexión a base de datos
- Credenciales de acceso
- Parámetros de conexión a APIs externas
- Entre otros

El propósito principal de este archivo es mantener buenas prácticas de seguridad al no exponer datos sensibles directamente en el código fuente. De esta manera, se centraliza la configuración en un solo lugar, lo que mejora la mantenibilidad y la seguridad del proyecto. Para este proyecto, el archivo application.properties se crea en la ruta ```src/main/resources``` y está configurado de la siguiente manera:

```
# Reemplazar clave aquí­ de forma segura, sin exponerla en el código fuente
exchange.api.key=
# Se anexa dominio web como parámetro
dominio.web=https://v6.exchangerate-api.com/v6/
```

Nótese que el parámetro ```exchange.api.key``` es el que contiene la información del access token de la API de intercambio (poner clave de acceso dada por la API); además, se crea un nuevo parámetro ```dominio.web``` para albergar la información del endpoint que se va a consumir en la consulta.

## Paso a paso
***
Una vez configurado el archivo pom.xml, es hora de copiar el repositorio y seguir los siguientes pasos:

### 1. Ejecutar la clase Main
La clase main se encuentra en la ruta ```src/main/java/org.alejandro```, y tiene el siguiente código:

```
package org.alejandro;

import Exceptions.EntradaDeDatosInvalidaException;
import Exceptions.LecturaDeArchivoException;
import Implementations.ApiExchangeImpl;
import Implementations.ConfiguracionImpl;
import Implementations.EjecutarImpl;
import Implementations.HistorialImpl;
import Services.ApiExchange;
import Services.ConfiguracionService;
import Services.Ejecutar;
import Services.Historial;
import Settings.Configuracion;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in).useDelimiter("\n");
        try{
            Configuracion configuracion = new Configuracion();
            ConfiguracionService configuracionService = new ConfiguracionImpl(configuracion);
            ApiExchange apiExchange = new ApiExchangeImpl(configuracionService);
            Historial historial = new HistorialImpl();
            Ejecutar ejecutar = new EjecutarImpl(entrada, apiExchange, historial);
            ejecutar.iniciarConversion();
        } catch (EntradaDeDatosInvalidaException | LecturaDeArchivoException e) {
            System.out.println(e.getMessage());;
        }finally {
            entrada.close();
        }
    }
}
```

Nótese que la clase solo funciona como contenedor de inyección de dependencias según la lógica de ejecución definida. Lo anterior también obedece al principio de inyección de dependencias, donde se garantiza un bajo acoplamiento entre clases y mayor modularidad. Como dato extra, se usaron principios SOLID en la arquitectura del código en aras de garantizar flexibilidad, mantenibilidad y facil depuración.

### Menú del terminal
- Una vez ejecutada la clase main, se despliega un menú por consola donde el usuario puede ver la lista de las 161 monedas disponibles en la API de intercambio.

  <p align="center">
    <img src="https://github.com/user-attachments/assets/b947cc51-36db-43a9-b835-908d21957eda" width="45%">
    <img src="https://github.com/user-attachments/assets/d87d9ba8-3c13-4c27-9db8-953504ddb81c" alt="Imagen 2" width="45%">
  </p>
  
  **Nota:** Por temas de espacio, se anexan dos imagenes ilustrativas sobre la lista de monedas para realizar intercambio

- Al final de la lista de monedas, se le pedirá al usuario ingresar, de acuerdo a la numeración de monedas del menú, el número de la moneda base, y el número de moneda a la que desea convertir. Una vez digitados los números, se mostrará por consola el resultado de la conversión y la hora en la que se hizo el registro. A continuación se muestra una imagen ilustrativa del proceso:

  ![4](https://github.com/user-attachments/assets/ee825aa2-9a50-403f-a348-102bd9cce52a)

- Al realizar la conversión de monedas, el usuario podrá tomar la opción de seguir realizando mas conversiones, o salir.
  
  ![5](https://github.com/user-attachments/assets/df5603f7-e441-4b2f-8d40-4a1441fb2aed)
  
- Cada vez que se realiza una conversión, el registro queda grabado en un archivo llamado ```historial_conversiones.txt```, el cual se encuentra en la ruta ```src/main/resources```  del proyecto maven.

  ![7](https://github.com/user-attachments/assets/43a69b49-b682-472d-abf5-afbd442327de)

  Al abrirlo nos encontraremos con el historial de registro de todas las conversiones realizadas por el usuario, con fecha y hora exacta.

  ![8](https://github.com/user-attachments/assets/be3d4202-fb79-40b2-8cee-5fc6f911af06)

- Si el usuario desea salir del menú de conversiones, el programa le pedirá si desea ver el historial de registros en la consola. En caso tal de hacerlo, se llamara el fichero ```historial_conversiones.txt``` y se mostrará en pantalla el resultado de las consultas.

  ![6](https://github.com/user-attachments/assets/826147fc-ac1f-4aee-b0d2-67cbfbfa7369)

## Conclusión
***
Aunque en este challenge aplicamos los conceptos de la progreamación orientada a objetos, tampoco se descartaron otras prácticas sobre arquitectura de software. Si bien, a nivel básico se pudo implementar principios SOLID, inyección de dependencias, entre otros; queda aún mucho por aprender en este nuevo desafío con Alura-Oracle. Espero con ansias seguir aprendiendo, y como no, seguir aportando conocimiento a la comunidad en general. Ojalá este repositorio pueda ser de gran ayuda, y tal vez podemos mejorarlo entre todos!!
