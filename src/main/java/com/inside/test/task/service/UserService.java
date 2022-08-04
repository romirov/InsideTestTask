package com.inside.test.task.service;

import com.inside.test.task.entity.User;
import com.inside.test.task.entity.UserInterface;
import com.inside.test.task.utils.UserRowMapper;
import com.inside.test.task.repository.UserDaoInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDaoInterface {

  private final JdbcTemplate jdbcTemplate;

  @Override
  @Transactional
  public boolean save(final UserInterface user) {
    final String queryInsert =
        "INSERT INTO user_table(username, password) VALUES(?, ?)";

    return jdbcTemplate.update(queryInsert, user.getUsername(),user.getPassword()) > 0;
  }

  @Override
  @Transactional
  public boolean update(final UserInterface user) {
    final String queryUpdate =
        "UPDATE user_table SET username=?, password=? WHERE username=?";
    return jdbcTemplate.update(queryUpdate
        ,user.getUsername()
        ,user.getPassword()
        ,user.getUsername()) > 0;
  }

  @Transactional
  @Override
  public boolean delete(final UserInterface user) {
    final String queryDelete = "DELETE FROM user_table WHERE username=" + user.getUsername();
    return jdbcTemplate.update(queryDelete) > 0;
  }

  @Override
  @Transactional
  public User find(final UserInterface user) {
    final String querySelect =
        "SELECT * FROM user_table WHERE username='" + user.getUsername() + "'";

    List<User> result = jdbcTemplate.query(querySelect
        ,new UserRowMapper());
    return DataAccessUtils.uniqueResult(result);
  }
}
