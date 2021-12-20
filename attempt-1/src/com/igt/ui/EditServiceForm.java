package com.igt.ui;

import com.igt.entities.Service;
import com.igt.entityManagers.ServiceManager;
import com.igt.util.BaseForm;
import com.igt.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class EditServiceForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField titleInput;
    private JTextField imagePathInput;
    private JTextField descriptionInput;
    private JSpinner costSpinner;
    private JSpinner durationSpinner;
    private JSpinner discountSpinner;
    private JButton addButton;
    private JButton backButton;
    private JTextField idField;
    private JButton deleteButton;

    public EditServiceForm(Service service) {
        super(600, 600);
        setContentPane(mainPanel);

        initButtons(service);

        idField.setText(String.valueOf(service.getId()));
        titleInput.setText(service.getTitle());
        costSpinner.setValue(service.getCost());
        durationSpinner.setValue(service.getDuration());
        descriptionInput.setText(service.getDescription());
        discountSpinner.setValue(service.getDiscount());
        imagePathInput.setText(service.getMainImagePath());

        setVisible(true);
    }

    private void initButtons(Service service) {
        backButton.addActionListener(e -> {
            dispose();
            new MainForm();
        });

        deleteButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить услугу?", "Удаление услуги", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    ServiceManager.delete(service);
                } catch (SQLException ex) {
                    DialogUtil.showError(this, "Не удается удалить услугу!");
                }

                dispose();
                new MainForm();
            }
        });

        addButton.addActionListener(e -> {
            String title = titleInput.getText();
            int cost = (int) costSpinner.getValue();
            int duration = (int) durationSpinner.getValue();
            String description = descriptionInput.getText();
            int discount = (int) discountSpinner.getValue();
            String imagePath = imagePathInput.getText();

            if (title.isEmpty() || title.length() > 100) {
                DialogUtil.showError(this, "Название не введено или введено неверно");
                return;
            }
            if (cost < 0) {
                DialogUtil.showError(this, "Цена введена неверно");
                return;
            }
            if (duration < 0) {
                DialogUtil.showError(this, "Длительность услуги введена неверно");
                return;
            }
            if (description.isEmpty()) {
                DialogUtil.showError(this, "Описание не введено или введено неверно");
                return;
            }
            if (discount < 0) {
                DialogUtil.showError(this, "Скидка введена неверно");
                return;
            }
            if (imagePath.length() > 1000) {
                DialogUtil.showError(this, "Путь до картинки введен неверно");
                return;
            }

            service.setTitle(title);
            service.setCost(cost);
            service.setDuration(duration);
            service.setDescription(description);
            service.setDiscount(discount);
            service.setMainImagePath(imagePath);

            if (JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите отредактировать услугу?", "Редактирование услуги", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    ServiceManager.update(service);
                } catch (SQLException ex) {
                    DialogUtil.showError(this, "Не удается отредактировать услугу!");
                }

                dispose();
                new MainForm();
            }
        });
    }
}
