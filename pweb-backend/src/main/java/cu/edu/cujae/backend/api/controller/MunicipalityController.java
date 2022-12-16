package cu.edu.cujae.backend.api.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cu.edu.cujae.backend.core.dto.MunicipalityDto;
import cu.edu.cujae.backend.core.service.MunicipalityService;

@RestController
@RequestMapping("/api/v1/municipalitys")
public class MunicipalityController {

  @Autowired
  private MunicipalityService municipalityService;

  @GetMapping("/")
  public ResponseEntity<List<MunicipalityDto>> getMunicipalitys() throws SQLException {
    List<MunicipalityDto> voterList = municipalityService.getMunicipalitys();
    return ResponseEntity.ok(voterList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MunicipalityDto> getMunicipalityById(@PathVariable int id) throws SQLException {
    MunicipalityDto voter = municipalityService.getMunicipalityById(id);
    return ResponseEntity.ok(voter);
  }


  @PostMapping("/")
  public ResponseEntity<String> insert(@RequestBody MunicipalityDto muni) throws SQLException {
    municipalityService.createMunicipality(muni);
    return ResponseEntity.ok("Muni inserted");
  }

  @PutMapping("/")
  public ResponseEntity<String> update(@RequestBody MunicipalityDto muni) throws SQLException {
    municipalityService.updateMunicipality(muni);
    return ResponseEntity.ok("Muni updated");
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable("id") int municipalityId) throws SQLException {
    municipalityService.deleteMunicipality(municipalityId);
    return ResponseEntity.ok("Muni deleted");
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<Integer> getByName(@PathVariable String municipalityName) throws SQLException {
    int id = municipalityService.getIdByName(municipalityName);
    return ResponseEntity.ok(id);
  }
}