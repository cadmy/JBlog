package ru.cadmy.blog.service;

/**
 * Created by Cadmy on 10.03.2016.
 */

import ru.cadmy.blog.model.User;

import java.util.List;

public interface UserService {

    UserAdditionResults addUser(User user);

    List<User> getUserList();

    void removeUser(Long id);

    User getCurrentUser();

    String getCurrentUsername();

    User getUserById(Long userId);

    User getUserByUsername(String username);

    boolean doesUsernameExist(String username);
}
