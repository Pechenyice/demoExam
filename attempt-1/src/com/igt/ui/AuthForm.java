package com.igt.ui;

import com.igt.util.BaseForm;
import com.igt.util.DialogUtil;

import javax.swing.*;

public class AuthForm extends BaseForm {
    private JPanel mainPanel;
    private JButton authButton;
    private JTextField passwordInput;

    public AuthForm() {
        super(400, 200);

        setContentPane(mainPanel);

        authButton.addActionListener(e -> {
            String password = passwordInput.getText();
            if (password.equals("0000")) {
                dispose();
                new MainForm();
            } else {
                DialogUtil.showError(this, "Пароль администратора введен неверно!");
            }
        });

        setVisible(true);
    }
}
