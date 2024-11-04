package com.ecommerce.Users.controller;

import com.ecommerce.Users.model.User;
import com.ecommerce.Users.dao.UserRepo;
import com.ecommerce.Users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService userService;


    //Servelet to add user
    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    //Delete User
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepo.deleteById(id);
    }

    //Update User
    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody User p) {
        if (userRepo.findByEmail(p.getEmail()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not exist with email:"+p.getEmail());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User is updated"+userRepo.save(p));
    }


    //get All users
    @GetMapping("/getAllUsers")
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    //get user ny id
    @GetMapping("/getUser/{id}")
    public Optional<User> getUserByID(@PathVariable int id) {
        return userRepo.findById(id);
    }


    //Login by email or
    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email,@PathVariable String password) {
        return userService.login(email,password);
    }


}
