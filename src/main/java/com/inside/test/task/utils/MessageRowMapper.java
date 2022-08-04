package com.inside.test.task.utils;

import com.inside.test.task.entity.Message;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageRowMapper implements RowMapper<Message> {

  @Override
  public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new Message(rs.getLong("id"),
        rs.getString("username"),
        rs.getString("message"));
  }
}