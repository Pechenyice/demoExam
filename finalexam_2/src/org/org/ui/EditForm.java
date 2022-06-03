package org.org.ui;

import org.org.App;
import org.org.entity.Product;
import org.org.manager.ProductManager;
import org.org.util.BaseForm;
import org.org.util.Notification;

import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class EditForm extends BaseForm {
    private JTextField titleInput;
    private JTextField typeInput;
    private JTextField imagePathInput;
    private JTextField costInput;
    private JTextField registerDateInput;
    private JButton editButton;
    private JPanel mainPanel;
    private JTextField idInput;
    private JButton backButton;
    private JButton deleteButton;

    private Product product;

    public EditForm(Product product) {
        super(800, 500);
        this.product = product;

        setContentPane(mainPanel);

        initFields();
        initButtons();

        setVisible(true);
    }

    public void initFields() {
        idInput.setText(String.valueOf(product.getId()));
        titleInput.setText(product.getTitle());
        typeInput.setText(product.getProductType());
        imagePathInput.setText(product.getImagePath());
        costInput.setText(String.valueOf(product.getCost()));
        registerDateInput.setText(String.valueOf(product.getRegisterDate()));
    }

    public void initButtons() {
        backButton.addActionListener(e -> {
            dispose();
            new MainForm();
        });

        deleteButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Вы удаляете?", "Вопрос", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) return;
            try {
                ProductManager.delete(product);
            } catch (SQLException ex) {
                ex.printStackTrace();
                Notification.showError(this, "Не удалилось");
                return;
            }

            dispose();
            new MainForm();
        });

        editButton.addActionListener(e -> {
            String title = titleInput.getText();
            if (title.isEmpty() || title.length() > 100) {
                Notification.showError(this, "Нет");
                return;
            }

            String type = typeInput.getText();
            if (type.isEmpty() || type.length() > 100) {
                Notification.showError(this, "Нет");
                return;
            }

            String imagePath = imagePathInput.getText();
            if (imagePath.isEmpty() || imagePath.length() > 100) {
                Notification.showError(this, "Нет");
                return;
            }

            Double cost = Double.valueOf(-1);
            try {
                cost = Double.parseDouble(costInput.getText());
            } catch (Exception ex) {
                Notification.showError(this, "Нет");
                return;
            }
            if (cost < 0) {
                Notification.showError(this, "Нет");
                return;
            }

            Date date;
            try {
                date = App.DateFormat.parse(registerDateInput.getText());
            } catch (ParseException ex) {
                ex.printStackTrace();
                Notification.showError(this, "Нет");
                return;
            }

            product.setTitle(title);
            product.setProductType(type);
            product.setImagePath(imagePath);
            product.setCost(cost);
            product.setRegisterDate(date);

            try {
                ProductManager.update(product);
            } catch (SQLException ex) {
                ex.printStackTrace();
                Notification.showError(this, "Не обновилось");
                return;
            }

            Notification.showInfo(this, "Обновилось");

            dispose();
            new MainForm();
        });
    }
}
