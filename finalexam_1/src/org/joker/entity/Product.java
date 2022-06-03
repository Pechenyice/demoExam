package org.joker.entity;

import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@Data
public class Product {
    private int id;
    private String title;
    private String articleNumber;
    private double minCostForAgent;
    private String imagePath;
    private String productType;
    private int productionPersonCount;
    private int productionWorkshopNumber;

    private ImageIcon image;

    public Product(int id, String title, String articleNumber, double minCostForAgent, String imagePath, String productType, int productionPersonCount, int productionWorkshopNumber) {
        this.id = id;
        this.title = title;
        this.articleNumber = articleNumber;
        this.minCostForAgent = minCostForAgent;
        this.imagePath = imagePath;
        this.productType = productType;
        this.productionPersonCount = productionPersonCount;
        this.productionWorkshopNumber = productionWorkshopNumber;

        try {
            this.image = new ImageIcon(ImageIO.read(Product.class.getClassLoader().getResource(this.imagePath.substring(1))).getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } catch (Exception e) {
            try {
                this.image = new ImageIcon(ImageIO.read(Product.class.getClassLoader().getResource("picture.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            } catch (Exception ex) {
            }
        }
    }

    public Product(String title, String articleNumber, double minCostForAgent, String imagePath, String productType, int productionPersonCount, int productionWorkshopNumber) {
        this(-1,title, articleNumber, minCostForAgent, imagePath, productType, productionPersonCount, productionWorkshopNumber);
    }
}
