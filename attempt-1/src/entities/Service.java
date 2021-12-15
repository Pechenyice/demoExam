package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    private int id;
    private String title;
    private int cost;
    private int durationInSeconds;
    private String description;
    private int discount;
    private String imagePath;

}
