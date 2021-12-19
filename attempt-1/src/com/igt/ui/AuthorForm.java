package com.igt.ui;

import com.igt.util.BaseForm;

import javax.swing.*;

public class AuthorForm extends BaseForm {
    private JPanel mainPanel;
    private JTextArea authorText;
    private JButton backButton;

    public AuthorForm() {
        super(400, 400);

        setContentPane(mainPanel);

        backButton.addActionListener(e -> {
            dispose();
            new MainForm();
        });

        setVisible(true);
    }
}
