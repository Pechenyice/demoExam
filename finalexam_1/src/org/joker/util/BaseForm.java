package org.joker.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;

public class BaseForm extends JFrame {
    static String NAME = "Jocker";
    static Image IMAGE = null;

    static {
        try {
            IMAGE = ImageIO.read(BaseForm.class.getClassLoader().getResource("icon.png"));
        } catch (Exception e) {
            Notification.showError(null, "Картинки не ма");
            e.printStackTrace();
        }
    }

    public BaseForm(int width, int height) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(width, height));
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - height / 2);

        setTitle(NAME);
        if (IMAGE != null) {
            setIconImage(IMAGE);
        }
    }
}
