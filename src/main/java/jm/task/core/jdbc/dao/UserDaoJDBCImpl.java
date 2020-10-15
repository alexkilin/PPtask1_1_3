package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public Connection mySQLConnection;


    public UserDaoJDBCImpl() {
        try {
            this.mySQLConnection = Util.getMySQLConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (id BIGINT NOT NULL AUTO_INCREMENT," +
                " name VARCHAR(40) NOT NULL, lastName VARCHAR(40) NOT NULL," +
                " age TINYINT,PRIMARY KEY (id))";
        try {
            mySQLConnection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = " DROP TABLE IF EXISTS Users ";
        try {
            mySQLConnection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = " INSERT INTO Users (name, lastName, age) values (?,?,?)";
        try {
        PreparedStatement pst = mySQLConnection.prepareStatement(sql);
        pst.setString(1,name);
        pst.setString(2,lastName);
        pst.setByte(3,age);
        pst.executeUpdate();
        pst.close();
            System.out.println("User c именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM Users WHERE id = ?";
        try {
            PreparedStatement pst = mySQLConnection.prepareStatement(sql);
            pst.setLong(1,id);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        String sql = "Select id, name, lastName, age from Users";
    List<User> userArrayList = new ArrayList<>();
    Statement myStatement = null;
        try {
        myStatement = mySQLConnection.createStatement();
        ResultSet rs = myStatement.executeQuery(sql);
        while (rs.next()) {
            Long id = rs.getLong(1);
            String name = rs.getString(2);
            String LastName = rs.getString(3);
            byte age = rs.getByte(4);
            User currentUser = new User(name, LastName, age);
            currentUser.setId(id);
            userArrayList.add(currentUser);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return userArrayList;
}

    public void printAllUsersFromTable (){
        System.out.println(" Таблица Users : ");

        String sql = "Select * from Users";
        Statement myStatement = null;
        try {
            myStatement = mySQLConnection.createStatement();
            ResultSet rs = myStatement.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getLong(1) + " " + rs.getString(2) + " " +
                        rs.getString(3) + " " + rs.getByte(4));
            }
            myStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cleanUsersTable() {
        String sql = " DELETE FROM Users ";
        try {
            mySQLConnection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
