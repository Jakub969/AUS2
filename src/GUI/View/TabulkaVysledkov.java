package GUI.View;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TabulkaVysledkov extends AbstractTableModel {
    private final String[] columnNames = {"Geograficky objekt", "GPS súradnica 1", "GPS súradnica 2", "Súpisné číslo", "Popis", "Zoznam"};
    private final ArrayList<Object[]> data = new ArrayList<>();

    public void addRow(String geoObjekt, String gps1, String gps2, String supisneCislo, String popis, String zoznam) {
        data.add(new Object[]{geoObjekt, gps1, gps2, supisneCislo, popis, zoznam});
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    public void removeRow(int row) {
        data.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void clear() {
        int rowCount = getRowCount();
        if (rowCount > 0) {
            data.clear();
            fireTableRowsDeleted(0, rowCount - 1);
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
