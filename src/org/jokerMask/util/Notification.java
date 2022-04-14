package org.jokerMask.util;

import javax.swing.*;
import java.awt.*;

public class Notification {
    public static void info(Component c, String m) {
        JOptionPane.showMessageDialog(c, m, "Информация", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(Component c, String m) {
        JOptionPane.showMessageDialog(c, m, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}
