package controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmpleadoService;

/**
 * Opción {@code listar}: carga todos los empleados y muestra {@code listar}.
 *
 * @author Juan Miguel Silva Martín
 * @version 2.0
 */
public class ListarCommand implements Command {

    private final EmpleadoService servicio = new EmpleadoService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        request.setAttribute("lista", servicio.listarEmpleados());
        return "listar";
    }
}
