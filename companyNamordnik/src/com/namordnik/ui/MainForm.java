package com.namordnik.ui;

import com.namordnik.entity.Product;
import com.namordnik.manager.ProductManager;
import com.namordnik.util.BaseForm;
import com.namordnik.util.CustomTableModel;
import com.namordnik.util.DialogUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.*;

public class MainForm extends BaseForm {
    private JTable table;
    private JButton helpButton;
    private JButton aboutButton;
    private JPanel mainPanel;
    private JButton addButton;
    private JComboBox sortBox;
    private JButton sortButton;
    private JComboBox filtrationBox;
    private JTextField searchField;
    private JLabel rowsCountLabel;

    private CustomTableModel<Product> model;
    private List<Product> products;

    private boolean byId;
    private boolean byTitle;
    private boolean byWork;
    private boolean byPrice;

    public MainForm(int width, int height) {
        super(width, height);
        setContentPane(mainPanel);

        byId = true;
        byTitle = false;
        byWork = false;
        byPrice = false;

        initTable();
        initButtons();
        initBoxes();
        initSearch();

        setVisible(true);
    }

    private void initTable() {
        try {
            products = ProductManager.selectAll();
            updateRowsCount(products.size(), products.size());
            model = new CustomTableModel<>(
                    Product.class,
                    products,
                    new String[] {"ID", "Название", "Тип продукта", "Артикул", "Описание", "Путь до картинки", "Количество персонала на разработке", "Количество персонала на складе", "Минимальная цена для агента", "Изображение"}
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(50);
        TableColumn column = table.getColumn("Путь до картинки");
        column.setMinWidth(0);
        column.setPreferredWidth(0);
        column.setMaxWidth(0);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    dispose();
                    new EditForm(800, 500, model.getRows().get(row));
                }
            }
        });
    }

    private void initSearch() {
        searchField.getDocument().addDocumentListener(new DocumentListener() {
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

    private void initButtons() {
        helpButton.addActionListener(e -> {
            DialogUtil.showMessage(this, "Для добавления нажмите кнопку добавить, для редактирования дважды кликните по строке в таблице");
        });

        aboutButton.addActionListener(e -> {
            DialogUtil.showMessage(this, "Автор: Вася Пупкин, почты нету еще во 2 классе сделаю");
        });

        sortButton.addActionListener(e -> {
            applyFilters();
        });

        addButton.addActionListener(e -> {
            dispose();
            new AddForm(800, 500);
        });
    }

    private void initBoxes() {
        sortBox.addItem("По ID");
        sortBox.addItem("По наименованию");
        sortBox.addItem("По номеру цеха");
        sortBox.addItem("По минимальной стоимости");

        filtrationBox.addItem("Все");
        Set<String> set = new HashSet<>();
        for (Product p : products) {
            set.add(p.getProductType());
        }
        for (String s : set) {
            filtrationBox.addItem(s);
        }

        filtrationBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                applyFilters();
            }
        });
    }

    private void applyFilters() {
        List<Product> currentProducts = new ArrayList<Product>(products);

        int filtrationIndex = filtrationBox.getSelectedIndex();
        if (filtrationIndex != 0) {
            System.out.println(filtrationBox.getItemAt(filtrationIndex));
            currentProducts.removeIf(product -> !product.getProductType().equals(filtrationBox.getItemAt(filtrationIndex)));
        }

        if (!searchField.getText().isEmpty()) {
            currentProducts.removeIf(product -> !product.getTitle().toLowerCase(Locale.ROOT).contains(searchField.getText().toLowerCase(Locale.ROOT)));
        }

        int sortIndex = sortBox.getSelectedIndex();
        switch (sortIndex) {
            case 0: {
                currentProducts.sort(new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        return byId
                                ? o1.getId() >= o2.getId() ? 1 : -1
                                : o2.getId() >= o1.getId() ? 1 : -1;
                    }
                });
                byId = !byId;
                byTitle = false;
                byWork = false;
                byPrice = false;
                break;
            }

            case 1: {
                currentProducts.sort(new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        return byTitle
                        ? o1.getTitle().compareTo(o2.getTitle())
                                : o2.getTitle().compareTo(o1.getTitle());
                    }
                });
                byId = false;
                byTitle = !byTitle;
                byWork = false;
                byPrice = false;
                break;
            }

            case 2: {
                currentProducts.sort(new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        return byWork
                        ? o1.getProductionWorkshopNumber() >= o2.getProductionWorkshopNumber() ? 1 : -1
                                : o2.getProductionWorkshopNumber() >= o1.getProductionWorkshopNumber() ? 1 : -1;
                    }
                });
                byId = false;
                byTitle = false;
                byWork = !byWork;
                byPrice = false;
                break;
            }

            case 3: {
                currentProducts.sort(new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        return byPrice
                        ? o1.getMinCostForAgent() >= o2.getMinCostForAgent() ? 1 : -1
                                : o2.getMinCostForAgent() >= o1.getMinCostForAgent() ? 1 : -1;
                    }
                });
                byId = false;
                byTitle = false;
                byWork = false;
                byPrice = !byPrice;
                break;
            }
        }
        model.setRows(currentProducts);
        model.fireTableDataChanged();

        updateRowsCount(currentProducts.size(), products.size());
    }

    private void updateRowsCount(int current, int max) {
        rowsCountLabel.setText("Отобажено записей: " + current + " / " + max);
    }
}
