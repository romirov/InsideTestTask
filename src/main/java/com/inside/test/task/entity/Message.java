package com.inside.test.task.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Message implements MessageInterface {
  private final long id;
  private final String username;
  private final String message;

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public String getMessage() {
    return this.message;
  }
}
