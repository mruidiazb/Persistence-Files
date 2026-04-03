# Persistence-Files

Este es un proyecto Java diseñado para demostrar cómo realizar la lectura y escritura (persistencia) de archivos en cuatro formatos de almacenamiento comunes: **TXT, CSV, JSON y XML**.

## Estructura del Proyecto

El proyecto está diseñado bajo una arquitectura de dominio orientada a objetos sencilla con las siguientes capas:

1. **`dominio`**:
   - `biblioteca` (`Libro`): Utilizado para la persistencia básica texto (TXT).
   - `empleado` (`Empleado`): Utilizado para la persistencia tabular (CSV).
   - `pedidos` (`Pedido`, `Producto`): Modelos jerárquicos utilizados para persistir información más compleja e indexada en JSON y XML.

2. **`manejadores`**:
   Contiene la lógica core de entrada/salida (I/O).
   - `ManejadorTXT`: Usa clases base en Java (`FileReader`, `FileWriter`, `BufferedReader` y `BufferedWriter`).
   - `ManejadorCSV`: Utiliza la librería [OpenCSV](http://opencsv.sourceforge.net/) para serializar y deserializar propiedades automáticamente (Manejado por Anotaciones).
   - `ManejadorJSON`: Utiliza [Jackson Databind](https://github.com/FasterXML/jackson).
   - `ManejadorXML`: Utiliza [Jackson Dataformat XML](https://github.com/FasterXML/jackson-dataformat-xml).

3. **`ejecucion`**:
   - `Principal.java`: Clase que contiene el método `main()` principal para ejecutar los ejemplos, generar los archivos en la ruta `src/main/resources/files/` y recuperar (leer) y mostrar sus datos desde consola.

## Requisitos y Configuración

- **Java**: 17 o superior.
- **Gestor de Dependencias**: Maven (para resolver automáticamente Jackson y OpenCSV).

> [!NOTE]
> Las dependencias están configuradas en el archivo `pom.xml`. Al compilar o ejecutar por primera vez el IDE (IntelliJ, Eclipse, VS Code) o Maven descargarán automáticamente estas librerías.

## Ejecutando el Proyecto

Para probar el proyecto puedes:
1. Usando el IDE (Recomendado): Abre el proyecto, navega a `src/main/java/com/proyecto/persistencia/ejecucion/Principal.java` y haz clic derecho -> `Run Principal.main()`.
2. Usando Maven (CLI): 
   ```bash
   mvn clean compile exec:java -Dexec.mainClass="com.proyecto.persistencia.ejecucion.Principal"
   ```

Una vez se ejecute con éxito, notarás que en el directorio `src/main/resources/files/` se han creado cuatro archivos nuevos:
- `libros.txt`
- `empleados.csv`
- `pedido.json`
- `pedido.xml`

Puedes abrirlos con cualquier editor de texto para ver la información almacenada con sus respectivos formatos.

## Documentación Técnica

* **TXT**: Forma más primitiva pero la más liviana. El archivo carece de esquema y cada desarrollo determina qué separador usar (en este caso el carácter `;`).
* **CSV**: Ideal para tablas e importación a hojas de cálculo (Excel). La biblioteca OpenCSV facilita agrupar columnas para entidades Java sencillas pero se queda corta con listas anidadas (un empleado dentro de otro empleado o listado dinámico).
* **JSON**: Formato estándar de la web actual. Extremadamente ligero, legible por humanos y soporta jerarquías multi-nivel (E.g un `Pedido` que contiene `[] de Productos`).
* **XML**: Formato etiquetado históricamente maduro muy utilizado en sistemas "legacy", web-services (SOAP) y facturación electrónica. Es más verborrágico y pesado que el JSON pero altamente estandarizado. Al utilizar `jackson-dataformat-xml`, el mismo motor de JSON exporta a XML casi sin cambios en nuestra lógica.
