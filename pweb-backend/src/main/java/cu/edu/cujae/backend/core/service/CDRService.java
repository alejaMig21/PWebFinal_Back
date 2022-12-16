package cu.edu.cujae.backend.core.service;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.backend.core.dto.CDRDto;

public interface CDRService {
	List<CDRDto> listCDRs() throws SQLException;
	CDRDto getCDRById(int cdrId) throws SQLException; // originalmente el parametro era String
    int getIdByName(String cdrName) throws SQLException;
	void createCDR(CDRDto cdr) throws SQLException;
	void updateCDR(CDRDto cdr) throws SQLException;
	void deleteCDR(int id) throws SQLException; // originalmente el parametro era String
}