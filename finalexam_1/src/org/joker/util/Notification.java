package org.joker.util;

import javax.swing.*;
import java.awt.*;

public class Notification {
    public static void showInfo(Component parent, String text) {
        JOptionPane.showMessageDialog(parent, text, "Информация", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(Component parent, String text) {
        JOptionPane.showMessageDialog(parent, text, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}
