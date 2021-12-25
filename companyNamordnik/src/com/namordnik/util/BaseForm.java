package com.namordnik.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BaseForm extends JFrame {
    public static String APP_NAME = "Магазин намордник";
    public static Image APP_IMAGE;

    static {
        try {
            APP_IMAGE = ImageIO.read(BaseForm.class.getClassLoader().getResource("Намордник.png"));
        } catch (Exception e) {
            DialogUtil.showError(null, "Не удалось загрузить иконку приложения");
        }
    }
    public BaseForm(int width, int height) {
        setTitle(APP_NAME);
        setIconImage(APP_IMAGE);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - height / 2
        );
        setMinimumSize(new Dimension(width, height));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
