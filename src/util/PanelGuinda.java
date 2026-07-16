package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class PanelGuinda extends JPanel {
    
    public PanelGuinda() {
        setOpaque(false); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Suaviza los bordes y las formas
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Color café (R: 80, G: 50, B: 30) con transparencia 200
        // Este es el tono tierra de tu imagen de referencia
        g2.setColor(new Color(80, 50, 30, 140)); 
        
        // Dibuja el rectángulo redondeado
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
    }
}