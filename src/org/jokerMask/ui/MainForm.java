package org.jokerMask.ui;

import org.jokerMask.App;
import org.jokerMask.entity.Product;
import org.jokerMask.manager.ProductManager;
import org.jokerMask.util.BaseForm;
import org.jokerMask.util.CustomTableModel;
import org.jokerMask.util.Notification;

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
    private JButton helpButton;
    private JButton aboutButton;
    private JButton createButton;
    private JLabel rowsCountLabel;
    private JButton idSortButton;
    private JButton clearFiltersButton;
    private JComboBox articleCombo;
    private JComboBox productionCombo;
    private JTextField search;

    private List<Product> rootProducts;
    private CustomTableModel<Product> model;

    private boolean byId = true;

    public MainForm() {
        super(1200, 800);

        setContentPane(mainPanel);

        initTable();
        initButtons();
        initSearch();
        initCombos();

        setVisible(true);
    }

    public void initCombos() {
        productionCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                applyFilters();
            }
        });

        Set<String> entries = new HashSet<>();
        for (int i = 0; i < rootProducts.size(); i++) {
            entries.add(rootProducts.get(i).getArticleNumber());
        }

        articleCombo.addItem("Все");
        entries.forEach(e -> {
            articleCombo.addItem(e);
        });

        articleCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                applyFilters();
            }
        });
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

    public void applyFilters() {
        List<Product> internal = new ArrayList<>(rootProducts);

        internal.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if (byId) {
                    return o1.getId() - o2.getId();
                } else {
                    return o2.getId() - o1.getId();
                }
            }
        });

        internal.removeIf(product ->
            !product.getTitle().toLowerCase(Locale.ROOT).contains(search.getText().toLowerCase(Locale.ROOT))
        );

        switch (productionCombo.getSelectedIndex()) {
            case 1: {
                internal.removeIf(product -> product.getProductionPersonCount() > 1);
                break;
            }
            case 2: {
                internal.removeIf(product -> product.getProductionPersonCount() < 2 || product.getProductionPersonCount() > 3);
                break;
            }
            case 3: {
                internal.removeIf(product -> product.getProductionPersonCount() < 4 || product.getProductionPersonCount() > 5);
                break;
            }
            case 4: {
                internal.removeIf(product -> product.getProductionPersonCount() < 6);
                break;
            }
        }

        if (articleCombo.getSelectedIndex() != 0) {
            String filter = String.valueOf(articleCombo.getItemAt(articleCombo.getSelectedIndex()));
            internal.removeIf(product -> product.getArticleNumber() != filter);
        }

        model.setList(internal);
        model.fireTableDataChanged();
        updateCount(model.getList().size(), rootProducts.size());
    }

    public void initTable() {
        try {
            rootProducts = ProductManager.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CustomTableModel<Product> model = new CustomTableModel<>(
                Product.class,
                rootProducts,
                new String[] {"id", "Тайтл", "продуктТайп", "артиклНумбер", "дескрипшн", "имагепас", "пр1", "пр2", "минКост", "image"}
        );

        this.model = model;

        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(50);
        table.getColumn("имагепас").setMaxWidth(0);
        table.getColumn("имагепас").setMinWidth(0);
        table.getColumn("имагепас").setPreferredWidth(0);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        dispose();
                        new EditForm(model.getList().get(row));
                    }
                }
            }
        });

        updateCount(rootProducts.size(), model.getList().size());
    }

    public void updateCount(int cur, int max) {
        rowsCountLabel.setText("Отображено записей: " + cur + "/" + max);
    }

    public void initButtons() {
        helpButton.addActionListener(e -> {
            Notification.info(this, "Пошел отсюда чистый грязный");
        });

        aboutButton.addActionListener(e -> {
            Notification.info(this, " и чо");
        });

        createButton.addActionListener(e -> {
            dispose();
            new CreateForm();
        });

        createButton.setEnabled(App.ADMIN_MODE);

        clearFiltersButton.addActionListener(e -> {
            byId = true;
            search.setText("");
            articleCombo.setSelectedIndex(0);
            productionCombo.setSelectedIndex(0);
            updateCount(rootProducts.size(), rootProducts.size());
        });

        idSortButton.addActionListener(e -> {
            byId = !byId;
            applyFilters();
        });
    }
}
