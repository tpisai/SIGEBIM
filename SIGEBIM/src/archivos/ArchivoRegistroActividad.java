/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package archivos;

/**
 *
 * @author zulmi
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import modelo.reporte.RegistroActividad;

public class ArchivoRegistroActividad {
    private final String RUTA = "data/registrosActividad.txt";
    private final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    // Guardar todos los registros de actividad
    public void guardarRegistros(ArrayList<RegistroActividad> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA))) {
            for (RegistroActividad registro : lista) {
                pw.println(limpiarTexto(registro.getCodigo()) + "|"
                        + registro.getFechaHora().format(FORMATO_FECHA) + "|"
                        + limpiarTexto(registro.getUsuario()) + "|"
                        + limpiarTexto(registro.getRol()) + "|"
                        + limpiarTexto(registro.getModulo()) + "|"
                        + limpiarTexto(registro.getAccion()) + "|"
                        + limpiarTexto(registro.getDetalle()) + "|"
                        + limpiarTexto(registro.getResultado())
                );
            }
        } catch (IOException e) {
            System.out.println("Error al guardar registros de actividad: " + e.getMessage());
        }
    }
    // Cargar los registros de actividad
    public ArrayList<RegistroActividad> cargarRegistros() {
        ArrayList<RegistroActividad> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                //El -1 permite conservar campos vacíos, incluyendo el último.
                String[] datos = linea.split("\\|", -1);
                if (datos.length != 8) {
                    System.out.println("Registro de actividad inválido: " + linea);
                    continue;
                }
                RegistroActividad registro = new RegistroActividad(datos[0],LocalDateTime.parse
                                (datos[1],FORMATO_FECHA), datos[2], datos[3], datos[4],
                                datos[5], datos[6],datos[7]);
                lista.add(registro);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar registros de actividad: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error en el formato de los registros: " + e.getMessage());
        }
        return lista;
    }
    //Evitar dañar la estructura de la auditoria
    private String limpiarTexto(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("|", "/").replace("\n", " ").replace("\r", " ").trim();
    }
}