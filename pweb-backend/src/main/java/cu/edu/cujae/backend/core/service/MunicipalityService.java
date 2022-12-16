package cu.edu.cujae.backend.core.service;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.backend.core.dto.MunicipalityDto;

public interface MunicipalityService {
	List<MunicipalityDto> getMunicipalitys() throws SQLException;

	MunicipalityDto getMunicipalityById(int municipalityId) throws SQLException; // originalmente el parametro era
																					// String

	int getIdByName(String nameMunicipality) throws SQLException;

	void createMunicipality(MunicipalityDto user) throws SQLException;

	void updateMunicipality(MunicipalityDto user) throws SQLException;

	void deleteMunicipality(int id) throws SQLException; // originalmente el parametro era String
}
