package org.org;

import org.org.manager.Auth;
import org.org.test.Calculation;
import org.org.ui.AuthForm;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

public class App {
    public static SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static boolean IS_ADMIN = false;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new AuthForm();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "root");
    }
}
