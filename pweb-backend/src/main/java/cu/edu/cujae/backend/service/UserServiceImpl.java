package cu.edu.cujae.backend.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.core.service.RoleService;
import cu.edu.cujae.backend.core.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RoleService roleService;

	@Override
	public void createUser(UserDto user) throws SQLException {
		
		try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
			CallableStatement CS = conn.prepareCall(
					"{call create_user(?, ?, ?, ?, ?, ?, ?)}");
			
			CS.setString(1, UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
			CS.setString(2, user.getUsername());
			CS.setString(3, user.getFullName());
			CS.setString(4, getMd5Hash(user.getPassword()));
			CS.setString(5, user.getEmail());
			CS.setString(6, user.getIdentification());
			
			//roles separados por coma, ej: "1, 2, 4"
			String roles = user.getRoles().stream().map(r -> r.getId().toString()).collect(Collectors.joining(","));
			CS.setString(7, roles);
			CS.executeUpdate();	
		} 
		
		
	}

	@Override
	public List<UserDto> listUsers() throws SQLException {
		List<UserDto> userList = new ArrayList<UserDto>();
		try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
			ResultSet rs = conn.createStatement().executeQuery(
					"SELECT * FROM users");
			
			while(rs.next()){
				userList.add(new UserDto(rs.getString("id_user")
						,rs.getString("username")
						,rs.getString("full_name")
						,rs.getString("password")
						,rs.getString("email")
						,rs.getString("identification")
						,roleService.getRolesByUserId(rs.getString("id"))));
			}
		} 
		return userList;
	}
	
	@Override
	public void updateUser(UserDto user) throws SQLException {
		try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement(
				      "update users set username = ?, full_name = ?, email = ?, identification = ? where id = ?");
	
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getFullName());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getIdentification());
			pstmt.setString(5, user.getId());
	
			pstmt.executeUpdate();
		}
	}

	@Override
	public UserDto getUserById(String userId) throws SQLException {
		
		UserDto user = null; 
		try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
		
			PreparedStatement pstmt = conn.prepareStatement(
				      "SELECT * FROM users where id = ?");
	
			pstmt.setString(1, userId);
	
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				user = new UserDto(rs.getString("id")
						,rs.getString("username")
						,rs.getString("full_name")
						,rs.getString("password")
						,rs.getString("email")
						,rs.getString("identification")
						,roleService.getRolesByUserId(rs.getString("id")));
			}
		}
		
		return user;
	}

	@Override
	public void deleteUser(String userId) throws SQLException {
		try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
			CallableStatement CS = conn.prepareCall(
					"{call delete_user(?)}");
			
			CS.setString(1, userId);
			CS.executeUpdate();	
		}
	}
	
	private String getMd5Hash(String password) {
		MessageDigest md;
		String md5Hash = "";
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
		    byte[] digest = md.digest();
		    md5Hash = DatatypeConverter
		      .printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return md5Hash;
	}

}
