package com.ecommerce.Users.service;

import com.ecommerce.Users.dao.UserRepo;
import com.ecommerce.Users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserService {
@Autowired
UserRepo userRepo;
@Autowired
PasswordEncoder passwordEncoder;
    public ResponseEntity<?> registerUser(User user) {
        // Check if email is already registered
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");

    }

    public ResponseEntity<?> login(String email, String password) {
        return userRepo.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .<ResponseEntity< Object>>map(user -> ResponseEntity.status(HttpStatus.FOUND).body(user))
                .orElseGet(()->ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized user try with different email and password"));

}
}
