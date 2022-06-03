package org.joker.ui;

import org.joker.entity.Product;
import org.joker.manager.ProductManager;
import org.joker.util.BaseForm;
import org.joker.util.Notification;

import javax.swing.*;
import java.sql.SQLException;

public class EditForm extends BaseForm {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JTextArea textArea1;
    private JPanel mainPanel;
    private JButton createButton;
    private JButton deleteButton;
    private JTextField textField4;

    private Product product;

    public EditForm(Product product) {
        super(800, 400);

        this.product = product;

        setContentPane(mainPanel);

        initFields();
        initButtons();

        setVisible(true);
    }

    public void initFields() {
        textField1.setText(product.getTitle());
    }

    public void initButtons() {
        deleteButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Yes?", "con", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    ProductManager.delete(product);

                    dispose();

                    new MainForm();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        createButton.addActionListener(e -> {
            if (textField1.getText().isEmpty()) {
                Notification.showError(this, "text");
                return;
            }

            if ((int) spinner1.getValue() < 0) {
                Notification.showError(this, "text");
                return;
            }

            double test = -1;
            try {
                test = Double.parseDouble(textField2.getText());
            } catch (Exception ex) {
                Notification.showError(this, "text");
                return;
            }
            if (test < -1) {
                Notification.showError(this, "text");
                return;
            }

//            ProductManager.create(new Product(textArea1.getText()));

            dispose();
            new MainForm();
        });
    }
}
