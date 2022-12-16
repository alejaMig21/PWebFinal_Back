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

import cu.edu.cujae.backend.core.dto.DistrictDto;
import cu.edu.cujae.backend.core.service.DistrictService;

@RestController
@RequestMapping("/api/v1/districts")
public class DistrictController {
	
	@Autowired
	private DistrictService districtService;
	
	@GetMapping("/")
    public ResponseEntity<List<DistrictDto>> getDistricts() throws SQLException {
		List<DistrictDto> userList = districtService.listDistricts();
        return ResponseEntity.ok(userList);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<DistrictDto> getDistrictById(@PathVariable int id) throws SQLException {
		DistrictDto user = districtService.getDistrictById(id);
        return ResponseEntity.ok(user);
    }
	
	@PostMapping("/")
    public ResponseEntity<String> createDistrict(@RequestBody DistrictDto district) throws SQLException {
		districtService.createDistrict(district);
        return ResponseEntity.ok("District Created");
    }
	
	@PutMapping("/")
    public ResponseEntity<String> updateDistrict(@RequestBody DistrictDto district) throws SQLException {
		//TODO code for update
        districtService.updateDistrict(district);
        return ResponseEntity.ok("District Updated");
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDistrict(@PathVariable int id) throws SQLException {
		districtService.deleteDistrict(id);
        return ResponseEntity.ok("District deleted");
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Integer> getByName(@PathVariable String name) throws SQLException {
        int id = districtService.getIdByName(name);
        return ResponseEntity.ok(id);
    }
}
