package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.RolesDto;
import cu.edu.cujae.backend.core.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface RolesService {
	List<RolesDto> listRoles() throws SQLException;
	RolesDto getRolesById(int voterId) throws SQLException;
	int getIdByName(String name) throws SQLException;
	void createRoles(RolesDto roles) throws SQLException;
	void updateRoles(RolesDto roles) throws SQLException;
	void deleteRoles(int id) throws SQLException;
}