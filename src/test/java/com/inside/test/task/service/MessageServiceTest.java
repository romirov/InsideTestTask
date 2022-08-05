package com.inside.test.task.service;

import com.inside.test.task.entity.Message;
import com.inside.test.task.entity.MessageInterface;
import com.inside.test.task.entity.UserInterface;
import com.inside.test.task.model.MessageModel;
import com.inside.test.task.model.UserModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessageServiceTest {

  @Autowired
  private UserService userService;

  @Autowired
  private MessageService messageService;

  @BeforeAll
  public void init(){
    final UserInterface user = new UserModel("111", "string");
    userService.save(user);
  }

  @Test
  public void saveFunctionsTest(){
    //given
    final MessageInterface message = new MessageModel("111", "string");
    //when
    final boolean isMessageSaved = messageService.save(message);
    //then
    Assertions.assertTrue(isMessageSaved);
  }

  @Test
  public void findByUsernameAndAmountTest(){
    //given
    final MessageInterface message = new MessageModel("111", "string2");
    //when
    final boolean isMessageSaved = messageService.save(message);
    final List<Message> listMessagesFromDb = messageService.findByUsernameAndAmount("111", "1");
    //then
    Assertions.assertTrue(listMessagesFromDb.size() == 1);
  }

  @Test
  public void updateTest(){
    //given
    final MessageInterface message = new MessageModel("111", "string3");
    //when
    final boolean isMessageSaved = messageService.save(message);
    final Message messageFromDb = messageService
        .findByUsernameAndAmount("111", "1")
        .stream().findFirst().get();
    final Message newMessage = new Message(messageFromDb.getId()
        ,messageFromDb.getUsername()
        ,"string4");
    final boolean isMessageUpdated = messageService.update(newMessage);
    //then
    Assertions.assertTrue(isMessageUpdated);
  }

  @Test
  public void deleteTest(){
    //given
    final MessageInterface message = new MessageModel("111", "string10");
    //when
    final boolean isMessageSaved = messageService.save(message);
    final boolean isMessageDelete = messageService.delete(message);
    //then
    Assertions.assertTrue(isMessageDelete);
  }

  @AfterAll
  public void destroy(){
    final UserInterface user = new UserModel("111", "string");
    userService.delete(user);
  }
}
