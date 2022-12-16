package cu.edu.cujae.backend.service;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.websocket.server.UriTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import cu.edu.cujae.backend.core.dto.CollegeDto;
import cu.edu.cujae.backend.core.service.CollegeService;

@Service
public class CollegeServiceImpl implements CollegeService {

   @Autowired
   private JdbcTemplate jdbcTemplate;

   public CollegeDto createNewDto(ResultSet resultSet) throws SQLException {
      int id_college = resultSet.getInt(1); // parametro 1
      String name_college = resultSet.getString(2); // parametro 2
      String address = resultSet.getString(3); // parametro 3
      int district = resultSet.getInt(4); // parametro 4

      return new CollegeDto(id_college, name_college, address, district);
   }

   @Override
   public List<CollegeDto> listColleges() throws SQLException { // Aparentemente esta funcion ya esta
      List<CollegeDto> list = new ArrayList<>();

      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{?= call read_list_college()}";

         //Connection connection = jdbcTemplate.getDataSource().getConnection();
         connection.setAutoCommit(false);
         CallableStatement statement = connection.prepareCall(function);
         statement.registerOutParameter(1, Types.OTHER);
         statement.execute();

         ResultSet resultSet = (ResultSet) statement.getObject(1);

         while (resultSet.next()) {
            CollegeDto dto = createNewDto(resultSet);
            list.add(dto);
         }
      }

      return list;
   }

   @Override
   public CollegeDto getCollegeById(int collegeId) throws SQLException {
      CollegeDto college = null;

      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(
                 "SELECT * FROM college where id_college = ?");

         pstmt.setInt(1, collegeId);

         ResultSet resultSet = pstmt.executeQuery();

         while (resultSet.next()) {
            college = createNewDto(resultSet);
         }
      }

      return college;
   }

   @Override
   public void createCollege(CollegeDto college) throws SQLException { // Originalmente aqui no se creaba el ID
      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call create_college(?,?,?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         statement.setString(1, college.getNameCollege());
         statement.setString(2, college.getAddress());
         statement.setInt(3, college.getDistrict()); // Es el ID
         statement.execute();
      }
   }

   @Override
   public void updateCollege(CollegeDto college) throws SQLException { // Originalmente este metodo actualizaba ademas de los valores del Dto su ID tambien
      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call update_college(?,?,?,?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         statement.setInt(1, college.getIdCollege());
         statement.setInt(2, college.getDistrict()); // Es el ID
         statement.setString(3, college.getNameCollege());
         statement.setString(4, college.getAddress());
         statement.execute();
      }
   }

   @Override
   public void deleteCollege(int collegeId) throws SQLException {
      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call delete_college(?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         statement.setInt(1, collegeId);
         statement.execute();
      }
   }

   @Override
   public int getIdByName(String collegeName) throws SQLException {
      CollegeDto college = null;

      try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM college where name_college = ?");

            pstmt.setString(1, collegeName);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
               int id_college = resultSet.getInt(1);
               String name_college = resultSet.getString(2);
               String address = resultSet.getString(3);
               int district = resultSet.getInt(4);

               college = new CollegeDto(id_college, name_college, address, district);
            }
      }

      return college.getIdCollege();
   }
}