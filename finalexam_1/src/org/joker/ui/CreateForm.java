package org.joker.ui;

import org.joker.entity.Product;
import org.joker.manager.ProductManager;
import org.joker.util.BaseForm;
import org.joker.util.Notification;

import javax.swing.*;

public class CreateForm extends BaseForm {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JTextArea textArea1;
    private JPanel mainPanel;
    private JButton createButton;

    public CreateForm() {
        super(800, 400);

        setContentPane(mainPanel);

        initButtons();

        setVisible(true);
    }

    public void initButtons() {
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
