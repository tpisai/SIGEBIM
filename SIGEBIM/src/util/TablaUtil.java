package util;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class TablaUtil {

    public static void aplicarEstiloYFormato(javax.swing.JScrollPane scrollPane, JTable tabla) {
        // 1. Diseño Visual
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        tabla.setOpaque(false);

        // Renderizado para filas intercaladas tipo "papel antiguo"
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                // Fondo intercalado
                if (row % 2 == 0) {
                    c.setBackground(new Color(245, 235, 215, 200)); // Crema claro
                } else {
                    c.setBackground(new Color(230, 215, 190, 200)); // Crema tostado
                }
                
                c.setForeground(Color.BLACK); 
                return c;
            }
        });

        // Encabezado Café Tierra
        tabla.getTableHeader().setBackground(new Color(80, 50, 30));
        tabla.getTableHeader().setForeground(new Color(245, 235, 215));

        // 2. Ajuste automático para ocupar TODO el espacio
        SwingUtilities.invokeLater(() -> {
            // USAR ESTO para que las columnas se expandan automáticamente
            tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            tabla.setFillsViewportHeight(true);
            tabla.setRowHeight(Math.max(tabla.getRowHeight(), 25));

            int columnas = tabla.getColumnCount();
            for (int i = 0; i < columnas; i++) {
                TableColumn col = tabla.getColumnModel().getColumn(i);
                int anchoMinimo = 60;

                // Calcular ancho ideal basado en contenido
                Component header = tabla.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(
                        tabla, col.getHeaderValue(), false, false, -1, i);
                anchoMinimo = Math.max(header.getPreferredSize().width + 20, anchoMinimo);

                for (int row = 0; row < tabla.getRowCount(); row++) {
                    TableCellRenderer renderer = tabla.getCellRenderer(row, i);
                    Component c = tabla.prepareRenderer(renderer, row, i);
                    anchoMinimo = Math.max(c.getPreferredSize().width + 20, anchoMinimo);
                }
                
                // Establecemos el tamaño preferido, pero el AUTO_RESIZE_ALL_COLUMNS 
                // se encargará de estirarlo para llenar el JScrollPane
                col.setPreferredWidth(anchoMinimo);
            }
        });
    }
}