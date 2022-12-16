package cu.edu.cujae.backend.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cu.edu.cujae.backend.core.dto.RoleDto;
import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.core.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
    private JdbcTemplate jdbcTemplate;

//	@Override
//	public List<RoleDto> getRolesByUserId(String userId) throws SQLException {
//		return null;
//	}
//
//	@Override
//	public List<RoleDto> listRoles() throws SQLException {
//		return null;
//	}
//
//	@Override
//	public RoleDto getRoleById(Long roleId) throws SQLException {
//		return null;
//	}



	@Override
	public List<RoleDto> getRolesByUserId(String userId) throws SQLException {
		List<RoleDto> roleList = new ArrayList<RoleDto>();
		
		try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(
				      "SELECT id, role_name, description FROM roles inner join user_role on user_role.role_id = xrole.id where user_role.user_id = ?");
	
			pstmt.setString(1, userId);
	
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				roleList.add(new RoleDto(rs.getLong("id")
						,rs.getString("role_name")
						,rs.getString("description")));
			}
		}
		return roleList;
	}


	@Override
	public List<RoleDto> listRoles() throws SQLException {
		List<RoleDto> roleList = new ArrayList<RoleDto>();
		try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
			ResultSet rs = conn.createStatement().executeQuery(
					"SELECT * FROM roles");
			
			while(rs.next()){
				roleList.add(new RoleDto(rs.getLong("id")
						,rs.getString("role_name")
						,rs.getString("description")));
			}
		} 
		return roleList;
	}

	@Override
	public RoleDto getRoleById(Long roleId) throws SQLException {
		RoleDto role = null; 
		try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
		
			PreparedStatement pstmt = conn.prepareStatement(
				      "SELECT * FROM roles where id = ?");
	
			pstmt.setLong(1, roleId);
	
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				role = new RoleDto(rs.getLong("id")
						,rs.getString("role_name")
						,rs.getString("description"));
			}
		}
		
		return role;
	}
}
