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

public class CreateForm extends BaseForm {
    private JTextField titleInput;
    private JTextField typeInput;
    private JTextField imagePathInput;
    private JTextField costInput;
    private JTextField registerDateInput;
    private JButton createButton;
    private JPanel mainPanel;

    public CreateForm() {
        super(800, 500);
        setContentPane(mainPanel);

        initButtons();

        setVisible(true);
    }

    public void initButtons() {
        createButton.addActionListener(e -> {
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

            try {
                ProductManager.insert(new Product(
                        -1,
                        title,
                        type,
                        imagePath,
                        cost,
                        date
                ));
            } catch (SQLException ex) {
                ex.printStackTrace();
                Notification.showError(this, "Не добавилось");
                return;
            }

            Notification.showInfo(this, "Добавилось");

            dispose();
            new MainForm();
        });
    }
}
