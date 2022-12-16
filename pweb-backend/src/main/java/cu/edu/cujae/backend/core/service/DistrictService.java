package cu.edu.cujae.backend.core.service;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.backend.core.dto.DistrictDto;

public interface DistrictService {
	List<DistrictDto> listDistricts() throws SQLException;
	DistrictDto getDistrictById(int districtId) throws SQLException; // originalmente el parametro era String
	int getIdByName(String districtName) throws SQLException;
	void createDistrict(DistrictDto user) throws SQLException;
	void updateDistrict(DistrictDto user) throws SQLException;
	void deleteDistrict(int id) throws SQLException; // originalmente el parametro era String
}