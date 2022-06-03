package org.org.ui;

import org.org.App;
import org.org.entity.Product;
import org.org.manager.ProductManager;
import org.org.util.BaseForm;
import org.org.util.CustomTableModel;
import org.org.util.Notification;

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
    private JTextField search;
    private JButton costSortButton;
    private JComboBox typeCombo;
    private JLabel rowsCountLabel;
    private JButton aboutButton;
    private JButton instructionButton;
    private JButton logoutButton;

    private CustomTableModel<Product> model;
    private List<Product> products;
    private boolean byCost = false;

    public MainForm() {
        super(1200, 800);
        setContentPane(mainPanel);

        initTable();
        initSearch();
        initCombos();
        initButtons();

        setRowsCount(products.size(), products.size());

        setVisible(true);
    }

    public void initButtons() {
        createButton.setEnabled(App.IS_ADMIN);
        createButton.addActionListener(e -> {
            dispose();
            new CreateForm();
        });

        costSortButton.addActionListener(e -> {
            byCost = !byCost;
            applyFilters();
        });

        aboutButton.addActionListener(e -> {
            Notification.showInfo(this, "Я автор");
        });

        instructionButton.addActionListener(e -> {
            Notification.showInfo(this, "Это инструкция");
        });

        logoutButton.addActionListener(e -> {
            dispose();
            new AuthForm();
        });
    }

    public void initCombos() {
        typeCombo.addItem("Все");

        Set<String> set = new HashSet<>();
        for (Product product : products) {
            set.add(product.getProductType());
        }
        for (String s : set) {
            typeCombo.addItem(s);
        }

        typeCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                applyFilters();
            }
        });
    }

    public void applyFilters() {
        List<Product> tmp = new ArrayList<>(products);

        if (typeCombo.getSelectedIndex() != 0) {
            tmp.removeIf(product -> !product.getProductType().equals(typeCombo.getItemAt(typeCombo.getSelectedIndex())));
        }

        if (!search.getText().isEmpty())  tmp.removeIf(product -> !product.getTitle().toLowerCase(Locale.ROOT).contains(search.getText().toLowerCase(Locale.ROOT)));

        if (byCost) {
            tmp.sort(new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return (int) (o1.getCost() - o2.getCost());
                }
            });
        } else {
            tmp.sort(new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return (int) (o2.getCost() - o1.getCost());
                }
            });
        }

        setRowsCount(tmp.size(), products.size());

        model.setRows(tmp);
        model.fireTableDataChanged();
    }

    public void initSearch() {
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

    public void setRowsCount(int cur, int max) {
        rowsCountLabel.setText("Отображено записей: " + cur + "/" + max);
    }

    public void initTable() {
        try {
            products = ProductManager.selectAll();
        } catch (SQLException e) {
            Notification.showError(this, "Соси");
            e.printStackTrace();
        }

        model = new CustomTableModel<>(
                Product.class,
                new String[] {"ID", "Title", "ProductType", "ImagePath", "Cost", "RegisterDate", "Image"},
                products
        );

        table.setModel(model);

        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(50);

        table.getColumn("ImagePath").setMinWidth(0);
        table.getColumn("ImagePath").setPreferredWidth(0);
        table.getColumn("ImagePath").setMaxWidth(0);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (App.IS_ADMIN && row != - 1) {
                        dispose();
                        new EditForm(model.getRows().get(row));
                    }
                }
            }
        });
    }
}
