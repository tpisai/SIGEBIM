package archivos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import modelo.libro.Categoria;

public class ArchivoCategoria {

    private final String RUTA = "data/categorias.txt";
    // Guardar por id y nombre
    public void guardarCategorias(ArrayList<Categoria> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA))) {
            for (Categoria c : lista) {
                pw.println(c.getId() + "|" + c.getNombre());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar categorías: " + e.getMessage());
        }
    }
    // Cargar ya sabemos como es
    public ArrayList<Categoria> cargarCategorias() {
        ArrayList<Categoria> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split("\\|");
                Categoria c = new Categoria(datos[0], datos[1]);
                lista.add(c);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar categorías: " + e.getMessage());
        }
        return lista;
    }
}