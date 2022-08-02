package com.inside.test.task.service;

import com.inside.test.task.entity.Message;
import com.inside.test.task.entity.MessageInterface;
import com.inside.test.task.entity.MessageRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

  private final JdbcTemplate jdbcTemplate;
  private final MessageService messageService;

  @Transactional
  public MessageInterface save(MessageInterface message) {
    final String queryInsert =
        "INSERT INTO message_table(" + message.getUsername() + ", " + message.getMessage() + ")";
    return jdbcTemplate.queryForObject(queryInsert, new MessageRowMapper());
  }

  @Transactional
  public List<Message> findByUsername(MessageInterface message) {
    final String querySelect = "SELECT * FROM message_table WHERE username=" + message.getUsername();
    return jdbcTemplate.query(querySelect, new MessageRowMapper());
  }
}
