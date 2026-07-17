package servicios.respaldos;

/**
 *
 * @author zulmi
 */
import archivos.ArchivoRegistroActividad;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import modelo.reporte.RegistroActividad;
import modelo.enums.RolUsuario;
import modelo.persona.Usuario;

public class RegistroActividadService {
    private final ArchivoRegistroActividad archivo;
    private final ArrayList<RegistroActividad> registros;

    public RegistroActividadService() {
        archivo = new ArchivoRegistroActividad();
        registros = archivo.cargarRegistros();
    }
    //Registrar una actividad de un usuario autenticado.
    public boolean registrar(Usuario usuario, String modulo, String accion, String detalle, boolean exitoso) {
        if (usuario == null) {
            System.out.println("No se puede registrar una actividad " + "sin un usuario.");
            return false;
        }
        if (modulo == null || modulo.trim().isEmpty()) {
            System.out.println("Debe indicar el módulo de la actividad.");
            return false;
        }
        if (accion == null || accion.trim().isEmpty()) {
            System.out.println("Debe indicar la acción realizada.");
            return false;
        }
        String username = usuario.getUsername();
        if (username == null || username.trim().isEmpty()) {
            username = usuario.getNombre();
        }
        RolUsuario rolUsuario = usuario.getRol();
        String rol = rolUsuario == null ? RolUsuario.LECTOR.getTexto() : rolUsuario.getTexto();
        String resultado = exitoso ? "Exitoso" : "Fallido";
        RegistroActividad registro = new RegistroActividad(generarCodigo(),LocalDateTime.now(),
                        username, rol,modulo.trim(),accion.trim(),
                        detalle == null ? "" : detalle.trim(), resultado);
        registros.add(registro);
        archivo.guardarRegistros(registros);
        return true;
    }
    //Registrar un inicio de sesión fallido,para este caso todavía no existe un Usuario autenticado.
    public boolean registrarIntentoFallido(String username, String detalle) {
        if (username == null || username.trim().isEmpty()) {
            username = "Desconocido";
        }
        RegistroActividad registro = new RegistroActividad(generarCodigo(), LocalDateTime.now(),
                username.trim(),"No identificado","Seguridad","Iniciar sesión",
                        detalle == null ? "" : detalle.trim(),"Fallido");
        registros.add(registro);
        archivo.guardarRegistros(registros);
        return true;
    }
    // Obtener todos los registros
    public ArrayList<RegistroActividad> listar() {
        return new ArrayList<>(registros);
    }
    // Buscar registros por usuario
    public ArrayList<RegistroActividad> buscarPorUsuario(String username) {
        ArrayList<RegistroActividad> encontrados = new ArrayList<>();
        if (username == null) {
            return encontrados;
        }
        for (RegistroActividad registro : registros) {
            if (registro.getUsuario().equalsIgnoreCase(username.trim())) {
                encontrados.add(registro);
            }
        }
        return encontrados;
    }
    // Buscar registros por rol
    public ArrayList<RegistroActividad> buscarPorRol(RolUsuario rol) {
        ArrayList<RegistroActividad> encontrados = new ArrayList<>();
        if (rol == null) {
            return encontrados;
        }
        for (RegistroActividad registro : registros) {
            if (registro.getRol().equalsIgnoreCase(rol.getTexto())) {
                encontrados.add(registro);
            }
        }
        return encontrados;
    }
    // Buscar registros por módulo
    public ArrayList<RegistroActividad> buscarPorModulo(String modulo) {
        ArrayList<RegistroActividad> encontrados = new ArrayList<>();
        if (modulo == null) {
            return encontrados;
        }
        for (RegistroActividad registro : registros) {
            if (registro.getModulo().equalsIgnoreCase(modulo.trim())) {
                encontrados.add(registro);
            }
        }
        return encontrados;
    }
    // Buscar registros por una fecha
    public ArrayList<RegistroActividad> buscarPorFecha(LocalDate fecha) {
        ArrayList<RegistroActividad> encontrados = new ArrayList<>();
        if (fecha == null) {
            return encontrados;
        }
        for (RegistroActividad registro : registros) {
            if (registro.getFechaHora().toLocalDate().equals(fecha)) {
                encontrados.add(registro);
            }
        }
        return encontrados;
    }
    // Buscar registros entre dos fechas
    public ArrayList<RegistroActividad> buscarPorRangoFechas(LocalDate fechaInicial, LocalDate fechaFinal) {
        ArrayList<RegistroActividad> encontrados = new ArrayList<>();
        if (fechaInicial == null || fechaFinal == null) {
            return encontrados;
        }
        if (fechaInicial.isAfter(fechaFinal)) {
            return encontrados;
        }
        for (RegistroActividad registro : registros) {
            LocalDate fechaRegistro = registro.getFechaHora().toLocalDate();
            boolean dentroDelRango = !fechaRegistro.isBefore(fechaInicial) && !fechaRegistro.isAfter(fechaFinal);
            if (dentroDelRango) {
                encontrados.add(registro);
            }
        }
        return encontrados;
    }

    // Generar códigos LOG-0001, LOG-0002...
    private String generarCodigo() {
        int mayorNumero = 0;
        for (RegistroActividad registro : registros) {
            String codigo = registro.getCodigo();
            if (codigo == null || !codigo.startsWith("LOG-")) {
                continue;
            }
            try {
                int numero = Integer.parseInt(codigo.substring(4));
                if (numero > mayorNumero) {
                    mayorNumero = numero;
                }
            } catch (NumberFormatException e) {
                // Se ignoran códigos que no tengan el formato esperado.
            }
        }
        return String.format("LOG-%04d", mayorNumero + 1);
    }
}