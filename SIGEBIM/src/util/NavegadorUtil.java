package util;

import java.awt.Component;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;

public class NavegadorUtil {

    private NavegadorUtil() {
    }
    
    public static void abrirEnNavegador(Component ventana, String url) {
        if (url == null || url.trim().isEmpty()) {
            JOptionPane.showMessageDialog(ventana,"El libro seleccionado no tiene una URL de acceso.",
                    "Acceso no disponible",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            JOptionPane.showMessageDialog(ventana,"El sistema no permite abrir el navegador.",
                    "Navegador no disponible",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            URI direccion = new URI(url.trim());
            Desktop.getDesktop().browse(direccion);
        } catch (URISyntaxException ex) {
            JOptionPane.showMessageDialog(ventana,"La URL registrada para este libro no es válida.",
                    "URL incorrecta",JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(ventana,"No se pudo abrir el libro en el navegador.",
                    "Error de acceso",JOptionPane.ERROR_MESSAGE);
        }
    }
}