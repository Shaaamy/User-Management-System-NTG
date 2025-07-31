package com.ntg.Ntg_User_Management_System.controller;

import com.ntg.Ntg_User_Management_System.dto.UserDTO;
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
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable int id){
        return userService.findUserById(id);
    }
    @GetMapping("/username/{username}")
    public UserDTO getUserByUserName(@PathVariable String username){
        return userService.findUserByUsername(username);
    }
    @GetMapping("/both/{value}")
    public UserDTO getUser(@PathVariable String value){
        try{
            int id = Integer.parseInt(value);
            return userService.findUserById(id);
        }catch (NumberFormatException ex) {
            return userService.findUserByUsername(value);
        }
    }
    @PutMapping("/{id}")
    public String updateUser(@RequestBody User user , @PathVariable int id){
        return userService.updateUser(user , id);
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id){
        return userService.setUserToDelete(id);
    }
    @GetMapping()
    public List<UserDTO> GetAllUsers(){
        return userService.findAllUsers();
    }
    @PostMapping("/login")
    public UserDTO login(@RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest.getUsername(), loginRequest.getPassword());
    }
}
