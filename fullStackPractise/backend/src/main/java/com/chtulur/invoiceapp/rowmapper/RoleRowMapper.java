package com.chtulur.invoiceapp.rowmapper;

import com.chtulur.invoiceapp.model.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {
    //the purpose of this class to just use the superBuilder to create a new Role instance.
    //The resultSet is handed for us by the database when it gives us the response.
    //We are adding an existing Role to our user and this code creates an INSTANCE of the Role
    //that will be given to our user.
    @Override
    public Role mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Role.builder()
                .roleId(resultSet.getLong("roleId"))
                .name(resultSet.getString("name"))
                .permissions(resultSet.getString("permissions"))
                .build();
    }
}
