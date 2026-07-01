/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package archivos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import modelo.libro.Autor;

/**
 *
 * @author zulmi
 */
public class ArchivoAutor {

    private final String RUTA = "data/autores.txt";
    // Guardar por id y nombre
    public void guardarAutores(ArrayList<Autor> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA))) {
            for (Autor a : lista) {
                pw.println(a.getId() + "|" + a.getNombre());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar autores: " + e.getMessage());
        }
    }
    // Cargar ya sabemos como es
    public ArrayList<Autor> cargarAutores() {
        ArrayList<Autor> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split("\\|");
                Autor a = new Autor(datos[0], datos[1]);
                lista.add(a);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar autores: " + e.getMessage());
        }
        return lista;
    }
}
