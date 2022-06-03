package org.org.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BaseForm extends JFrame {
    public static String NAME = "NAME";
    public static Image IMAGE = null;

    static {
        try {
            IMAGE = ImageIO.read(BaseForm.class.getClassLoader().getResource("icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
            Notification.showError(null, "Говно");
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
