package org.jokerMask.manager;

import org.jokerMask.App;
import org.jokerMask.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    public static void insert(Product product) throws SQLException {
        String sql = "insert into Product(title, productType, articleNumber, description, image, productionPersonCount, productionWorkshopNumber, minCostForAgent) values (?,?,?,?,?,?,?,?)";

        try (Connection c = App.getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, product.getTitle());
            ps.setString(2, product.getProductType());
            ps.setString(3, product.getArticleNumber());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getImagePath());
            ps.setInt(6, product.getProductionPersonCount());
            ps.setInt(7, product.getProductionWorkshopNumber());
            ps.setFloat(8, product.getMinCostForAgent());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if (keys.next()) {
                product.setId(keys.getInt(1));
            }
        }
    }

    public static void update(Product product) throws SQLException {
        String sql = "update Product set title=?, productType=?, articleNumber=?, description=?, imagePath=?, productionPersonCount=?, productionWorkshopNumber=?, minCostForAgent=? where id=?";

        try (Connection c = App.getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, product.getTitle());
            ps.setString(2, product.getProductType());
            ps.setString(3, product.getArticleNumber());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getImagePath());
            ps.setInt(6, product.getProductionPersonCount());
            ps.setInt(7, product.getProductionWorkshopNumber());
            ps.setFloat(8, product.getMinCostForAgent());
            ps.setInt(9, product.getId());

            ps.executeUpdate();
        }
    }

    public static void delete(Product product) throws SQLException {
        String sql = "delete from Product where id=?";

        try (Connection c = App.getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setInt(1, product.getId());

            ps.executeUpdate();
        }
    }

    public static List<Product> selectAll() throws SQLException {
        String sql = "select * from Product";

        try (Connection c = App.getConnection()) {
            Statement s = c.createStatement();

            ResultSet keys = s.executeQuery(sql);

            List<Product> internal = new ArrayList<>();

            while (keys.next()) {
                internal.add(new Product(
                        keys.getInt("ID"),
                        keys.getString("Title"),
                        keys.getString("ProductType"),
                        keys.getString("ArticleNumber"),
                        keys.getString("Description"),
                        keys.getString("Image"),
                        keys.getInt("ProductionPersonCount"),
                        keys.getInt("ProductionWorkshopNumber"),
                        keys.getFloat("MinCostForAgent")
                ));
            }

            return internal;
        }
    }
}
