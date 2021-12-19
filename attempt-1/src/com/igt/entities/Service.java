package com.igt.entities;

import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.io.IOException;

@Data
public class Service {
    private int id;
    private String title;
    private int cost;
    private int duration;
    private String description;
    private int discount;
    private String mainImagePath;

    private ImageIcon icon;

    public Service(String title, int cost, int duration, String description, int discount, String mainImagePath) {
        this.title = title;
        this.cost = cost;
        this.duration = duration;
        this.description = description;
        this.discount = discount;
        this.mainImagePath = mainImagePath;

        this.id = -1;
        try {
            this.icon = new ImageIcon(ImageIO.read(Service.class.getClassLoader().getResource(mainImagePath)).getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } catch (Exception e) {
        }
    }

    public Service(int id, String title, int cost, int duration, String description, int discount, String mainImagePath) {
        this(title, cost, duration, description, discount, mainImagePath);
        this.id = id;
    }
}
