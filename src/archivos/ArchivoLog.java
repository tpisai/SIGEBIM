package archivos;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ArchivoLog {
    
    private static final String RUTA = "data/log_busqueda.txt";
    private static final DateTimeFormatter FORMATO = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    
    public void registrarBusqueda(String tipoBusqueda, String textoBuscado, int resultadosEncontrados) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA, true))) {
            String fecha = LocalDateTime.now().format(FORMATO);
            String estado = resultadosEncontrados > 0 ? "Encontrado" : "No encontrado"; 
            pw.printf("[%s] Tipo: %s | Término: %s | %s%n",  
                fecha, tipoBusqueda, textoBuscado, estado);
        } catch (Exception e) {
            System.err.println("Error al escribir log: " + e.getMessage());
        }
    }
}