package cu.edu.cujae.backend.api.controller;

import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.core.email.EmailSenderService;
import cu.edu.cujae.backend.core.email.Mail;
import cu.edu.cujae.backend.core.service.UserService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
    private UserService userService;
    @Autowired
    private EmailSenderService emailService;

  @GetMapping("/")
  public ResponseEntity<List<UserDto>> getUsers() throws SQLException {
    List<UserDto> voterList = userService.listUsers();
      System.out.println("En el controller mando " + voterList.size() + " usuarios");
      return ResponseEntity.ok(voterList);
  }

	@GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id) throws SQLException {
        UserDto voter = userService.getUserById(id);
        return ResponseEntity.ok(voter);
    }

    @PostMapping("/")
    public ResponseEntity<String> insert(@RequestBody UserDto user) throws SQLException{
        userService.createUser(user);
        sendMailToUserWithCredentials(user.getFull_name(), user.getEmail());
        return ResponseEntity.ok("User created");
    }
   
  @PutMapping("/")
   public ResponseEntity<String> update(@RequestBody UserDto voter) throws SQLException {
     userService.updateUser(voter);
       return ResponseEntity.ok("User Updated");
   }
   
   @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) throws SQLException {
      userService.deleteUser(id);
        return ResponseEntity.ok("User Deleted");
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Integer> getByName(@PathVariable String name) throws SQLException {
        int id = userService.getIdByName(name);
        return ResponseEntity.ok(id);
    }

    private void sendMailToUserWithCredentials(String fullName, String email){
        Mail mail = new Mail();
        mail.setMailTo(email);
        mail.setSubject("Registro de Usuario");
        mail.setTemplate("user-registration-template.ftl");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name",fullName);
        mail.setProps(model);

        try{
            emailService.sendEmail(mail);
        }catch (MessagingException | IOException | TemplateException e){
            e.printStackTrace();
        }
    }
}