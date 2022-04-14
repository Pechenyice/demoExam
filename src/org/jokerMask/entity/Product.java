package org.jokerMask.entity;

import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.regex.Pattern;

@Data
public class Product {
    public int id;
    public String title;
    public String productType;
    public String articleNumber;
    public String description;
    public String imagePath;
    public int productionPersonCount;
    public int productionWorkshopNumber;
    public float minCostForAgent;

    public ImageIcon image;

    public Product(int id, String title, String productType, String articleNumber, String description, String imagePath, int productionPersonCount, int productionWorkshopNumber, float minCostForAgent) {
        this.id = id;
        this.title = title;
        this.productType = productType;
        this.articleNumber = articleNumber;
        this.description = description;
        this.imagePath = imagePath;
        this.productionPersonCount = productionPersonCount;
        this.productionWorkshopNumber = productionWorkshopNumber;
        this.minCostForAgent = minCostForAgent;

        try {
            this.image = new ImageIcon(ImageIO.read(Product.class.getClassLoader().getResource(imagePath.replaceAll(Pattern.quote("\\"), "/").replaceAll(Pattern.quote("продукты"), "products").substring(1))).getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } catch (Exception e) {
            try {
                this.image = new ImageIcon(ImageIO.read(Product.class.getClassLoader().getResource("picture.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            } catch (Exception err) {

            }
        }
    }

    public Product(String title, String productType, String articleNumber, String description, String imagePath, int productionPersonCount, int productionWorkshopNumber, float minCostForAgent) {
        this(-1, title, productType, articleNumber, description, imagePath, productionPersonCount, productionWorkshopNumber, minCostForAgent);
    }
}
