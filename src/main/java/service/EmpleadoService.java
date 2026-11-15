package service;

import dao.EmpleadoDAO;
import dao.NominasDAO;
import java.util.List;
import modelo.DatosNoCorrectosException;
import modelo.Empleado;

/**
 * Capa de servicio de la aplicación.
 *
 * <p>
 * <b>Patrón Service Layer (con función de Facade):</b> ofrece al controlador
 * una interfaz sencilla con las operaciones de negocio y le oculta los DAO. Los
 * comandos nunca hablan con la base de datos: solo conocen este servicio, de
 * modo que si cambia la forma de acceder a los datos no hay que tocar el
 * controlador.</p>
 *
 * @author Juan Miguel Silva Martín
 * @version 2.0
 */
public class EmpleadoService {

    /**
     * Obtiene todos los empleados.
     *
     * @return lista con todos los empleados
     * @throws DatosNoCorrectosException si algún dato recuperado no es válido
     */
    public List<Empleado> listarEmpleados() throws DatosNoCorrectosException {
        return EmpleadoDAO.devolverEmpleados();
    }

    /**
     * Obtiene los empleados que cumplen los criterios indicados (los criterios
     * {@code null} no se aplican).
     *
     * @param dni DNI (o parte), o null
     * @param nombre nombre (o parte), o null
     * @param sexo sexo, o null
     * @param categoria categoría, o null
     * @param anyos años trabajados, o null
     * @return lista de empleados filtrada
     * @throws DatosNoCorrectosException si algún dato recuperado no es válido
     */
    public List<Empleado> filtrarEmpleados(String dni, String nombre,
            Character sexo, Integer categoria, Integer anyos)
            throws DatosNoCorrectosException {
        return EmpleadoDAO.filtrarEmpleados(dni, nombre, sexo, categoria, anyos);
    }

    /**
     * Consulta la nómina almacenada de un empleado.
     *
     * @param dni DNI del empleado
     * @return importe de la nómina, o 0 si no existe
     */
    public int consultarSalario(String dni) {
        return NominasDAO.devolverNomina(dni);
    }
}
