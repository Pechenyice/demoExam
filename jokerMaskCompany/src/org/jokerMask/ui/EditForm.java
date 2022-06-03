package org.jokerMask.ui;

import org.jokerMask.entity.Product;
import org.jokerMask.manager.ProductManager;
import org.jokerMask.util.BaseForm;
import org.jokerMask.util.Notification;

import javax.swing.*;
import java.sql.SQLException;

public class EditForm extends BaseForm {
    private JTextField productTypeInput;
    private JTextField articleNumberInput;
    private JTextField descriptionInput;
    private JTextField imagePathInput;
    private JSpinner productionPersonCountSpinner;
    private JSpinner productionWorkshopNumberSpinner;
    private JTextField minCostInput;
    private JTextField titleInput;
    private JButton backButton;
    private JButton saveButton;
    private JPanel mainPanel;
    private JTextField idInput;
    private JButton deletButton;

    private Product product;

    public EditForm(Product product) {
        super(800, 400);

        this.product = product;

        setContentPane(mainPanel);

        initDeafultValues();
        initButtons();

        setVisible(true);
    }

    public void initDeafultValues() {
        idInput.setText(String.valueOf(product.getId()));
    }

    public void initButtons() {
        backButton.addActionListener(e -> {
            dispose();
            new MainForm();
        });

        deletButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Привет") == JOptionPane.YES_OPTION) {
                try {
                    ProductManager.delete(product);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                dispose();
                new MainForm();
            }
        });

        saveButton.addActionListener(e -> {

            try {
                ProductManager.update(product);
            } catch (SQLException ex) {
                Notification.error(this, "Не вышло");
                ex.printStackTrace();
            }

            Notification.info(this, "Вышло");
            dispose();
            new MainForm();
        });
    }
}
