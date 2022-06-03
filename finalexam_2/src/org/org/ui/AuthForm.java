package org.org.ui;

import org.org.App;
import org.org.manager.Auth;
import org.org.util.BaseForm;
import org.org.util.Notification;

import javax.swing.*;
import java.sql.SQLException;

public class AuthForm extends BaseForm {
    private JButton guestButton;
    private JButton authButton;
    private JTextField login;
    private JPasswordField password;
    private JPanel mainPanel;

    public AuthForm() {
        super(800, 500);
        setContentPane(mainPanel);

        initButtons();

        setVisible(true);
    }

    public void initButtons() {
        guestButton.addActionListener(e -> {
            dispose();
            new MainForm();
        });

        authButton.addActionListener(e -> {
            int authResult = 0;
            try {
                authResult = Auth.auth(login.getText(), new String(password.getPassword()));
            } catch (SQLException ex) {
                ex.printStackTrace();
                dispose();
            new MainForm();
                return;
            }
            if (authResult == 2) {
                App.IS_ADMIN = true;
            }
            if (authResult == 0) {
                Notification.showError(this, "Такого пользователя нет");
                return;
            }
            dispose();
            new MainForm();
        });
    }
}
