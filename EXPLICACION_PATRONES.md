# nominasWeb – Refactorización a MVC con Front Controller

**Autor:** Juan Miguel Silva Martín · 2º DAW · Unidad 3

## Qué se ha hecho

La aplicación de la unidad 2 tenía un servlet (`Controller`) con un `switch` gigante que mezclaba la lectura de parámetros, la llamada a los DAO y la elección de la vista. Se ha reorganizado según MVC:

| Capa | Contenido |
|---|---|
| **Modelo** | `modelo` (Persona, Empleado, Nomina, DatosNoCorrectosException), `dao`, `service` |
| **Vista** | JSP en `/WEB-INF/views` (ya no se pueden pedir directamente desde el navegador) |
| **Controlador** | `controller.FrontController` + `controller.CommandFactory` + `controller.command.*` |

Flujo de una petición: `index.html` → `/empleados?opcion=X` → `EncodingFilter` → `LoggingFilter` → `FrontController` → `CommandFactory` → `Command` → `EmpleadoService` → DAO → atributo en la petición → JSP.

## Patrones ya presentes en la unidad 2 (no cuentan como nuevos)

- **DAO**: `EmpleadoDAO`, `NominasDAO`.
- **Objeto de dominio / herencia**: `Persona` ← `Empleado`.

## Patrones nuevos

### 1. Front Controller — `controller/FrontController.java`
**Dónde:** único servlet de la aplicación (`@WebServlet("/empleados")`), atiende GET y POST.
**Por qué:** centraliza la entrada de todas las peticiones en un punto (gestión de errores 400/500, forward a la vista) y evita servlets sueltos con código repetido. Además las JSP están en `WEB-INF`, así que es imposible saltarse el controlador.

### 2. Command — `controller/command/*`
**Dónde:** interfaz `Command` y una clase por opción: `ListarCommand`, `SalarioCommand`, `MostrarSalarioCommand`, `ModificarCommand`, `FiltrarCommand` (más `MenuCommand` y `OpcionDesconocidaCommand`).
**Por qué:** cada acción queda encapsulada en su propia clase, pequeña y fácil de probar. El `switch` del controlador antiguo desaparece y añadir una opción nueva no obliga a modificar el `FrontController`.

### 3. Factory — `controller/CommandFactory.java`
**Dónde:** `CommandFactory.getCommand(opcion)` devuelve el `Command` que corresponde al parámetro `opcion`.
**Por qué:** el controlador no conoce las clases concretas; la decisión de «qué objeto crear» está en un único sitio. Sin opción devuelve el menú y con una opción inválida devuelve un comando que responde 404 (antes un `opcion` nulo provocaba un `NullPointerException`).

### 4. Singleton — `dao/Conexion.java`
**Dónde:** constructor privado y `Conexion.getInstance()`; los DAO piden las conexiones con `Conexion.getInstance().getConnection()`.
**Por qué:** la carga del driver y la lectura de la configuración (URL, usuario, contraseña, sobrescribibles con variables de entorno `DB_URL`, `DB_USER`, `DB_PASSWORD`) se hace una sola vez y en un solo punto, en lugar de repetirse en cada conexión.

### 5. Service Layer (Facade) — `service/EmpleadoService.java`
**Dónde:** `listarEmpleados()`, `filtrarEmpleados(...)`, `consultarSalario(dni)`.
**Por qué:** los comandos solo conocen el servicio, no los DAO. El controlador queda desacoplado de cómo se accede a los datos y las operaciones de negocio tienen un único punto de entrada.

### 6. Intercepting Filter — `filter/EncodingFilter.java`, `filter/LoggingFilter.java`
**Dónde:** filtros de servlet que se ejecutan antes del `FrontController`.
**Por qué:** `EncodingFilter` fija UTF-8 en todas las peticiones (acentos y «ñ» de los formularios) y `LoggingFilter` registra método, opción y tiempo de cada petición, sustituyendo a los `System.out.println` que había dentro del controlador. Son tareas transversales que no deben estar en el controlador.

## Otros cambios y correcciones

- `DatosNoCorrectosException` pasa de `controller` a `modelo`: el modelo no debe depender del controlador.
- `NominasDAO.actualizarNomina`: los parámetros del `UPDATE` estaban invertidos (nómina y DNI).
- `mostrarSalario.jsp`: taglib actualizado a `jakarta.tags.core` (JSTL 3.0, igual que `listar.jsp`) y salida escapada con `<c:out>` (evita XSS con el DNI).

## Cómo ejecutarlo

1. MariaDB en `localhost:3307`, base de datos `empleados` (o definir `DB_URL`, `DB_USER`, `DB_PASSWORD`).
2. `mvn clean package` y desplegar el WAR en Tomcat 10.1 (contexto `/empresa`).
3. Abrir `http://localhost:8080/empresa/index.html`.
