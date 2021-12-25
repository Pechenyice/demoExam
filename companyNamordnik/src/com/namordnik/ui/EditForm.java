package com.namordnik.ui;

import com.namordnik.entity.Product;
import com.namordnik.manager.ProductManager;
import com.namordnik.util.BaseForm;
import com.namordnik.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class EditForm extends BaseForm {
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
    private JTextField idField;
    private JButton deleteButton;

    private Product product;

    public EditForm(int width, int height, Product product) {
        super(width, height);
        this.product = product;
        setContentPane(mainPanel);

        initButtons();
        initFields();

        setVisible(true);
    }

    private void initFields() {
        idField.setText(String.valueOf(product.getId()));
    }

    private void initButtons() {
        backButton.addActionListener(e -> {
            dispose();
            new MainForm(1200, 500);
        });

        deleteButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить?", "Удаление", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    ProductManager.delete(product);
                    dispose();
                    new MainForm(1200, 500);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
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

            product.setTitle(title);
            product.setProductType(type);
            product.setArticleNumber(article);
            product.setDescription(description);
            product.setImagePath(imagePath);
            product.setProductionPersonCount(peopleCount);
            product.setProductionWorkshopNumber(workCount);
            product.setMinCostForAgent(Double.parseDouble(minPrice));

            try {
                ProductManager.update(
                        product
                );
            } catch (SQLException ex) {
                DialogUtil.showError(this, "Не удалось изменить продукт");
                return;
            }

            dispose();
            new MainForm(1200, 500);
        });
    }
}
