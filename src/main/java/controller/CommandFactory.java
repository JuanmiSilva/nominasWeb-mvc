package controller;

import controller.command.Command;
import controller.command.FiltrarCommand;
import controller.command.ListarCommand;
import controller.command.MenuCommand;
import controller.command.ModificarCommand;
import controller.command.MostrarSalarioCommand;
import controller.command.OpcionDesconocidaCommand;
import controller.command.SalarioCommand;
import java.util.HashMap;
import java.util.Map;

/**
 * Crea el {@link Command} que corresponde al valor del parámetro
 * {@code opcion}.
 *
 * <p>
 * <b>Patrón Factory (Simple Factory / Factory Method):</b> centraliza en un
 * único sitio la decisión de qué objeto crear. El controlador pide un comando
 * por su nombre y no sabe qué clase concreta recibe; para añadir una opción
 * nueva basta con crear el comando y registrarlo aquí. Los comandos no guardan
 * estado, por lo que se crean una vez y se comparten entre peticiones.</p>
 *
 * @author Juan Miguel Silva Martín
 * @version 2.0
 */
public final class CommandFactory {

    private static final Map<String, Command> COMANDOS = new HashMap<>();

    static {
        COMANDOS.put("listar", new ListarCommand());
        COMANDOS.put("salario", new SalarioCommand());
        COMANDOS.put("mostrarSalario", new MostrarSalarioCommand());
        COMANDOS.put("modificar", new ModificarCommand());
        COMANDOS.put("filtrado", new FiltrarCommand());
    }

    private CommandFactory() {
    }

    /**
     * Devuelve el comando asociado a una opción.
     *
     * @param opcion valor del parámetro {@code opcion} (puede ser null)
     * @return el comando correspondiente; {@link MenuCommand} si no hay opción
     * y {@link OpcionDesconocidaCommand} si no se reconoce
     */
    public static Command getCommand(String opcion) {
        if (opcion == null || opcion.isBlank()) {
            return new MenuCommand();
        }
        Command comando = COMANDOS.get(opcion);
        return comando != null ? comando : new OpcionDesconocidaCommand(opcion);
    }
}
