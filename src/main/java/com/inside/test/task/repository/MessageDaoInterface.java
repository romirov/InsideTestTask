package com.inside.test.task.repository;

import com.inside.test.task.entity.Message;
import com.inside.test.task.entity.MessageInterface;
import java.util.List;

public interface MessageDaoInterface {
  public boolean save(final MessageInterface message);
  public boolean update(final MessageInterface message);
  public boolean delete(final MessageInterface message);
  public List<Message> findByUsernameAndAmount(final String username, final String amountMessages);
}
