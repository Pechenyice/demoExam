package entityManagers;

import com.igt.App;
import entities.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceManager {

    public static void insert(Service service) throws SQLException {
        try (Connection c = App.getConnection()) {
            String sql = "insert into Service(Title, Cost, DurationInSeconds, Description, Discount, MainImagePath) values (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, service.getTitle());
            ps.setInt(2, service.getCost());
            ps.setInt(3, service.getDurationInSeconds());
            ps.setString(4, service.getDescription());
            ps.setInt(5, service.getDiscount());
            ps.setString(6, service.getImagePath());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if (keys.next()) {
                service.setId(keys.getInt(1));
                return;
            }

            throw new SQLException("Cannot insert Service");
        }

    }

    public static List<Service> selectAll() throws SQLException {
        List<Service> services = new ArrayList<>();
        try (Connection c = App.getConnection()) {
            String sql = "select * from Service;";
            Statement s = c.createStatement();

            ResultSet keys = s.executeQuery(sql);

            while (keys.next()) {
                services.add(
                        new Service(
                                keys.getInt("ID"),
                                keys.getString("Title"),
                                keys.getInt("Cost"),
                                keys.getInt("DurationInSeconds"),
                                keys.getString("Description"),
                                keys.getInt("Discount"),
                                keys.getString("MainImagePath")
                        )
                );
            }

            return services;
        }
    }

    public static void update(Service service) throws SQLException {
        try (Connection c = App.getConnection()) {
            String sql = "update Service set Title=?, Cost=?, DurationInSeconds=?, Description=?, Discount=?, MainImagePath=? where ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, service.getTitle());
            ps.setInt(2, service.getCost());
            ps.setInt(3, service.getDurationInSeconds());
            ps.setString(4, service.getDescription());
            ps.setInt(5, service.getDiscount());
            ps.setString(6, service.getImagePath());
            ps.setInt(7, service.getId());

            ps.executeUpdate();
        }
    }

    public static void delete(Service service) throws SQLException {
        try (Connection c = App.getConnection()) {
            String sql = "delete from Service where ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, service.getId());

            ps.executeUpdate();
        }
    }
}
