package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.security.details.UserDetailsImpl;
import ru.kata.spring.boot_security.demo.services.UsersService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class UsersControllers {

    private final UsersService usersService;

    @Autowired
    public UsersControllers(UsersService usersService ) {
        this.usersService = usersService;
    }

    @GetMapping("/admin")
    public String getUsers(Model model, User user) {
        List<User> userList = usersService.getAllUsers();
        model.addAttribute("users", userList);
        model.addAttribute("add_user", user);
        return "admin";
    }

    @PostMapping("/admin")
    public String addUser(@ModelAttribute("add_user") @Valid User addUser,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin";
        }
        if (addUser.getId() == null) {
            usersService.save(addUser);
        } else {
            usersService.update(addUser.getId(), addUser);
        }
        return "redirect:/admin";
    }

    @GetMapping("admin/user-{id}/delete-user")
    public String removeUser(@PathVariable("id") Long id) {
        usersService.removeUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/user-{id}/update-user")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = usersService.getUserById(id);
        model.addAttribute("add_user", user);
        model.addAttribute("users", usersService.getAllUsers());
        return "admin";
    }

    @GetMapping("/user")
    public String userPage(Principal principal, Model model) {
        User user = usersService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

}
