package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.CollegeDto;
import cu.edu.cujae.backend.core.dto.DelegatedDto;

import java.sql.SQLException;
import java.util.List;

public interface DelegatedService {
	List<DelegatedDto> listDelegateds() throws SQLException;
	DelegatedDto getDelegatedById(int delegatedId) throws SQLException; // originalmente el parametro era String
//	int getIdByName(String delegatedName) throws SQLException;
	void createDelegated(DelegatedDto delegated) throws SQLException;
	void updateDelegated(DelegatedDto delegated) throws SQLException;
	void deleteDelegated(int id) throws SQLException; // originalmente el parametro era String
}