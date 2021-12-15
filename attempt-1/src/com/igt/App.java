package com.igt;

import entities.Service;
import entityManagers.ServiceManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        try {
            List<Service> list = ServiceManager.selectAll();
            for (Service l : list) {
                System.out.println(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/demoAttempt1", "root", "root");
    }
}
