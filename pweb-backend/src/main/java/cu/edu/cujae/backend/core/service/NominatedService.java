package cu.edu.cujae.backend.core.service;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.backend.core.dto.NominatedDto;

public interface NominatedService {
	List<NominatedDto> listNominateds() throws SQLException;
	NominatedDto getNominatedById(int userId) throws SQLException; // originalmente el parametro era String
	int getIdByName(String nominatedName) throws SQLException;
	void createNominated(NominatedDto user) throws SQLException;
	void updateNominated(NominatedDto user) throws SQLException;
	void deleteNominated(int id) throws SQLException; // originalmente el parametro era String
}