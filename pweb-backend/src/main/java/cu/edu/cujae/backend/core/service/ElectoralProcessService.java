package cu.edu.cujae.backend.core.service;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.backend.core.dto.ElectoralProcessDto;

public interface ElectoralProcessService {
	List<ElectoralProcessDto> listElectoralProcess() throws SQLException;
	ElectoralProcessDto getElectoralProcessById(int id_electoral_process) throws SQLException; // originalmente el parametro era String
	void createElectoralProcess(ElectoralProcessDto electoralProcess) throws SQLException;
	void updateElectoralProcess(ElectoralProcessDto electoralProcess) throws SQLException;
	void deleteElectoralProcess(int id_electoral_process) throws SQLException; // originalmente el parametro era String
}