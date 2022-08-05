package com.inside.test.task.service;

import com.inside.test.task.entity.Message;
import com.inside.test.task.entity.MessageInterface;
import com.inside.test.task.utils.MessageRowMapper;
import com.inside.test.task.repository.MessageDaoInterface;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService implements MessageDaoInterface {

  private final JdbcTemplate jdbcTemplate;

  @Override
  @Transactional
  public boolean save(MessageInterface message) {
    final String queryInsert =
        "INSERT INTO message_table(username, message) VALUES(?, ?)";
    return jdbcTemplate.update(queryInsert
        ,message.getUsername()
        ,message.getMessage()) > 0;
  }

  @Override
  @Transactional
  public boolean update(final Message message) {
    final String queryUpdate =
        "UPDATE message_table SET message=? WHERE id=?";
    return jdbcTemplate.update(queryUpdate
        ,message.getMessage()
        ,message.getId()) > 0;
  }

  @Override
  @Transactional
  public boolean delete(MessageInterface message) {
    final String queryDelete = "DELETE FROM message_table WHERE username='" + message.getUsername() + "'";
    return jdbcTemplate.update(queryDelete) > 0;
  }

  @Override
  @Transactional
  public List<Message> findByUsernameAndAmount(final String username, final String amountMessages) {
    final String querySelect = "SELECT * FROM message_table WHERE username='"
        + username
        + "' order by id desc limit "
        + amountMessages;

    return jdbcTemplate.query(querySelect, new MessageRowMapper());
  }
}
