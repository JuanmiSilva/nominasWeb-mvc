package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión con la base de datos MariaDB ({@code empleados}) que
 * utiliza la aplicación.
 *
 * <p>
 * <b>Patrón Singleton:</b> existe una única instancia de esta clase en toda la
 * aplicación. La instancia se crea una sola vez (al cargarse la clase), carga
 * el driver JDBC y lee la configuración de acceso; después, todos los DAO
 * piden las conexiones a través de {@link #getInstance()}.</p>
 *
 * <p>
 * Los parámetros se pueden sobrescribir con las variables de entorno
 * {@code DB_URL}, {@code DB_USER} y {@code DB_PASSWORD}; si no existen se usan
 * los valores por defecto del entorno de desarrollo.</p>
 *
 * @author Juan Miguel Silva Martín
 * @version 2.0
 */
public final class Conexion {

    /** Única instancia (inicialización temprana, segura entre hilos). */
    private static final Conexion INSTANCIA = new Conexion();

    private final String url;
    private final String usuario;
    private final String password;

    /** Constructor privado: nadie más puede crear instancias. */
    private Conexion() {
        this.url = valor("DB_URL", "jdbc:mariadb://localhost:3307/empleados");
        this.usuario = valor("DB_USER", "root");
        this.password = valor("DB_PASSWORD", "0607");
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Driver de MariaDB no encontrado", e);
        }
    }

    /**
     * Devuelve la única instancia de la clase.
     *
     * @return instancia única de {@code Conexion}
     */
    public static Conexion getInstance() {
        return INSTANCIA;
    }

    /**
     * Abre una conexión con la base de datos de empleados. Quien la solicite es
     * responsable de cerrarla (los DAO lo hacen con try-with-resources).
     *
     * @return conexión activa con la base de datos
     * @throws SQLException si no es posible establecer la conexión
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, usuario, password);
    }

    private static String valor(String variable, String porDefecto) {
        String v = System.getenv(variable);
        return (v == null || v.isBlank()) ? porDefecto : v;
    }
}
