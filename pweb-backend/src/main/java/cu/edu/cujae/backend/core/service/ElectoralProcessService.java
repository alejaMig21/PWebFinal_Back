package cu.edu.cujae.backend.core.service;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.backend.core.dto.Electoral_ProcessDto;

public interface ElectoralProcessService {
	List<Electoral_ProcessDto> listElectoralProcess() throws SQLException;
	Electoral_ProcessDto getElectoralProcessById(int electoralProcessId) throws SQLException; // originalmente el parametro era String
	void createElectoralProcess(Electoral_ProcessDto electoralProcess) throws SQLException;
	void updateElectoralProcess(Electoral_ProcessDto electoralProcess) throws SQLException;
	void deleteElectoralProcess(int id) throws SQLException; // originalmente el parametro era String
}