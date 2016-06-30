package ru.cadmy.blog.service;

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
