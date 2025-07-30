package com.ntg.Ntg_User_Management_System.controller;

import com.ntg.Ntg_User_Management_System.model.LoginRequest;
import com.ntg.Ntg_User_Management_System.model.User;
import com.ntg.Ntg_User_Management_System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user){
        return userService.createUser(user);
    }
    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable int id){
        return userService.findUserById(id);
    }
    @GetMapping("/username/{username}")
    public User getUserByUserName(@PathVariable String username){
        return userService.findUserByUsername(username);
    }
    @GetMapping("/{value}")
    public User getUser(@PathVariable String value){
        try{
            int id = Integer.parseInt(value);
            return userService.findUserById(id);
        }catch (NumberFormatException ex) {
            return userService.findUserByUsername(value);
        }
    }
    @PutMapping("/edit/{id}")
    public String updateUser(@RequestBody User user , @PathVariable int id){
        return userService.updateUser(user , id);
    }
    @PutMapping("/setdelete/{id}")
    public String deleteUser(@PathVariable int id){
        return userService.setUserToDelete(id);
    }
    @GetMapping()
    public List<User> GetAllUsers(){
        return userService.findAllUsers();
    }
    @PostMapping("/login")
    public User login(@RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest.getUsername(), loginRequest.getPassword());
    }
}
