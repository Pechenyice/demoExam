package entities;

import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@Data
public class Service {
    private int id;
    private String title;
    private int cost;
    private int durationInSeconds;
    private String description;
    private int discount;
    private String imagePath;

    private ImageIcon image;

    public Service(String title, int cost, int durationInSeconds, String description, int discount, String imagePath) {
        this(-1, title, cost, durationInSeconds, description, discount, imagePath);
    }

    public Service(int id, String title, int cost, int durationInSeconds, String description, int discount, String imagePath) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.durationInSeconds = durationInSeconds;
        this.description = description;
        this.discount = discount;
        this.imagePath = imagePath;

        try {
            this.image = new ImageIcon(ImageIO.read(Service.class.getClassLoader().getResource(this.imagePath)).getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
