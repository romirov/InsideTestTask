package com.inside.test.task.model;

import com.inside.test.task.entity.UserInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserModel implements UserInterface {
  public final String username;
  public final String password;

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public String getPassword() {
    return this.password;
  }
}
