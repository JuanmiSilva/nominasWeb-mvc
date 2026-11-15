package controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Acción que puede ejecutar el controlador frontal.
 *
 * <p>
 * <b>Patrón Command:</b> cada opción de la aplicación ({@code listar},
 * {@code salario}, ...) se encapsula en una clase propia que implementa esta
 * interfaz. El {@code FrontController} solo conoce {@code Command} y no tiene
 * ningún {@code switch} con la lógica de cada opción.</p>
 *
 * @author Juan Miguel Silva Martín
 * @version 2.0
 */
public interface Command {

    /**
     * Ejecuta la acción.
     *
     * @param request petición HTTP
     * @param response respuesta HTTP
     * @return nombre lógico de la vista a mostrar (por ejemplo {@code listar},
     * que corresponde a {@code /WEB-INF/views/listar.jsp}), o {@code null} si
     * el propio comando ya ha respondido (redirección o error)
     * @throws Exception si falla la ejecución; el controlador frontal la
     * traduce a un error HTTP
     */
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception;
}
