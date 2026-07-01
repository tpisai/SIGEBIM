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
import modelo.libro.Categoria;
import modelo.libro.Libro;
import modelo.enums.TipoLibro;

/**
 *
 * @author zulmi
 */
public class ArchivoLibro {

    private final String RUTA = "data/libros.txt";
    //Guardar libros
    public void guardarLibros(ArrayList<Libro> lista){
        try{
            PrintWriter pw = new PrintWriter(new FileWriter(RUTA));
            for(Libro l : lista){
                pw.println(
                    l.getIsbn() + "|" +
                    l.getTitulo() + "|" +
                    l.getAutor().getId() + "|" +
                    l.getAutor().getNombre()+ "|" +
                    l.getCategoria().getId() + "|" +
                    l.getCategoria().getNombre()+ "|" +
                    l.getAnioPublicacion() + "|" +
                    l.getDescripcion() + "|" +
                    l.getStock() + "|" +
                    l.getEstadoLibro().getTexto() + "|" +
                    l.getTipoLibro().getTexto() + "|" +
                    l.getUbicacionEstante() + "|" +
                    l.getUrlAcceso()
                );
            }
            pw.close();
        }catch(IOException e){
            System.out.println(
            "Error al guardar libros: " + e.getMessage());
        }
    }
    //Cargar libros
    public ArrayList<Libro> cargarLibros(){
        ArrayList<Libro> lista = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(RUTA));
            String linea;
            while((linea = br.readLine()) != null){
                //split("\\|") divide la línea en partes usando el carácter | como separador.
                String[] datos = linea.split("\\|");
                //Asignar la forma correcta del Objeto Libro
                //Llamamos a las clases de autor y categoria para rellenar los datos
                Autor autor = new Autor(datos[2], datos[3]);
                Categoria categoria = new Categoria(datos[4],datos[5]);
                //Libro
                Libro libro = new Libro(datos[0], datos[1], autor, categoria, Integer.parseInt(datos[6]),
                        datos[7], Integer.parseInt(datos[8]), datos[9], TipoLibro.desdeTexto(datos[10]),
                        datos[11], datos[12]);
                //Añadir el libro al fichero
                lista.add(libro);
            }
            br.close();
        }catch(IOException e){
            System.out.println(
            "Error al cargar libros: " + e.getMessage());
        }
        return lista;
    }
}
