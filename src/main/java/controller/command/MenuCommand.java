package controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Se ejecuta cuando la petición llega sin parámetro {@code opcion}: redirige al
 * menú principal.
 *
 * @author Juan Miguel Silva Martín
 * @version 2.0
 */
public class MenuCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/index.html");
        return null;
    }
}
