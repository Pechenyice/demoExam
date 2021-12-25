package com.namordnik.entity;

import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.regex.Pattern;

@Data
public class Product {
    private int id;
    private String title;
    private String productType;
    private String articleNumber;
    private String description;
    private String imagePath;
    private int productionPersonCount;
    private int productionWorkshopNumber;
    private double minCostForAgent;

    private ImageIcon image;

    public Product(int id, String title, String productType, String articleNumber, String description, String imagePath, int productionPersonCount, int productionWorkshopNumber, double minCostForAgent) {
        this.id = id;
        this.title = title;
        this.productType = productType;
        this.articleNumber = articleNumber;
        this.description = description;
        this.imagePath = imagePath;
        this.productionPersonCount = productionPersonCount;
        this.productionWorkshopNumber = productionWorkshopNumber;
        this.minCostForAgent = minCostForAgent;

        this.imagePath = this.imagePath.replaceAll(Pattern.quote("\\"), "/");

        try {
            this.image = new ImageIcon(ImageIO.read(Product.class.getClassLoader().getResource(this.imagePath)).getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } catch (Exception e) {
            try {
                this.image = new ImageIcon(ImageIO.read(Product.class.getClassLoader().getResource("picture.png")).getScaledInstance(50,50, Image.SCALE_DEFAULT));
            } catch (Exception err) {

            }
        }
    }

    public Product(String title, String productType, String articleNumber, String description, String imagePath, int productionPersonCount, int productionWorkshopNumber, double minCostForAgent) {
        this(-1, title, productType, articleNumber, description, imagePath, productionPersonCount, productionWorkshopNumber, minCostForAgent);
    }
}
