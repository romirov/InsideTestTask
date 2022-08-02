package com.inside.test.task.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Message implements MessageInterface {
  public final long id;
  public final String username;
  public final String message;

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public String getMessage() {
    return this.message;
  }
}
