package com.ntg.Ntg_User_Management_System.repo;

import com.ntg.Ntg_User_Management_System.dto.UserDTO;
import com.ntg.Ntg_User_Management_System.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to create a new user
    public String save(User user) {
        String sql = "INSERT INTO users (first_name, last_name, username, password, email, phone_number, is_deleted)" + "VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPhoneNumber());
            ps.setBoolean(7, user.isDeleted());
            return ps;
        }, keyHolder);
        int generatedId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        return "User created with ID: " + generatedId;
    }
    // Method to find a user by id
    public UserDTO findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.query(sql , new UserRowMapper(),id).stream().findFirst().orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }
    // Method to find a user by username
    public UserDTO findByUsername(String username){
        String sql = "SELECT * FROM users WHERE username ILIKE ?";
        String pattern = "%" + username + "%";
        return jdbcTemplate.query(sql,new UserRowMapper(),pattern).stream().findFirst().orElseThrow(()-> new RuntimeException("User not found with username "+username));
    }
    // Method to update a user
    public String update(User user , int id){
        String sql = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ?, email = ?, phone_number = ?, is_deleted = ? WHERE id = ?";
        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPhoneNumber());
            ps.setBoolean(7, user.isDeleted());
            ps.setInt(8,id);
            return ps;
        });
        return "User with ID: " + id + " is Updated";
    }
    // Method to is_deleted to true
    public String setToDelete(int id){
        String sql = "UPDATE users SET is_deleted = true WHERE id = ?";
        jdbcTemplate.update(sql, id);
//        jdbcTemplate.update(connection->{
//            var ps = connection.prepareStatement(sql);
//            ps.setInt(1,id);
//            return ps;
//        });
        return "User with ID " + id +" is set to be deleted";
    }
    // Method to find all
    public List<UserDTO> findAll(){
        String sql = "SELECT * FROM users ORDER BY id";
        return jdbcTemplate.query(sql , new UserRowMapper());
    }
    // Method to login
    public String getPassword(String username){
        String sql = "SELECT password FROM users where username = ?";
        return jdbcTemplate.queryForObject(sql ,String.class, username);
    }

    public String partiallyUpdate(User user, int id) {
        UserDTO ExistingUser = findById(id);
        String currentPassword = getPassword(ExistingUser.getUsername());
        String firstName = user.getFirstName() != null ? user.getFirstName() : ExistingUser.getFirstName();
        String lastName = user.getLastName() != null ? user.getLastName() : ExistingUser.getLastName();
        String username = user.getUsername() != null ? user.getUsername() : ExistingUser.getUsername();
        String password = user.getPassword() != null ? user.getPassword() : currentPassword;
        String email = user.getEmail() != null ? user.getEmail() : ExistingUser.getEmail();
        String phoneNumber = user.getPhoneNumber() != null ? user.getPhoneNumber() : ExistingUser.getPhoneNumber();
        boolean isDeleted = user.isDeleted();
        String sql = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ?, email = ?, phone_number = ?, is_deleted = ? WHERE id = ?";
        jdbcTemplate.update(sql,firstName, lastName, username, password, email, phoneNumber, isDeleted, id);
        return "User with ID: " + id + " is partially updated";

    }
}
