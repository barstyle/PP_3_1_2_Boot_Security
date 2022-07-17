package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    void save(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    void removeUserById(Long id);

    void update(Long id, User user);

    void register(User user);

    User findByEmail(String email);

}
