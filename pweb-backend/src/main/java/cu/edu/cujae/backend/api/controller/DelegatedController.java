package cu.edu.cujae.backend.api.controller;

import cu.edu.cujae.backend.core.dto.CollegeDto;
import cu.edu.cujae.backend.core.dto.DelegatedDto;
import cu.edu.cujae.backend.core.service.DelegatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/delegateds")
public class DelegatedController {
	@Autowired
	private DelegatedService delegatedService;
	
	@GetMapping("/")
    public ResponseEntity<List<DelegatedDto>> getDelegateds() throws SQLException {
		List<DelegatedDto> userList = delegatedService.listDelegateds();
        return ResponseEntity.ok(userList);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<DelegatedDto> getDelegatedById(@PathVariable int id) throws SQLException {
        DelegatedDto user = delegatedService.getDelegatedById(id);
        return ResponseEntity.ok(user);
    }
	
	@PostMapping("/")
    public ResponseEntity<String> insert(@RequestBody DelegatedDto delegated) throws SQLException {
		delegatedService.createDelegated(delegated);
        return ResponseEntity.ok("Delegated created");
    }
	
	@PutMapping("/")
    public ResponseEntity<String> update(@RequestBody DelegatedDto delegated) throws SQLException {
		//TODO code for update
        delegatedService.updateDelegated(delegated);
        return ResponseEntity.ok("Delegated updated");
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws SQLException {
		delegatedService.deleteDelegated(id);
        return ResponseEntity.ok("Delegated deleted");
    }

//    @GetMapping("/name/{name}")
//    public ResponseEntity<Integer> getByName(@PathVariable String name) throws SQLException {
//        int id = delegatedService.getIdByName(name);
//        return ResponseEntity.ok(id);
//    }
}
