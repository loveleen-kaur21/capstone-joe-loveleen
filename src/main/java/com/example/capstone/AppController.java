package com.example.capstone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class AppController {
    @Autowired

    private UserRepository userRepo;

    @GetMapping("/users")
    public String listAll(Model model) {
        List<Users> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }
}
