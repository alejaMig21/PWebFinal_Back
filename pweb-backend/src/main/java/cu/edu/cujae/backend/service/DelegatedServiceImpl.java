package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.CollegeDto;
import cu.edu.cujae.backend.core.dto.DelegatedDto;
import cu.edu.cujae.backend.core.service.CollegeService;
import cu.edu.cujae.backend.core.service.DelegatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DelegatedServiceImpl implements DelegatedService {

   @Autowired
   private JdbcTemplate jdbcTemplate;

   public DelegatedDto createNewDto(ResultSet resultSet) throws SQLException {
      int id = resultSet.getInt(1);
      int id_nominated = resultSet.getInt(2);
      int roundnum = resultSet.getInt(3);

      return new DelegatedDto(id, id_nominated, roundnum);
   }

   @Override
   public List<DelegatedDto> listDelegateds() throws SQLException { // Aparentemente esta funcion ya esta
      List<DelegatedDto> list = new ArrayList<>();

      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{?= call read_list_delegated()}";

         //Connection connection = jdbcTemplate.getDataSource().getConnection();
         connection.setAutoCommit(false);
         CallableStatement statement = connection.prepareCall(function);
         statement.registerOutParameter(1, Types.OTHER);
         statement.execute();

         ResultSet resultSet = (ResultSet) statement.getObject(1);

         while (resultSet.next()) {
            DelegatedDto dto = createNewDto(resultSet);
            list.add(dto);
         }
      }

      return list;
   }

   @Override
   public DelegatedDto getDelegatedById(int delegatedId) throws SQLException {
      DelegatedDto delegated = null;

      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(
                 "SELECT * FROM delegated where id = ?");

         pstmt.setInt(1, delegatedId);

         ResultSet resultSet = pstmt.executeQuery();

         while (resultSet.next()) {
            delegated = createNewDto(resultSet);
         }
      }

      return delegated;
   }

   @Override
   public void createDelegated(DelegatedDto delegated) throws SQLException { // Originalmente aqui no se creaba el ID
      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call create_delegated(?,?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         statement.setInt(1, delegated.getId_nominated());
         statement.setInt(2, delegated.getRoundnum());
         statement.execute();
      }
   }

   @Override
   public void updateDelegated(DelegatedDto delegated) throws SQLException { // Originalmente este metodo actualizaba ademas de los valores del Dto su ID tambien
      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call update_delegated(?,?,?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         statement.setInt(1, delegated.getId());
         statement.setInt(2, delegated.getId_nominated());
         statement.setInt(3, delegated.getRoundnum());
         statement.execute();
      }
   }

   @Override
   public void deleteDelegated(int delegatedId) throws SQLException {
      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call delete_delegated(?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         statement.setInt(1, delegatedId);
         statement.execute();
      }
   }

//   @Override
//   public int getIdByName(String delegatedName) throws SQLException {
//      DelegatedDto delegated = null;
//
//      try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
//            PreparedStatement pstmt = connection.prepareStatement(
//                    "SELECT * FROM delegated where name_college = ?");
//
//            pstmt.setString(1, delegatedName);
//
//            ResultSet resultSet = pstmt.executeQuery();
//
//            while (resultSet.next()) {
//               int id_college = resultSet.getInt(1);
//               String name_college = resultSet.getString(2);
//               String address = resultSet.getString(3);
//               int district = resultSet.getInt(4);
//
//               delegated = new CollegeDto(id_college, name_college, address, district);
//            }
//      }
//
//      return delegated.getIdCollege();
//   }
}