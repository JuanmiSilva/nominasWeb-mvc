package controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmpleadoService;

/**
 * Opción {@code filtrado}: convierte los parámetros del formulario, pide al
 * servicio los empleados que cumplen los criterios y muestra {@code listar}.
 *
 * @author Juan Miguel Silva Martín
 * @version 2.0
 */
public class FiltrarCommand implements Command {

    private final EmpleadoService servicio = new EmpleadoService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String dni = request.getParameter("dni");
        String nombre = request.getParameter("nombre");
        String sexoStr = request.getParameter("sexo");
        String catStr = request.getParameter("categoria");
        String anyStr = request.getParameter("anyos");

        Character sexo = (sexoStr != null && !sexoStr.isBlank())
                ? Character.toUpperCase(sexoStr.trim().charAt(0)) : null;
        Integer categoria;
        Integer anyos;
        try {
            categoria = (catStr != null && !catStr.isBlank())
                    ? Integer.valueOf(catStr.trim()) : null;
            anyos = (anyStr != null && !anyStr.isBlank())
                    ? Integer.valueOf(anyStr.trim()) : null;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Categoría y años deben ser numéricos");
        }

        request.setAttribute("lista",
                servicio.filtrarEmpleados(dni, nombre, sexo, categoria, anyos));
        return "listar";
    }
}
