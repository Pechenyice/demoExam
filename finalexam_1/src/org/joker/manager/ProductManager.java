package org.joker.manager;

import org.joker.App;
import org.joker.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    public static List<Product> selectAll() throws SQLException {
        String sql = "select * from product";

        List<Product> products = new ArrayList<>();

        try (Connection connection = App.getConnection()) {
             Statement s = connection.createStatement();

             ResultSet keys = s.executeQuery(sql);

             while (keys.next()) {
                 products.add(new Product(
                         keys.getInt("ID"),
                         keys.getString("Title"),
                         keys.getString("ArticleNumber"),
                         keys.getDouble("MinCostForAgent"),
                         keys.getString("Image"),
                         keys.getString("ProductType"),
                         keys.getInt("ProductionPersonCount"),
                         keys.getInt("ProductionWorkshopNumber")
                 ));
             }
        }

        return products;
    }

    public static void create(Product product) throws SQLException {
        String sql = "insert into product(Title, ArticleNumber, MinCostForAgent, Image, ProductType, ProductionPersonCount, ProductionWorkshopNumber) values (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = App.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, product.getTitle());
            ps.setString(2, product.getArticleNumber());
            ps.setDouble(3, product.getMinCostForAgent());
            ps.setString(4, product.getImagePath());
            ps.setString(5, product.getProductType());
            ps.setInt(6, product.getProductionPersonCount());
            ps.setInt(7, product.getProductionWorkshopNumber());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if (keys.next()) {
                product.setId(keys.getInt("ID"));
            }
        }
    }

    public static void update(Product product) throws SQLException {
        String sql = "update product set Title=?, ArticleNumber=?, MinCostForAgent=?, Image=?, ProductType=?, ProductionPersonCount=?, ProductionWorkshopNumber=? where ID=?";

        try (Connection connection = App.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, product.getTitle());
            ps.setString(2, product.getArticleNumber());
            ps.setDouble(3, product.getMinCostForAgent());
            ps.setString(4, product.getImagePath());
            ps.setString(5, product.getProductType());
            ps.setInt(6, product.getProductionPersonCount());
            ps.setInt(7, product.getProductionWorkshopNumber());
            ps.setInt(8, product.getId());

            ps.executeUpdate();
        }
    }

    public static void delete(Product product) throws SQLException {
        String sql = "delete from product where id=?";

        try (Connection connection = App.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, product.getId());

            ps.executeUpdate();
        }
    }
}
