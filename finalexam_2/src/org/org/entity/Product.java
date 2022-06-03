package org.org.entity;

import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Date;

@Data
public class Product {
    private int id;
    private String title;
    private String productType;
    private String imagePath;
    private double cost;
    private Date registerDate;

    private ImageIcon image;

    public Product(int id, String title, String productType, String imagePath, double cost, Date registerDate) {
        this.id = id;
        this.title = title;
        this.productType = productType;
        this.imagePath = imagePath;
        this.cost = cost;
        this.registerDate = registerDate;

        try {
            this.image = new ImageIcon(ImageIO.read(Product.class.getClassLoader().getResource(this.imagePath)).getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } catch (Exception e) {
            try {
                this.image = new ImageIcon(ImageIO.read(Product.class.getClassLoader().getResource("picture.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public Product(String title, String productType, String imagePath, double cost, Date registerDate) {
        this(-1, title, productType, imagePath, cost, registerDate);
    }
}
