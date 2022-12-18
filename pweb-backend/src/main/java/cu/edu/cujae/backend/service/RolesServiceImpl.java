package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.RolesDto;
import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.core.service.RolesService;
import cu.edu.cujae.backend.core.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {

   @Autowired
   private JdbcTemplate jdbcTemplate;

   public RolesDto createNewDto(@NotNull ResultSet resultSet) throws SQLException {
      int id = resultSet.getInt(1);
      String role_name = resultSet.getString(2);
      String description = resultSet.getString(3);

      return new RolesDto(id, role_name, description);
   }

   @Override
   public List<RolesDto> listRoles() throws SQLException { // Aparentemente esta funcion ya esta
       List<RolesDto> list = new ArrayList<>();

       try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
           String function = "{?= call read_list_roles()}";

           //Connection connection = jdbcTemplate.getDataSource().getConnection();
           connection.setAutoCommit(false);
           CallableStatement statement = connection.prepareCall(function);
           statement.registerOutParameter(1, Types.OTHER);
           statement.execute();

           ResultSet resultSet = (ResultSet) statement.getObject(1);

           while (resultSet.next()) {
               RolesDto dto = createNewDto(resultSet);
               list.add(dto);
           }
       }

       return list;
   }

   @Override
   public RolesDto getRolesById(int rolesId) throws SQLException {
       RolesDto roles = null;

       try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
           PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(
                   "SELECT * FROM roles where roles.id = ?");

           pstmt.setInt(1, rolesId);

           ResultSet resultSet = pstmt.executeQuery();

           while (resultSet.next()) {
               roles = createNewDto(resultSet);
           }
       }

       return roles;
   }

   @Override
   public void createRoles(@NotNull RolesDto roles) throws SQLException {
       try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
           String function = "{call create_roles(?,?)}";

           CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
           statement.setString(1, roles.getRole_name());
           statement.setString(2, roles.getDescription());
           statement.execute();
       }
   }

  @Override
  public void updateRoles(@NotNull RolesDto roles) throws SQLException {
      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
          String function = "{call update_roles(?,?,?)}";

          CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
          statement.setInt(1, roles.getId());
          statement.setString(2, roles.getRole_name());
          statement.setString(3, roles.getDescription());
          statement.execute();
      }
  }

   @Override
   public void deleteRoles(int rolesId) throws SQLException {
       try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
           String function = "{call delete_roles(?)}";

           CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
           statement.setInt(1, rolesId);
           statement.execute();
       }
   }

   @Override
   public int getIdByName(String rolesName) throws SQLException {
      RolesDto roles = null;

      try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM roles where role_name = ?");

            pstmt.setString(1, rolesName);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int id_roles = resultSet.getInt(1);

                roles = new RolesDto();
                roles.setId(id_roles);
            }
      }

      return roles.getId();
   }
}