package controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Se ejecuta cuando el valor de {@code opcion} no corresponde a ningún comando:
 * responde con un error 404.
 *
 * @author Juan Miguel Silva Martín
 * @version 2.0
 */
public class OpcionDesconocidaCommand implements Command {

    private final String opcion;

    /**
     * @param opcion valor recibido que no se reconoce
     */
    public OpcionDesconocidaCommand(String opcion) {
        this.opcion = opcion;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Opción no válida");
        return null;
    }
}
