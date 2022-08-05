package com.inside.test.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inside.test.task.model.MessageModel;
import com.inside.test.task.model.UserModel;
import com.inside.test.task.service.AuthorizationService;
import com.inside.test.task.service.UserService;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class TestTaskControllerTest {

  private MockMvc mockMvc;
  private UserModel user;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private UserService userService;

  @Autowired
  private AuthorizationService authorizationService;

  @BeforeEach
  public void init(){
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    this.user = new UserModel("adm", "adm");
  }

  @Test
  public void addUserTest() throws Exception {
    //then
    this.mockMvc.perform(MockMvcRequestBuilders
        .post("/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(user))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void authorizationTest() throws Exception {
    //then
    MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
        .post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(user))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    final String responseBody = mvcResult.getResponse().getContentAsString();
    final String token = JsonPath.read(responseBody, "$.token");
    Assertions.assertNotNull(token);
  }

  @Test
  public void processingOfSimpleMessagesTest() throws Exception {
    //given
    final MessageModel message = new MessageModel("test", "string");
    final UserModel userForSendMessage = new UserModel("test", "test");
    userService.save(userForSendMessage);
    final String token = authorizationService.generateToken(userForSendMessage);

    //then
    this.mockMvc.perform(MockMvcRequestBuilders
        .post("/message")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(message))
        .header("Authorization", "Bearer " + token)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    userService.delete(userForSendMessage);
  }

  @Test
  public void processingOfHistoricalMessagesTest() throws Exception {
    final MessageModel message = new MessageModel("test", "string");
    final MessageModel historicalMessage = new MessageModel("test", "history 10");
    final UserModel userForSendMessage = new UserModel("test", "test");
    userService.save(userForSendMessage);
    final String token = authorizationService.generateToken(userForSendMessage);

    //then
    this.mockMvc.perform(MockMvcRequestBuilders
            .post("/message")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(message))
            .header("Authorization", "Bearer " + token)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    this.mockMvc.perform(MockMvcRequestBuilders
            .post("/message")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(historicalMessage))
            .header("Authorization", "Bearer " + token)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    userService.delete(userForSendMessage);
  }

  @AfterAll
  private void destroy(){
    userService.delete(user);
  }
}
