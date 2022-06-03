package org.jokerMask.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jokerMask.entity.Product;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.List;

@Data
@AllArgsConstructor
public class CustomTableModel<T> extends AbstractTableModel {
    Class<T> cls;
    List<T> list;
    String[] names;

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return cls.getDeclaredFields().length;
    }

    @Override
    public String getColumnName(int column) {
        return column < names.length ? names[column] : "Default Govno";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return cls.getDeclaredFields()[columnIndex].getType();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Field field = cls.getDeclaredFields()[columnIndex];
        field.setAccessible(true);
        try {
            return field.get(list.get(rowIndex));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
