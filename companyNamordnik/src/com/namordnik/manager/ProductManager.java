package com.namordnik.manager;

import com.namordnik.Main;
import com.namordnik.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    public static void insert(Product product) throws SQLException {
        String sql = "insert into product (Title, ProductTypeID, ArticleNumber, Description, Image, ProductionPersonCount, ProductionWorkshopNumber, MinCostForAgent) values (?,?,?,?,?,?,?,?)";
        try (Connection c = Main.getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getTitle());
            ps.setString(2, product.getProductType());
            ps.setString(3, product.getArticleNumber());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getImagePath());
            ps.setInt(6, product.getProductionPersonCount());
            ps.setInt(7, product.getProductionWorkshopNumber());
            ps.setDouble(8, product.getMinCostForAgent());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if (keys.next()) {
                product.setId(keys.getInt(1));
                return;
            }

            throw new SQLException("Не удалось добавить продукт!");
        }
    }

    public static List<Product> selectAll() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "select * from product";
        try (Connection c = Main.getConnection()) {
            Statement s = c.createStatement();
            ResultSet results = s.executeQuery(sql);
            while (results.next()) {
                products.add(
                        new Product(
                                results.getInt("id"),
                                results.getString("title"),
                                results.getString("productTypeId"),
                                results.getString("articleNumber"),
                                results.getString("description"),
                                results.getString("image"),
                                results.getInt("productionPersonCount"),
                                results.getInt("productionWorkshopNumber"),
                                results.getDouble("minCostForAgent")
                        )
                );
            }
            return products;
        }
    }

    public static void update(Product product) throws SQLException {
        String sql = "update product set Title=?, ProductTypeID=?, ArticleNumber=?, Description=?, Image=?, ProductionPersonCount=?, ProductionWorkshopNumber=?, MinCostForAgent=? where ID=?";
        try (Connection c = Main.getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, product.getTitle());
            ps.setString(2, product.getProductType());
            ps.setString(3, product.getArticleNumber());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getImagePath());
            ps.setInt(6, product.getProductionPersonCount());
            ps.setInt(7, product.getProductionWorkshopNumber());
            ps.setDouble(8, product.getMinCostForAgent());
            ps.setInt(9, product.getId());

            ps.executeUpdate();
        }
    }

    public static void delete(Product product) throws SQLException {
        String sql = "delete from product where id=?";
        try (Connection c = Main.getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, product.getId());

            ps.executeUpdate();
        }
    }
}
