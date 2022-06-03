package org.org.manager;

import org.org.App;

import java.sql.*;

public class Auth {
    public static int auth(String login, String password) throws SQLException {
        String sql = "select * from logins where login=? and password=?";

        try (Connection c = App.getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, login);
            ps.setString(2, password);

            ResultSet keys = ps.executeQuery();

            if (keys.next()) {
                if ("Администратор".equals(keys.getString("role"))) {
                    return 2;
                } else {
                    return 1;
                }
            } else {
                return 0;
            }
        }
    }
}
