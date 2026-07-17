/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author zulmi
 */
import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

public class AyudaUtil {

    private static final String CARPETA_AYUDA = "recursos_ayuda";
    private AyudaUtil() {
    }

    public static void abrirPagina(Component ventana, String nombreArchivo) {
        File archivo = new File(System.getProperty("user.dir")
                + File.separator
                + CARPETA_AYUDA
                + File.separator
                + nombreArchivo);
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(ventana, "No se encontró el archivo de ayuda:\n"
                    + archivo.getAbsolutePath(),"Archivo no encontrado",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            JOptionPane.showMessageDialog(ventana, "El sistema no permite abrir páginas en el navegador.",
                    "Ayuda",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Desktop.getDesktop().browse(archivo.toURI());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(ventana,"No se pudo abrir la página de ayuda.",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}