package com.inside.test.task.model;

import com.inside.test.task.entity.MessageInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageModel implements MessageInterface {
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
