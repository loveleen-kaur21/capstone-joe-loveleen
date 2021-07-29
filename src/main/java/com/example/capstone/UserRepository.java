package com.example.capstone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    public User findByResetPasswordToken(String token);

    User findByFullName(String fullName);

    List<User> findAllByGroupAndRole(String group_type, String role);

//    User findbyID(Long id);



}
