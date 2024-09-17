package Cliente.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class RendererComponentTable implements TableCellRenderer {

    private int tamañoSombreado = 0;

    public RendererComponentTable(int num) {
        tamañoSombreado = num;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Crear un componente de celda básico (JLabel en este caso)
        JLabel cell = new JLabel(value != null ? value.toString() : "");
        cell.setOpaque(true); // Para asegurarse de que el fondo se pinte correctamente
        cell.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto

        // Personalizar el borde
        int columnCount = table.getColumnCount();
        int rowCount = table.getRowCount();
        int groupSize = tamañoSombreado; // Número de columnas en cada grupo
        int borderWidth = 1;

        // Crear el borde
        Border border = BorderFactory.createEmptyBorder();
        if ((column + 1) % groupSize == 0 || column == columnCount - 1) {
            // Aplicar borde derecho e inferior en las últimas columnas del grupo
            border = BorderFactory.createMatteBorder(0, 0, 0, borderWidth, Color.BLACK);
        }

        if ((row + 1) % groupSize == 0 || row == rowCount -1){
            border = BorderFactory.createMatteBorder(0, 0, borderWidth, 0, Color.BLACK);
        }

        if(((column + 1) % groupSize == 0 || column == columnCount - 1) && ((row + 1) % groupSize == 0 || row == rowCount -1)){
            border = BorderFactory.createMatteBorder(0, 0, borderWidth, borderWidth, Color.BLACK);
        }

        cell.setBorder(border);

        return cell;
    }
}
