package com.igt.util;

import javax.swing.*;
import java.awt.*;

public class DialogUtil {
    public static void showMessage(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Сообщение", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void showMessage(String message) {
        showMessage(null, message);
    }
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
    public static void showError(String message) {
        showError(null, message);
    }
}
