package controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmpleadoService;

/**
 * Opción {@code mostrarSalario}: consulta el salario del DNI indicado y deja
 * en la petición los atributos {@code sueldo} y {@code dni}.
 *
 * @author Juan Miguel Silva Martín
 * @version 2.0
 */
public class MostrarSalarioCommand implements Command {

    private final EmpleadoService servicio = new EmpleadoService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String dni = request.getParameter("dni");
        if (dni == null || dni.isBlank()) {
            throw new IllegalArgumentException("Debes indicar un DNI");
        }
        dni = dni.trim();
        request.setAttribute("sueldo", servicio.consultarSalario(dni));
        request.setAttribute("dni", dni);
        return "mostrarSalario";
    }
}
