package cu.edu.cujae.backend.api.controller;

import java.sql.SQLException;
import java.util.List;

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

import cu.edu.cujae.backend.core.dto.RoleDto;
import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.core.service.RoleService;
import cu.edu.cujae.backend.core.service.UserService;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("")
    public ResponseEntity<List<RoleDto>> getRoles() throws SQLException {
		List<RoleDto> roleList = roleService.listRoles();
        return ResponseEntity.ok(roleList);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<RoleDto> geRoleById(@PathVariable Long id) throws SQLException {
		RoleDto role = roleService.getRoleById(id);
        return ResponseEntity.ok(role);
    }
	
	@GetMapping("/users/{userId}")
    public ResponseEntity<List<RoleDto>> geRoleByUserId(@PathVariable String userId) throws SQLException {
		List<RoleDto> roleList = roleService.getRolesByUserId(userId);
        return ResponseEntity.ok(roleList);
    }
	
}
