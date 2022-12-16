package cu.edu.cujae.backend.core.service;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.backend.core.dto.TraceDto;

public interface TraceService {
	List<TraceDto> listTraces() throws SQLException;
	TraceDto getTraceById(int traceId) throws SQLException; // originalmente el parametro era String
	void createTrace(TraceDto trace) throws SQLException;
	void updateTrace(TraceDto trace) throws SQLException;
	void deleteTrace(int id) throws SQLException; // originalmente el parametro era String
}