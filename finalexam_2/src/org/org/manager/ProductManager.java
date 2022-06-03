package org.org.manager;

import org.org.App;
import org.org.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    public static List<Product> selectAll() throws SQLException {
        String sql = "select * from product";

        List<Product> products = new ArrayList<>();
        try (Connection c = App.getConnection()) {
            Statement s = c.createStatement();

            ResultSet keys = s.executeQuery(sql);

            while (keys.next()) {
                products.add(new Product(
                        keys.getInt("ID"),
                        keys.getString("Title"),
                        keys.getString("ProductType"),
                        keys.getString("Image"),
                        keys.getDouble("Cost"),
                        keys.getTimestamp("RegisterDate")
                ));
            }
        }

        return products;
    }

    public static void insert(Product product) throws SQLException {
        String sql = "insert into product(title, productType, image, cost, registerDate) values (?, ?, ?, ?, ?)";

        try (Connection c = App.getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, product.getTitle());
            ps.setString(2, product.getProductType());
            ps.setString(3, product.getImagePath());
            ps.setDouble(4, product.getCost());
            ps.setTimestamp(5, new Timestamp(product.getRegisterDate().getTime()));

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if (keys.next()) {
                product.setId(keys.getInt(1));
            }
        }
    }

    public static void update(Product product) throws SQLException {
        String sql = "update product set title=?, productType=?, image=?, cost=?, registerDate=? where id=?";

        try (Connection c = App.getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, product.getTitle());
            ps.setString(2, product.getProductType());
            ps.setString(3, product.getImagePath());
            ps.setDouble(4, product.getCost());
            ps.setTimestamp(5, new Timestamp(product.getRegisterDate().getTime()));
            ps.setInt(6, product.getId());

            ps.executeUpdate();
        }
    }

    public static void delete(Product product) throws SQLException {
        String sql = "delete from product where id=?";

        try (Connection c = App.getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setInt(1, product.getId());

            ps.executeUpdate();
        }
    }
}
