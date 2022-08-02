package com.inside.test.task.service;

import com.inside.test.task.entity.UserInterface;
import com.inside.test.task.entity.UserRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Queue;

@Service
@AllArgsConstructor
public class UserService {

  private final JdbcTemplate jdbcTemplate;
  private final UserService userService;

  @Transactional
  public UserInterface save(UserInterface user) {
    final String queryInsert =
        "INSERT INTO user_table(" + user.getUsername() + ", " + user.getPassword() + ")";
    return jdbcTemplate.queryForObject(queryInsert, new UserRowMapper());
  }

  @Transactional
  public UserInterface findByUsername(UserInterface user) {
    final String querySelect = "SELECT * FROM user_table WHERE username=" + user.getUsername();
    return jdbcTemplate.queryForObject(querySelect, new UserRowMapper());
  }

  @Transactional
  public boolean deleteByUsername(UserInterface user) {
    final String queryDelete = "DELETE FROM user_table WHERE username=" + user.getUsername();
    return jdbcTemplate.update(queryDelete) > 0;
  }
}
