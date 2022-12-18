package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.core.dto.VoterDto;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
	List<UserDto> listUsers() throws SQLException;
	UserDto getUserById(int voterId) throws SQLException;
	int getIdByName(String name) throws SQLException;
	void createUser(UserDto user) throws SQLException;
	void updateUser(UserDto user) throws SQLException;
	void deleteUser(int id) throws SQLException;
}