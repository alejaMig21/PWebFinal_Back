package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.core.dto.VoterDto;
import cu.edu.cujae.backend.core.service.UserService;
import cu.edu.cujae.backend.core.service.VoterService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

   @Autowired
   private JdbcTemplate jdbcTemplate;

   public UserDto createNewDto(@NotNull ResultSet resultSet) throws SQLException {
      int id_user = resultSet.getInt(1);
      String user_name = resultSet.getString(2);
      String user_password = resultSet.getString(3);
      String full_name = resultSet.getString(4);
      String email = resultSet.getString(5);
      int roles = resultSet.getInt(6);

      return new UserDto(id_user, user_name, user_password, full_name, email, roles);
   }

   @Override
   public List<UserDto> listUsers() throws SQLException { // Aparentemente esta funcion ya esta
       List<UserDto> list = new ArrayList<>();

       try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
           String function = "{?= call read_list_users()}";

           //Connection connection = jdbcTemplate.getDataSource().getConnection();
           connection.setAutoCommit(false);
           CallableStatement statement = connection.prepareCall(function);
           statement.registerOutParameter(1, Types.OTHER);
           statement.execute();

           ResultSet resultSet = (ResultSet) statement.getObject(1);

           while (resultSet.next()) {
               UserDto dto = createNewDto(resultSet);
               list.add(dto);
           }
       }

       return list;
   }

   @Override
   public UserDto getUserById(int voterId) throws SQLException {
       UserDto voter = null;

       try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
           PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(
                   "SELECT * FROM users where users.id_user = ?");

           pstmt.setInt(1, voterId);

           ResultSet resultSet = pstmt.executeQuery();

           while (resultSet.next()) {
               voter = createNewDto(resultSet);
           }
       }

       return voter;
   }

   @Override
   public void createUser(@NotNull UserDto user) throws SQLException {
       try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
           String function = "{call create_user(?,?,?,?,?)}";

           CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
           statement.setString(1, user.getUser_name());
           statement.setString(2, user.getUser_password());
           statement.setString(3, user.getFull_name());
           statement.setString(4, user.getEmail());
           statement.setInt(5, user.getRoles());
           statement.execute();
       }
   }

  @Override
  public void updateUser(@NotNull UserDto user) throws SQLException {
      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
          String function = "{call update_user(?,?,?,?,?,?)}";

          CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
          statement.setInt(1, user.getId_user());
          statement.setString(2, user.getUser_name());
          statement.setString(3, user.getUser_password());
          statement.setString(4, user.getFull_name());
          statement.setString(5, user.getEmail());
          statement.setInt(6, user.getRoles());
          statement.execute();
      }
  }

   @Override
   public void deleteUser(int userId) throws SQLException {
       try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
           String function = "{call delete_user(?)}";

           CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
           statement.setInt(1, userId);
           statement.execute();
       }
   }

   @Override
   public int getIdByName(String userName) throws SQLException {
      UserDto voter = null;

      try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM users where user_name = ?");

            pstmt.setString(1, userName);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int id_user = resultSet.getInt(1);

                voter = new UserDto();
                voter.setId_user(id_user);
            }
      }

      return voter.getId_user();
   }
}