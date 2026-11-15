package controller;

import controller.command.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Único servlet controlador de la aplicación.
 *
 * <p>
 * <b>Patrón Front Controller:</b> todas las peticiones de negocio entran por
 * {@code /empleados}. Este servlet no contiene lógica de cada opción: pide a
 * {@link CommandFactory} el {@link Command} adecuado, lo ejecuta y reenvía la
 * petición a la vista JSP que éste indique. Las vistas están en
 * {@code /WEB-INF/views}, por lo que no se pueden pedir directamente desde el
 * navegador.</p>
 *
 * <p>
 * Los errores se traducen a HTTP: {@link IllegalArgumentException} → 400 y
 * cualquier otra excepción → 500.</p>
 *
 * @author Juan Miguel Silva Martín
 * @version 2.0
 */
@WebServlet(name = "FrontController", urlPatterns = "/empleados")
public class FrontController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(FrontController.class.getName());

    /** Carpeta (no accesible desde fuera) donde están las vistas JSP. */
    private static final String PREFIJO_VISTAS = "/WEB-INF/views/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesar(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesar(request, response);
    }

    private void procesar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Command comando = CommandFactory.getCommand(request.getParameter("opcion"));
        try {
            String vista = comando.execute(request, response);
            if (vista != null) {
                request.getRequestDispatcher(PREFIJO_VISTAS + vista + ".jsp")
                        .forward(request, response);
            }
        } catch (IllegalArgumentException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        } catch (ServletException | IOException ex) {
            throw ex;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al ejecutar la opción", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al procesar la petición");
        }
    }
}
