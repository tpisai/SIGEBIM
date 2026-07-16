package archivos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import modelo.libro.Libro;
import modelo.enums.EstadoPrestamo;
import modelo.persona.Usuario;
import modelo.prestamo.Prestamo;

public class ArchivoPrestamo {
    private final String RUTA = "data/prestamos.txt";

    public void guardarPrestamos(ArrayList<Prestamo> lista){
        try(PrintWriter pw = new PrintWriter(new FileWriter(RUTA))){
            for(Prestamo p : lista){
                pw.println(
                    p.getIdPrestamo() + "|" +
                    p.getLibro().getIsbn() + "|" +
                    p.getUsuario().getUsername() + "|" +
                    p.getFechaPrestamo() + "|" +
                    p.getFechaDevolucion() + "|" +
                    p.getEstado()
                );
            }
        }catch(IOException e){
            System.out.println("Error al guardar prestamos: " + e.getMessage());
        }
    }

    public ArrayList<Prestamo> cargarPrestamos(ArrayList<Libro> libros, ArrayList<Usuario> usuarios){
        ArrayList<Prestamo> lista = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(RUTA))){
            String linea;
            while((linea = br.readLine()) != null){
                if(linea.trim().isEmpty()) continue;
                String[] datos = linea.split("\\|");
                //Incorporar las clases anteriores
                Libro libro = buscarLibro(libros, datos[1]);
                Usuario usuario = buscarUsuario(usuarios, datos[2]);
                Prestamo prestamo = new Prestamo(
                    datos[0],
                    libro,
                    usuario,
                    LocalDate.parse(datos[3]),
                    LocalDate.parse(datos[4]),
                        EstadoPrestamo.desdeTexto(datos[5])
                );
                prestamo.actualizarEstadoPorFecha();
                lista.add(prestamo);
            }
        }catch(IOException e){
            System.out.println("Error al cargar prestamos: " + e.getMessage());
        }
        return lista;
    }
    //Apoyos para el cargar prestamo
    private Libro buscarLibro(ArrayList<Libro> lista, String isbn) {
        for (Libro l : lista) {
            if (l.getIsbn().equalsIgnoreCase(isbn)) return l;
        }
        return null;
    }

    private Usuario buscarUsuario(ArrayList<Usuario> lista, String username) {
        for (Usuario u : lista) {
            if (u.getUsername().equalsIgnoreCase(username)) return u;
        }
        return null;
    }
}
