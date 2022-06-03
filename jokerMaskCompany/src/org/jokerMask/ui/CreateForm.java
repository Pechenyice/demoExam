package org.jokerMask.ui;

import org.jokerMask.App;
import org.jokerMask.entity.Product;
import org.jokerMask.manager.ProductManager;
import org.jokerMask.util.BaseForm;
import org.jokerMask.util.Notification;

import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class CreateForm extends BaseForm {
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

    public CreateForm() {
        super(800, 400);
        setContentPane(mainPanel);

        initButtons();

        setVisible(true);
    }

    public void initButtons() {
        backButton.addActionListener(e -> {
            dispose();
            new MainForm();
        });

        saveButton.addActionListener(e -> {
            String title = titleInput.getText();
            if (title.isEmpty() || title.length() > 100) {
                Notification.error(this, "А можно титле?");
                return;
            }

            String stringDescription = "";
            try {
                Date description = App.DATE_FORMAT.parse(descriptionInput.getText());
                stringDescription = description.toString();
            } catch (ParseException ex) {
                Notification.error(this, "А можно датку?");
                ex.printStackTrace();
            }

            int productionPersonCount = (int) productionPersonCountSpinner.getValue();

            float minCost = -1;
            try {
                minCost = Float.parseFloat(minCostInput.getText());
            } catch (Exception ex) {
                Notification.error(this, "А можно флоат?");
                ex.printStackTrace();
            }

            try {
                ProductManager.insert(new Product(
                        titleInput.getText(),
                        productTypeInput.getText(),
                        articleNumberInput.getText(),
                        descriptionInput.getText(),
                        imagePathInput.getText(),
                        (int) productionPersonCountSpinner.getValue(),
                        (int) productionWorkshopNumberSpinner.getValue(),
                        Float.parseFloat(minCostInput.getText())
                ));
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
