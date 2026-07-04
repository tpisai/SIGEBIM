package util;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class TablaUtil {

    public static void ajustarTabla(JTable tabla) {
        int columnas = tabla.getColumnCount();

        if (columnas <= 3) {
            // Pocas columnas: que se repartan proporcionalmente
            tabla.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
            int anchoTotal = tabla.getParent().getWidth();
            int anchoPorColumna = anchoTotal / columnas;
            for (int col = 0; col < columnas; col++) {
                tabla.getColumnModel().getColumn(col).setPreferredWidth(anchoPorColumna);
            }
        } else {
            // Muchas columnas: mostrar completo con scroll
            tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tabla.setFillsViewportHeight(true);
            tabla.setRowHeight(tabla.getRowHeight() + 8);

            for (int column = 0; column < tabla.getColumnCount(); column++) {
                TableColumn tableColumn = tabla.getColumnModel().getColumn(column);
                int preferredWidth = 80;
                int maxWidth = 400;

                for (int row = 0; row < tabla.getRowCount(); row++) {
                    TableCellRenderer cellRenderer = tabla.getCellRenderer(row, column);
                    Component c = tabla.prepareRenderer(cellRenderer, row, column);
                    int width = c.getPreferredSize().width + tabla.getIntercellSpacing().width;
                    preferredWidth = Math.max(preferredWidth, width);
                }

                preferredWidth = Math.min(preferredWidth, maxWidth);
                tableColumn.setPreferredWidth(preferredWidth);
            }
        }
    }
}
