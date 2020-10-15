package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.createUsersTable();
        User user1 = new User("Alex", "Sidorov", (byte) 5);
        User user2 = new User("Michael", "Ivanov", (byte) 25);
        User user3 = new User("Ivan", "Petrov", (byte) 35);
        User user4 = new User("Oleg", "Lee", (byte) 45);
        userServiceImpl.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userServiceImpl.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userServiceImpl.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userServiceImpl.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        List<User> userList = userServiceImpl.getAllUsers();
        for (int i = 0; i < userList.size(); i++) {
            System.out.println(userList.get(i).toString());
        }
        userServiceImpl.cleanUsersTable();
        userServiceImpl.dropUsersTable();
    }
}