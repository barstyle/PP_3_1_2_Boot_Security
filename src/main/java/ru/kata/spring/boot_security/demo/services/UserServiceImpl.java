package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, PasswordEncoder encoder) {
        this.usersRepository = usersRepository;
        this.encoder = encoder;
    }

    @Override
    public void save(User user) {
        usersRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return usersRepository.getById(id);
    }

    @Override
    public void removeUserById(Long id) {
        usersRepository.delete(getUserById(id));
    }

    @Override
    public void update(Long id, User user) {
        User updateUser = getUserById(id);
        updateUser.setEmail(user.getEmail());
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        usersRepository.save(updateUser);
    }

    @Override
    public void register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return usersRepository.findByEmail(email).orElse(new User());
    }
}
