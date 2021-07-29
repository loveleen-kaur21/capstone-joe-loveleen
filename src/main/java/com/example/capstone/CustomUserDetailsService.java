package com.example.capstone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new CustomUserDetails(user);
    }

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepo.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any customer with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepo.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepo.save(user);
    }

    public User setGroup (User current) {
        List<User> usersA = userRepo.findAll();
        usersA.removeIf(user -> !user.getRole().equals(current.getRole()));
        usersA.removeIf(user ->  !user.getGroup().equals("A"));
        List<User> usersB = userRepo.findAll();
        usersB.removeIf(user -> !user.getRole().equals(current.getRole()));
        usersB.removeIf(user -> !user.getGroup().equals("B"));

        if (current.getRole().equals("Manager") && usersA.size() < 3) {
            current.setGroup("A");
        } else if (current.getRole().equals("Nurse") && usersA.size() < 15) {
            current.setGroup("A");
        } else if (current.getRole().equals("PCA") && usersA.size() < 6) {
            current.setGroup("A");
        } else if (current.getRole().equals("Manager") && usersB.size() < 3) {
            current.setGroup("B");
        } else if (current.getRole().equals("Nurse") && usersB.size() < 15) {
            current.setGroup("B");
        } else if (current.getRole().equals("PCA") && usersB.size() < 6) {
            current.setGroup("B");
        } else if (usersB.size() > usersA.size()) {
            current.setGroup("A");
        } else if (usersB.size() < usersA.size()) {
            System.out.println(usersA.size());
            System.out.println(usersB.size());
            current.setGroup("B");
        } else {
            current.setGroup("A");
        }
        System.out.println(current.getGroup());
        return current;
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public void renderUser(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
    }


}
