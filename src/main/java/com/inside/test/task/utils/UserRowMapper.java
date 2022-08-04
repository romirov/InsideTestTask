package com.inside.test.task.utils;

import com.inside.test.task.entity.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

  @Override
  public User mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new User(rs.getLong("id"),
        rs.getString("username"),
        rs.getString("password"));
  }
}