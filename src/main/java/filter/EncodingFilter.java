package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Fija la codificación UTF-8 de la petición y la respuesta antes de que nadie
 * lea los parámetros (así los acentos y la «ñ» de los formularios llegan bien).
 *
 * <p>
 * <b>Patrón Intercepting Filter:</b> es el primer eslabón de la cadena de
 * filtros; actúa sobre todas las peticiones sin que el controlador ni los
 * comandos tengan que ocuparse de ello.</p>
 *
 * @author Juan Miguel Silva Martín
 * @version 2.0
 */
@WebFilter(filterName = "EncodingFilter", urlPatterns = "/*")
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }
}
