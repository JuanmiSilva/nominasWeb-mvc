He refactorizado la aplicación de nóminas a MVC con un único servlet controlador (FrontController, en /empleados), que recibe todas las peticiones y reenvía a las vistas JSP, ahora en WEB-INF/views. Patrones utilizados:

Front Controller (controller/FrontController.java): es el único punto de entrada de la aplicación. Centraliza la gestión de errores y el reenvío a las vistas, y evita tener servlets sueltos con código repetido.

Command (controller/command/*): cada opción (listar, salario, filtrar...) es una clase propia que implementa la interfaz Command. Así desaparece el switch gigante del controlador y añadir una opción no obliga a modificarlo.

Factory (controller/CommandFactory.java): crea el comando que corresponde al parámetro opcion. El controlador no conoce las clases concretas y la decisión de qué objeto crear queda en un único sitio.

Singleton (dao/Conexion.java): una única instancia que carga el driver y la configuración de la base de datos una sola vez, y de la que los DAO obtienen las conexiones.
