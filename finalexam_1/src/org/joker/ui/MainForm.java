package org.joker.ui;

import org.joker.entity.Product;
import org.joker.manager.ProductManager;
import org.joker.util.BaseForm;
import org.joker.util.CustomTableModel;
import org.joker.util.Notification;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.*;

public class MainForm extends BaseForm {
    private JTable table;
    private JPanel mainPanel;
    private JButton createButton;
    private JComboBox costCombo;
    private JComboBox typeCombo;
    private JButton sortByCost;
    private JTextField search;
    private JLabel rowsCount;

    private List<Product> products;
    private CustomTableModel<Product> model;

    private boolean byCost;

    public MainForm() {
        super(1200, 600);

        setContentPane(mainPanel);

        try {
            products = ProductManager.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
            Notification.showError(this, "Пошел нахрен");
        }

        model = new CustomTableModel<Product>(
                Product.class,
                new String[] {"ID", "Тайтл", "Артикул", "Минимальная цена", "Путь до картинки", "Тип продукта", "1", "2"},
                products
        );

        table.setModel(model);

        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(50);
        table.getColumn("Путь до картинки").setMinWidth(0);
        table.getColumn("Путь до картинки").setPreferredWidth(0);
        table.getColumn("Путь до картинки").setMaxWidth(0);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        dispose();
                        new EditForm(model.getRows().get(row));
                    }
                }
            }
        });

        initButtons();
        initCombos();
        initSearch();

        byCost = false;

        setRowsCount(products.size(), products.size());
        setVisible(true);
    }

    private void initButtons() {
        sortByCost.addActionListener(e -> {
            byCost = !byCost;
            applyFilters();
        });

        createButton.addActionListener(e -> {
            dispose();
            new CreateForm();
        });
    }

    private void initSearch() {
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                applyFilters();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                applyFilters();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                applyFilters();
            }
        });
    }

    private void initCombos() {
        costCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                applyFilters();
            }
        });

        Set<String> set = new HashSet<>();

        for (Product product : products) {
            set.add(product.getProductType());
        }

        typeCombo.addItem("Все");
        for (String s : set) {
            typeCombo.addItem(s);
        }

        typeCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                applyFilters();
            }
        });
    }

    private void applyFilters() {
        List<Product> temp = new ArrayList<>(products);

        switch (costCombo.getSelectedIndex()) {
            case 1: {
                temp.removeIf(product -> {
                    return product.getMinCostForAgent() < 0 || product.getMinCostForAgent() > 100;
                });
                break;
            }
            case 2: {
                temp.removeIf(product -> {
                    return product.getMinCostForAgent() < 101 || product.getMinCostForAgent() > 200;
                });
                break;
            }
            case 3: {
                temp.removeIf(product -> {
                    return product.getMinCostForAgent() < 201;
                });
                break;
            }
        }

        if (typeCombo.getSelectedIndex() != 0) {
            temp.removeIf(product -> !product.getProductType().equals(typeCombo.getItemAt(typeCombo.getSelectedIndex())));
        }

        if (search.getText() != "") {
            temp.removeIf(product -> !product.getTitle().toLowerCase(Locale.ROOT).contains(search.getText().toLowerCase(Locale.ROOT)));
        }

        if (byCost) {
            temp.sort(new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return (int) (o1.getMinCostForAgent() - o2.getMinCostForAgent());
                }
            });
        } else {
            temp.sort(new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return (int) (o2.getMinCostForAgent() - o1.getMinCostForAgent());
                }
            });
        }

        setRowsCount(temp.size(), products.size());

        model.setRows(temp);
        model.fireTableDataChanged();
    }

    private void setRowsCount(int cur, int max) {
        rowsCount.setText("Отображено записей: " + cur + "/" + max);
    }
}
