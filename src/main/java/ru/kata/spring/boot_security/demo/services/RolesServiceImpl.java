package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RolesServiceImpl implements RolesService{

    private final RolesRepository rolesRepository;
    private final UsersService usersService;

    @Autowired
    public RolesServiceImpl(RolesRepository rolesRepository, UsersService usersService) {
        this.rolesRepository = rolesRepository;
        this.usersService = usersService;
    }

    @Override
    public List<Role> getAllRoles() {
        return rolesRepository.findAll();
    }

    @Override
    public void save(Role role) {
        rolesRepository.save(role);
    }

    @Override
    public void addRoleToUser(User user, Role role) {
        Set<Role> roles = (Set<Role>) usersService.getUserById(user.getId()).getRoles();
        roles.add(role);
        user.setRoles(roles);
        usersService.save(user);

    }
}
