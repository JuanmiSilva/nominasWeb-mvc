package controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Opción {@code salario}: muestra el formulario que pide el DNI.
 *
 * @author Juan Miguel Silva Martín
 * @version 2.0
 */
public class SalarioCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "salario";
    }
}
