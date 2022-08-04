package com.inside.test.task.repository;

import com.inside.test.task.entity.User;
import com.inside.test.task.entity.UserInterface;

public interface UserDaoInterface {
  public boolean save(final UserInterface user);
  public boolean update(final UserInterface user);
  public boolean delete(final UserInterface user);
  public User find(final UserInterface user);
}
