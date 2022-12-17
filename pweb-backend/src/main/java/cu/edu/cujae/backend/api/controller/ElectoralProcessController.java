package cu.edu.cujae.backend.api.controller;

import cu.edu.cujae.backend.core.dto.DelegatedDto;
import cu.edu.cujae.backend.core.dto.ElectoralProcessDto;
import cu.edu.cujae.backend.core.service.ElectoralProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/electoralprcs")
public class ElectoralProcessController {
	@Autowired
	private ElectoralProcessService electoralProcessService;
	
	@GetMapping("/")
    public ResponseEntity<List<ElectoralProcessDto>> getDelegateds() throws SQLException {
		List<ElectoralProcessDto> userList = electoralProcessService.listElectoralProcess();
        return ResponseEntity.ok(userList);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<ElectoralProcessDto> getElectoralProcessById(@PathVariable int id) throws SQLException {
        ElectoralProcessDto user = electoralProcessService.getElectoralProcessById(id);
        return ResponseEntity.ok(user);
    }
	
	@PostMapping("/")
    public ResponseEntity<String> insert(@RequestBody ElectoralProcessDto electoralProcess) throws SQLException {
		electoralProcessService.createElectoralProcess(electoralProcess);
        return ResponseEntity.ok("Electoral Process created");
    }
	
	@PutMapping("/")
    public ResponseEntity<String> update(@RequestBody ElectoralProcessDto electoralprocess) throws SQLException {
		//TODO code for update
        electoralProcessService.updateElectoralProcess(electoralprocess);
        return ResponseEntity.ok("Electoral Process updated");
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws SQLException {
		electoralProcessService.deleteElectoralProcess(id);
        return ResponseEntity.ok("Electoral Process deleted");
    }

//    @GetMapping("/name/{name}")
//    public ResponseEntity<Integer> getByName(@PathVariable String name) throws SQLException {
//        int id = delegatedService.getIdByName(name);
//        return ResponseEntity.ok(id);
//    }
}
