package com.inside.test.task.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class User implements UserInterface {
  public final long id;
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
