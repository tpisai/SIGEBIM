package servicios.respaldos;

/**
 *
 * @author zulmi
 */
import archivos.ArchivoRespaldo;
import modelo.respaldo.ResultadoRespaldo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RespaldoService {

    private final ArchivoRespaldo archivoRespaldo;

    private final List<String> archivosObligatorios = List.of(
            "usuarios.txt",
            "libros.txt",
            "autores.txt",
            "categorias.txt",
            "prestamos.txt",
            "log_busqueda.txt");

    public RespaldoService() {
        this.archivoRespaldo = new ArchivoRespaldo();
    }

    public ResultadoRespaldo crearRespaldo(Path carpetaDatos, Path carpetaRespaldos) {
        if (!archivoRespaldo.existeDirectorio(carpetaDatos)) {
            return new ResultadoRespaldo(false, "La carpeta de datos no existe.", null);
        }
        String nombreRespaldo = generarNombreRespaldo();
        Path directorioRespaldo = carpetaRespaldos.resolve(nombreRespaldo);
        try {
            archivoRespaldo.crearDirectorio(directorioRespaldo);
            int archivosCopiados = 0;
            for (String nombreArchivo : archivosObligatorios) {
                Path origen = carpetaDatos.resolve(nombreArchivo);
                if (archivoRespaldo.existeArchivo(origen)) {
                    Path destino = directorioRespaldo.resolve(nombreArchivo);
                    archivoRespaldo.copiarArchivo(origen, destino);
                    archivosCopiados++;
                }
            }
            if (archivosCopiados == 0) {
                return new ResultadoRespaldo(false, "No se encontraron archivos para respaldar.", directorioRespaldo);
            }
            return new ResultadoRespaldo(true, "Copia de seguridad creada correctamente.", directorioRespaldo);
        } catch (IOException ex) {
            return new ResultadoRespaldo(false, "No se pudo crear la copia de seguridad: " +
                    ex.getMessage(),directorioRespaldo);
        }
    }

    public ResultadoRespaldo restaurarRespaldo(Path carpetaRespaldo, Path carpetaDatos) {
        if (!validarRespaldo(carpetaRespaldo)) {
            return new ResultadoRespaldo(false, "La carpeta seleccionada no contiene un respaldo válido.", carpetaRespaldo);
        }
        try {
            archivoRespaldo.crearDirectorio(carpetaDatos);
            for (String nombreArchivo : archivosObligatorios) {
                Path origen = carpetaRespaldo.resolve(nombreArchivo);
                if (archivoRespaldo.existeArchivo(origen)) {
                    Path destino = carpetaDatos.resolve(nombreArchivo);
                    archivoRespaldo.copiarArchivo(origen, destino);
                }
            }
            return new ResultadoRespaldo(true, "La información fue restaurada correctamente.", carpetaDatos);
        } catch (IOException ex) {
            return new ResultadoRespaldo(false, "No se pudo restaurar la información: " + 
                            ex.getMessage(), carpetaDatos);
        }
    }

    public boolean validarRespaldo(Path carpetaRespaldo) {
        if (!archivoRespaldo.existeDirectorio(carpetaRespaldo)) {
            return false;
        }
        return archivosObligatorios.stream().anyMatch(nombre -> 
                archivoRespaldo.existeArchivo(carpetaRespaldo.resolve(nombre)));
    }

    public List<Path> listarArchivos(Path carpeta) throws IOException {
        return archivoRespaldo.listarArchivos(carpeta);
    }

    private String generarNombreRespaldo() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return "respaldo_" + LocalDateTime.now().format(formato);
    }
    
    public String formatearTamanio(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        }
        if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        }
        return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
    }
    
    public LocalDateTime obtenerFechaModificacion(Path carpeta) {
    try {
            FileTime fechaArchivo = Files.getLastModifiedTime(carpeta);
            return LocalDateTime.ofInstant(fechaArchivo.toInstant(),ZoneId.systemDefault());
        } catch (IOException ex) {
            return null;
        }
    }
}