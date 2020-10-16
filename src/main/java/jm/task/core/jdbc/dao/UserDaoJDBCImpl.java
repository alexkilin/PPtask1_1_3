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
        try {
            mySQLConnection.createStatement().executeUpdate(" DROP TABLE IF EXISTS Users ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
        PreparedStatement pst = mySQLConnection.prepareStatement(" INSERT INTO Users (name, lastName, age) " +
                "values (?,?,?)");
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
        try {
            PreparedStatement pst = mySQLConnection.prepareStatement("DELETE FROM Users WHERE id = ?");
            pst.setLong(1,id);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
    List<User> userArrayList = new ArrayList<>();
    Statement myStatement = null;
        try {
        myStatement = mySQLConnection.createStatement();
        ResultSet rs = myStatement.executeQuery("Select id, name, lastName, age from Users");
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



    public void cleanUsersTable() {
        String sql = " DELETE FROM Users ";
        try {
            mySQLConnection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
