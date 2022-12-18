package cu.edu.cujae.backend.api.controller;

import cu.edu.cujae.backend.core.dto.RolesDto;
import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.core.email.EmailSenderService;
import cu.edu.cujae.backend.core.email.Mail;
import cu.edu.cujae.backend.core.service.RolesService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/roles")
public class RolesController {

	@Autowired
    private RolesService rolesService;

  @GetMapping("/")
  public ResponseEntity<List<RolesDto>> getUsers() throws SQLException {
    List<RolesDto> voterList = rolesService.listRoles();
      return ResponseEntity.ok(voterList);
  }

	@GetMapping("/{id}")
    public ResponseEntity<RolesDto> getRolesById(@PathVariable int id) throws SQLException {
        RolesDto voter = rolesService.getRolesById(id);
        return ResponseEntity.ok(voter);
    }

    @PostMapping("/")
    public ResponseEntity<String> insert(@RequestBody RolesDto roles) throws SQLException{
        rolesService.createRoles(roles);
        return ResponseEntity.ok("Roles created");
    }
   
  @PutMapping("/")
   public ResponseEntity<String> update(@RequestBody RolesDto roles) throws SQLException {
     rolesService.updateRoles(roles);
       return ResponseEntity.ok("Roles Updated");
   }
   
   @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoles(@PathVariable int id) throws SQLException {
      rolesService.deleteRoles(id);
        return ResponseEntity.ok("Roles Deleted");
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Integer> getByName(@PathVariable String name) throws SQLException {
        int id = rolesService.getIdByName(name);
        return ResponseEntity.ok(id);
    }
}