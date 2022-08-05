package com.inside.test.task.controller;

import com.inside.test.task.entity.Message;
import com.inside.test.task.entity.Token;
import com.inside.test.task.entity.UserInterface;
import com.inside.test.task.model.MessageModel;
import com.inside.test.task.model.UserModel;
import com.inside.test.task.repository.MessageDaoInterface;
import com.inside.test.task.repository.UserDaoInterface;
import com.inside.test.task.service.AuthorizationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
@Slf4j
public class TestTaskController {

  private final UserDaoInterface userDaoImpl;
  private final MessageDaoInterface messageDaoImpl;
  private final AuthorizationService authService;

  @PostMapping("/user")
  @ResponseBody
  public ResponseEntity<?> addUser(@RequestBody UserModel user) {

    log.info("Adding a user " + user.getUsername());

    if(userDaoImpl.find(user) != null) {
      log.error("Found a user with the same name");
      return new ResponseEntity<>("Found a user with the same name", HttpStatus.BAD_REQUEST);
    }

    if (userDaoImpl.save(user)) {
      log.info("User " + user.getUsername() + " added");
      return new ResponseEntity<>("User added", HttpStatus.OK);
    }

    log.error("User " + user.getUsername() + " not added");
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PostMapping("/login")
  @ResponseBody
  public ResponseEntity<?> authorization(@RequestBody UserModel user) {

    log.info("The user " + user.getUsername() +" logs in ");
    final UserInterface userFromDB = userDaoImpl.find(user);

    if(userFromDB != null
        && userFromDB.getUsername().equals(user.getUsername())
        && userFromDB.getPassword().equals(user.getPassword())) {

      log.info("The user " + user.getUsername() + " is authorized ");
      final Token token = new Token(authService.generateToken(user));

      log.info("The user's token is: " + token.getToken());
      return new ResponseEntity<Token>(token, HttpStatus.OK);
    }

    log.error("The user " + user.getUsername() + " is unauthorized ");
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

  @GetMapping("/message")
  @ResponseBody
  public ResponseEntity<?> addMessage(@RequestBody MessageModel message
      ,@RequestHeader(name="Authorization", required=true) String authHeader) {

    log.info("User " + message.getUsername() + " adds a message: " + message.getMessage());

    if(isAuthHeaderValid(message.getUsername(), authHeader)) {
      if(isHistoryMessage(message.getMessage())) {

        ResponseEntity<?> response = processingOfHistoricalMessages(message, authHeader);
        return response;
      }

      if(messageDaoImpl.save(message)) {
        log.info("Message saved");
        return new ResponseEntity<>("Message saved",HttpStatus.OK);
      }
    }

    return new ResponseEntity<>("Message not saved",HttpStatus.BAD_REQUEST);
  }

  private boolean isHistoryMessage (final String message) {
    if(message.matches("history [\\p{Digit}]+")) {
      return true;
    }
    return false;
  }

  private boolean isAuthHeaderValid(final String username, final String authHeader) {

    final String tokenFromRequest = authHeader.substring("Bearer ".length(), authHeader.length());
    log.info("User`s token from message: " + tokenFromRequest);

    return (authService.isTokenValid(username,tokenFromRequest)) ? true : false;
  }

  @ResponseBody
  public ResponseEntity<?> processingOfHistoricalMessages(@RequestBody MessageModel message
      ,@RequestHeader(name = "Authorization", required=true) String authHeader) {

    log.info("User " + message.getUsername() + " adds a message: " + message.getMessage());
    final String amountMessages = message.getMessage().substring("history ".length(), message.getMessage().length());
    final List<Message> listMessages =
        messageDaoImpl.findByUsernameAndAmount(message.getUsername(), amountMessages);

    messageDaoImpl.save(message);
    if(listMessages.isEmpty()) {
      return new ResponseEntity<>("Messages not found", HttpStatus.NOT_FOUND);
    }

    final Stream<MessageModel> stream = listMessages.stream().map(it ->
        new MessageModel(it.getUsername(), it.getMessage()));
    final List<MessageModel> listMessageModel = stream.collect(Collectors.toList());

    return new ResponseEntity<>(listMessageModel, HttpStatus.OK);
  }
}
