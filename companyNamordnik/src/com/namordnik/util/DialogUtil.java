package com.namordnik.util;

import javax.swing.*;
import java.awt.*;

public class DialogUtil {
    public static void showError(Component parent, String content) {
        JOptionPane.showMessageDialog(parent, content, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public static void showMessage(Component parent, String content) {
        JOptionPane.showMessageDialog(parent, content, "Информация", JOptionPane.INFORMATION_MESSAGE);
    }
}
