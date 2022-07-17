package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RolesService;
import ru.kata.spring.boot_security.demo.services.UsersService;


import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DataInitializer {

    private final UsersService usersService;
    private final RolesService rolesService;

    @Autowired
    public DataInitializer(UsersService usersService, RolesService rolesService) {
        this.usersService = usersService;
        this.rolesService = rolesService;
    }

    @PostConstruct
    void addConstUsers() {

        Role roleAdmin = new Role(1L, Role.ROLE_ADMIN);
        Role roleUser = new Role(2L, Role.ROLE_USER);

        rolesService.save(roleAdmin);
        rolesService.save(roleUser);

        User userAdmin = new User(
                1L,
                "admin",
                "admin",
                "admin@mail.ru",
                "admin");

        User userUser = new User(
                2L,
                "user",
                "user",
                "user@mail.ru",
                "user"
        );

        userAdmin.setRoles(List.of(roleAdmin, roleUser));
        userUser.setRoles(List.of(roleUser));

        usersService.register(userAdmin);
        usersService.register(userUser);
    }
}
