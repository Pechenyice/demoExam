package util;

import javax.swing.table.AbstractTableModel;
import javax.swing.text.TableView;
import java.lang.reflect.Field;
import java.util.List;

public class CustomTableModel<T> extends AbstractTableModel {
    private List<T> rows;
    private Field[] fields;
    private List<String> fieldNames;

    public CustomTableModel(Class<T> cls, List<String> names, List<T> rows) {
        this.rows = rows;
        this.fieldNames = names;
        fields = cls.getDeclaredFields();
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return fields.length;
    }

    @Override
    public String getColumnName(int column) {
        return column < fieldNames.size() ? fieldNames.get(column) : "Default title";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return fields[columnIndex].getType();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Field field = fields[columnIndex];
            field.setAccessible(true);
            return field.get(rows.get(rowIndex));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
