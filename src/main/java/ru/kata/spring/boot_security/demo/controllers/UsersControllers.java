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
import ru.kata.spring.boot_security.demo.services.UsersService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UsersControllers {

    private final UsersService usersService;

    @Autowired
    public UsersControllers(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/users")
    public String getUsers(Model model, User user) {
        List<User> userList = usersService.getAllUsers();
        model.addAttribute("users", userList);
        model.addAttribute("add_user", user);
        return "users";
    }

    @PostMapping("/users")
    public String addUser(@ModelAttribute("add_user") @Valid User addUser,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users";
        }
        usersService.save(addUser);
        return "redirect:/users";
    }

    @GetMapping("users/{id}/delete-user")
    public String removeUser(@PathVariable("id") Long id) {
        usersService.removeUserById(id);
        return "redirect:/users";
    }

    @GetMapping("users/{id}/update-user")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = usersService.getUserById(id);
        model.addAttribute("add_user", user);
        model.addAttribute("users", usersService.getAllUsers());
        return "/users";
    }

}
