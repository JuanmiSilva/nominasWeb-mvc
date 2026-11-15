package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Registra cada petición que llega al controlador frontal (método, opción y
 * tiempo de proceso). Sustituye a los {@code System.out.println} de depuración
 * que había dentro del controlador.
 *
 * <p>
 * <b>Patrón Intercepting Filter:</b> se ejecuta antes y después del servlet sin
 * que éste lo sepa.</p>
 *
 * @author Juan Miguel Silva Martín
 * @version 2.0
 */
@WebFilter(filterName = "LoggingFilter", urlPatterns = "/empleados")
public class LoggingFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        long inicio = System.currentTimeMillis();
        try {
            chain.doFilter(request, response);
        } finally {
            LOG.info(req.getMethod() + " /empleados opcion="
                    + req.getParameter("opcion") + " ("
                    + (System.currentTimeMillis() - inicio) + " ms)");
        }
    }
}
