package com.inside.test.task.controller;

import com.inside.test.task.service.AuthorizationService;
import com.inside.test.task.service.MessageService;
import com.inside.test.task.service.UserService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestTaskControllerTest {

  @MockBean
  private UserService userService;

  @MockBean
  private MessageService messageService;

  @MockBean
  private AuthorizationService authorizationService;

  private final TestTaskController testTaskController = Mockito.mock(TestTaskController.class);

  @Test
  public void addUserTest() throws Exception {
    OkHttpClient client = new OkHttpClient();
    RequestBody requestBody = RequestBody.create(new StringBuilder().append("{\n")
        .append("  \"username\": \"adm\",\n").append("  \"password\": \"adm\"\n")
        .append("}").toString().getBytes());
    Request request = new Request.Builder()
        .url("/user")
        .post(requestBody).build();
    try (Response response = client.newCall(request).execute()) {
      Assertions.assertTrue(response.isSuccessful());
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}
