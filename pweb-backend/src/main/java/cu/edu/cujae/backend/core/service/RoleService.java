package cu.edu.cujae.backend.core.service;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.backend.core.dto.RoleDto;

public interface RoleService {
	
	List<RoleDto> getRolesByUserId(String userId) throws SQLException;
	
	List<RoleDto> listRoles() throws SQLException;
	
	RoleDto getRoleById(Long roleId) throws SQLException;
	
}
