package com.namordnik.ui;

import com.namordnik.entity.Product;
import com.namordnik.manager.ProductManager;
import com.namordnik.util.BaseForm;
import com.namordnik.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class AddForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField titleField;
    private JTextField priceField;
    private JTextField imagePathField;
    private JTextField descriptionField;
    private JTextField articleField;
    private JTextField typeField;
    private JButton backButton;
    private JButton addButton;
    private JSpinner peopleField;
    private JSpinner workField;

    public AddForm(int width, int height) {
        super(width, height);
        setContentPane(mainPanel);

        initButtons();

        setVisible(true);
    }

    private void initButtons() {
        backButton.addActionListener(e -> {
            dispose();
            new MainForm(1200, 500);
        });

        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String type = typeField.getText();
            String article = articleField.getText();
            String description = descriptionField.getText();
            String imagePath = imagePathField.getText();
            int peopleCount = (int) peopleField.getValue();
            int workCount = (int) workField.getValue();
            String minPrice = priceField.getText();

            try {
                if (minPrice.isEmpty() || Double.isNaN(Double.parseDouble(minPrice))) {
                    DialogUtil.showError(this, "Некорректный татйдазыв");
                    return;
                }
            } catch (Exception err) {
                DialogUtil.showError(this, "Некорректный татйдазыв");
                return;
            }

            try {
                ProductManager.insert(
                        new Product(
                                title,
                                type,
                                article,
                                description,
                                imagePath,
                                peopleCount,
                                workCount,
                                Double.parseDouble(minPrice)
                        )
                );
            } catch (SQLException ex) {
                DialogUtil.showError(this, "Не удалось добавить продукт");
                return;
            }

            dispose();
            new MainForm(1200, 500);
        });
    }
}
