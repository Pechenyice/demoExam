package org.jokerMask.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BaseForm extends JFrame {
    public static String NAME = "GOVNO";
    public static Image ICON = null;

    static {
        try {
            ICON = ImageIO.read(BaseForm.class.getClassLoader().getResource("icon.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BaseForm(int width, int height)  {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(width, height));
        if (ICON != null) {
            setIconImage(ICON);
        }
        setTitle(NAME);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - height / 2);
    }
}
