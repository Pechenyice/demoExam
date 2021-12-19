package com.igt.ui;

import com.igt.entities.Service;
import com.igt.entityManagers.ServiceManager;
import com.igt.util.BaseForm;
import com.igt.util.CustomTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainForm extends BaseForm {
    private JPanel mainPanel;
    private JTable servicesTable;
    private JButton idSortButton;
    private JButton nameSortButton;
    private JComboBox costCombo;
    private JComboBox discountCombo;
    private JLabel rowsCountText;
    private JButton addServiceButton;
    private JTextPane hintText;
    private JButton authorButton;
    private CustomTableModel<Service> model;

    private List<Service> rootServices;

    private boolean sortedById;
    private boolean sortedByName;

    public MainForm() {
        super(1200, 700);
        setContentPane(mainPanel);

        try {
            rootServices = ServiceManager.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        initTable();
        initBoxes();
        initButtons();


        setVisible(true);
    }

    private void initTable() {
        try {
            model = new CustomTableModel<Service>(
                    ServiceManager.selectAll(),
                    Service.class,
                    new String[] {"id", "Название", "Цена", "Длительность", "Описание", "Скидка", "Путь до картинки", "Картинка"}
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        servicesTable.setModel(model);
        servicesTable.getTableHeader().setReorderingAllowed(false);

        TableColumn column = servicesTable.getColumn("Путь до картинки");
        column.setPreferredWidth(0);
        column.setMaxWidth(0);
        column.setMinWidth(0);
        column.setWidth(0);

        servicesTable.setRowHeight(50);
        sortedById = true;
        sortedByName = false;
        updateCount(servicesTable.getRowCount(), servicesTable.getRowCount());
    }

    private void initButtons() {
        addServiceButton.addActionListener(e -> {
            dispose();
            new AddServiceForm();
        });

        authorButton.addActionListener(e -> {
            dispose();
            new AuthorForm();
        });

        idSortButton.addActionListener(e -> {
            if (!sortedById) {
                model.getRows().sort(new Comparator<Service>() {
                    @Override
                    public int compare(Service o1, Service o2) {
                        return o1.getId() >= o2.getId() ? 1 : -1;
                    }
                });
            } else {
                model.getRows().sort(new Comparator<Service>() {
                    @Override
                    public int compare(Service o1, Service o2) {
                        return o2.getId() >= o1.getId() ? 1 : -1;
                    }
                });
            }
            sortedById = !sortedById;
            sortedByName = false;
            model.fireTableDataChanged();
        });

        nameSortButton.addActionListener(e -> {
            if (sortedByName) {
                model.getRows().sort(new Comparator<Service>() {
                    @Override
                    public int compare(Service o1, Service o2) {
                        return o1.getTitle().compareTo(o2.getTitle());
                    }
                });
            } else {
                model.getRows().sort(new Comparator<Service>() {
                    @Override
                    public int compare(Service o1, Service o2) {
                        return o2.getTitle().compareTo(o1.getTitle());
                    }
                });
            }
            sortedById = false;
            sortedByName = !sortedByName;
            model.fireTableDataChanged();
        });
    }

    private void initBoxes() {
        costCombo.addItem("Все");
        costCombo.addItem("< 1000");
        costCombo.addItem("1000-1500");
        costCombo.addItem("1500-2000");
        costCombo.addItem("> 2000");

        costCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                applyFilters();
            }
        });

        discountCombo.addItem("Все");
        discountCombo.addItem("0");
        discountCombo.addItem("1-10");
        discountCombo.addItem("11-20");
        discountCombo.addItem("21+");

        discountCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                applyFilters();
            }
        });
    }

    private void applyFilters() {
        List<Service> services = new ArrayList<Service>(rootServices);

        int costIndex = costCombo.getSelectedIndex();
        int discountIndex = discountCombo.getSelectedIndex();

        switch (costIndex) {
            case 0: {
                break;
            }
            case 1: {
                services.removeIf(service -> service.getCost() >= 1000);
                break;
            }
            case 2: {
                services.removeIf(service -> service.getCost() <= 1000 || service.getCost() > 1500);
                break;
            }
            case 3: {
                services.removeIf(service -> service.getCost() <= 1500 || service.getCost() > 2000);
                break;
            }
            case 4: {
                services.removeIf(service -> service.getCost() <= 2000);
                break;
            }
        }

        switch (discountIndex) {
            case 0: {
                break;
            }
            case 1: {
                services.removeIf(service -> service.getDiscount() > 0);
                break;
            }
            case 2: {
                services.removeIf(service -> service.getDiscount() <= 0 || service.getDiscount() > 10);
                break;
            }
            case 3: {
                services.removeIf(service -> service.getDiscount() <= 10 || service.getDiscount() > 20);
                break;
            }
            case 4: {
                services.removeIf(service -> service.getDiscount() <= 20);
                break;
            }
        }

        model.setRows(services);
        model.fireTableDataChanged();

        updateCount(services.size(), services.size());
    }

    private void updateCount(int current, int max) {
        rowsCountText.setText("Отображено записей: " + current + " / " + max);
    }
}
