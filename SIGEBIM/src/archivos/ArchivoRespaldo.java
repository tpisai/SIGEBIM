package archivos;

/**
 *
 * @author zulmi
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

public class ArchivoRespaldo {

    public void crearDirectorio(Path directorio) throws IOException {
        Files.createDirectories(directorio);
    }

    public void copiarArchivo(Path origen, Path destino) throws IOException {
        Files.copy(origen,destino,
                StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.COPY_ATTRIBUTES);
    }

    public boolean existeDirectorio(Path directorio) {
        return directorio != null && Files.isDirectory(directorio);
    }

    public boolean existeArchivo(Path archivo) {
        return archivo != null && Files.isRegularFile(archivo);
    }

    public List<Path> listarArchivos(Path directorio) throws IOException {
        try (Stream<Path> archivos = Files.list(directorio)) {
            return archivos.filter(Files::isRegularFile).toList();
        }
    }
}