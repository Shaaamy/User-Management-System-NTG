package com.ntg.Ntg_User_Management_System.repo;

import com.ntg.Ntg_User_Management_System.dto.UserDTO;
import com.ntg.Ntg_User_Management_System.model.User;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserDTO> {


    @Override
    public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDTO user = new UserDTO();
        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setDeleted(rs.getBoolean("is_deleted"));
        return user;
    }
}
