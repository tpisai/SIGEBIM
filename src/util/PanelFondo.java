package util; 

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelFondo extends JPanel {
    private Image imagen;

    public PanelFondo(String rutaImagen) {
        if (rutaImagen != null) {
            imagen = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}