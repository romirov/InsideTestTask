package com.inside.test.task.service;

import com.inside.test.task.entity.User;
import com.inside.test.task.entity.UserInterface;
import com.inside.test.task.model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
  @Autowired
  private UserService userService;

  @Test
  public void saveTest(){
    //given
    final UserInterface user = new UserModel("222", "222");
    //when
    final boolean isUserSaved = userService.save(user);
    //then
    Assertions.assertTrue(isUserSaved);
  }

  public void updateTest(){
    //given
    final UserModel user = new UserModel("222", "333");
    //when
    final boolean isUserUpdates = userService.update(user);
    //then
    Assertions.assertTrue(isUserUpdates);
  }

  public void findTest(){
    //given
    final UserInterface user = new UserModel("222", "333");
    //when
    final User userFromDb = userService.find(user);
    //then
    Assertions.assertEquals(userFromDb.getUsername(), user.getUsername());
    Assertions.assertEquals(userFromDb.getPassword(), user.getPassword());
  }

  @Test
  public void deleteTest(){
    //given
    final UserInterface user = new UserModel("222", "333");
    //when
    final boolean isUserDeleted = userService.delete(user);
    //then
    Assertions.assertTrue(isUserDeleted);
  }
}
