package com.igt.util;

import com.igt.entities.Service;
import lombok.AllArgsConstructor;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.List;

@AllArgsConstructor
public class CustomTableModel<T> extends AbstractTableModel {
    List<T> rows;
    Class<T> cls;
    String[] fieldNames;

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return cls.getDeclaredFields().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Field field = cls.getDeclaredFields()[columnIndex];
            field.setAccessible(true);
            return field.get(rows.get(rowIndex));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return column < fieldNames.length ? fieldNames[column] : "Default header";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return cls.getDeclaredFields()[columnIndex].getType();
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
