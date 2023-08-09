package com.example.spring.security.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.example.spring.security.springsecurity.model.Role;
import com.example.spring.security.springsecurity.model.User;
import com.example.spring.security.springsecurity.service.RoleService;
import com.example.spring.security.springsecurity.service.UserService;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAllUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.getAllUser());
        model.addAttribute("admin", userService.findByUsername(principal.getName()));
        model.addAttribute("roles", roleService.getAllRoles());
        return "users";
    }

    @GetMapping("/info")
    public String showAdminInfo(Model model, Principal principal) {
        model.addAttribute("admin", userService.findByUsername(principal.getName()));
        return "admin";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model, Principal principal) {
        model.addAttribute("admin", userService.findByUsername(principal.getName()));
        model.addAttribute("roles", roleService.getAllRoles());
        return "new-user";
    }


    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin/";
    }


    @PatchMapping("/{id}")
    public String saveEditUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.updateUser(user);
        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }
}