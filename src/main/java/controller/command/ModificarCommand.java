package controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Opción {@code modificar}: muestra el formulario de filtrado de empleados.
 *
 * @author Juan Miguel Silva Martín
 * @version 2.0
 */
public class ModificarCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "modificar";
    }
}
