package cu.edu.cujae.backend.core.service;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.backend.core.dto.CollegeDto;

public interface CollegeService {
	List<CollegeDto> listColleges() throws SQLException;
	CollegeDto getCollegeById(int cdrId) throws SQLException; // originalmente el parametro era String
	int getIdByName(String collegeName) throws SQLException;
	void createCollege(CollegeDto cdr) throws SQLException;
	void updateCollege(CollegeDto cdr) throws SQLException;
	void deleteCollege(int id) throws SQLException; // originalmente el parametro era String
}