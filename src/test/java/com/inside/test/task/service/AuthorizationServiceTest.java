package com.inside.test.task.service;

import com.inside.test.task.entity.UserInterface;
import com.inside.test.task.model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthorizationServiceTest {

  @Autowired
  private AuthorizationService authService;

  @Test
  public void generateTokenTest(){
    //given
    final UserInterface user = new UserModel("111", "111");
    //when
    final String token = authService.generateToken(user);
    //then
    Assertions.assertTrue(token.length() > 0);
  }

  @Test
  public void isTokenValidTest() {
    //given
    final UserInterface user = new UserModel("111", "111");
    final UserInterface fakeUser = new  UserModel("222", "111");
    final String fakeToken = "4IHmhSN6XhyOfalcss2AaRWYbIzlQosxaaLU7rZ9U3NF9TE3KzYKIgJnvILvlAkf";
    //when
    final String token = authService.generateToken(user);
    //then
    Assertions.assertTrue(authService.isTokenValid(user.getUsername(), token));
    Assertions.assertFalse(authService.isTokenValid(fakeUser.getUsername(), token));
    Assertions.assertFalse(authService.isTokenValid(user.getUsername(), fakeToken));

  }
}
