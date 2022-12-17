package cu.edu.cujae.backend.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cu.edu.cujae.backend.core.dto.CDRDto;
import cu.edu.cujae.backend.core.service.CDRService;

@Service
public class CDRServiceImpl implements CDRService {

   @Autowired
   private JdbcTemplate jdbcTemplate;

   public CDRDto createNewDto(ResultSet resultSet) throws SQLException {
      int id_cdr = resultSet.getInt(1); // parametro 1
      String name_cdr = resultSet.getString(2); // parametro 2
      int college = resultSet.getInt(3); // parametro 4
      int id_president = resultSet.getInt(4); // parametro 3

      return new CDRDto(id_cdr, name_cdr, id_president, college);
   }

   @Override
   public List<CDRDto> listCDRs() throws SQLException { // Aparentemente esta funcion ya esta
      List<CDRDto> list = new ArrayList<>();

      //String function = "{?= call read_list_cdr()}";

      //Connection connection = jdbcTemplate.getDataSource().getConnection();

      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{?= call read_list_cdr()}";
         connection.setAutoCommit(false);
         CallableStatement statement = connection.prepareCall(function);
         statement.registerOutParameter(1, Types.OTHER);
         statement.execute();

         ResultSet resultSet = (ResultSet) statement.getObject(1);

         while (resultSet.next()) {
            CDRDto dto = createNewDto(resultSet);
            list.add(dto);
         }
      }

      return list;
   }

   @Override
   public CDRDto getCDRById(int cdrId) throws SQLException {
      CDRDto cdr = null;

      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(
                 "SELECT * FROM cdrdto where id_cdr = ?");

         pstmt.setInt(1, cdrId);

         ResultSet resultSet = pstmt.executeQuery();

         while (resultSet.next()) {
            cdr = createNewDto(resultSet);
         }
      }

      return cdr;
   }

   @Override
   public void createCDR(@NotNull CDRDto cdr) throws SQLException { // Originalmente aqui no se creaba el ID
      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call create_cdr(?,?,?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         //statement.setInt(1, cdr.getCodCDR());
         statement.setString(1, cdr.getName_cdr());
         statement.setInt(2, cdr.getCollege());
         statement.setInt(3, cdr.getId_president());
         statement.execute();
      }
   }

   @Override
   public void updateCDR(@NotNull CDRDto cdr) throws SQLException { // Originalmente este metodo actualizaba ademas de los valores del Dto su ID tambien
      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call update_cdr(?,?,?,?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         statement.setString(1, cdr.getName_cdr());
         statement.setInt(2, cdr.getId_president());
         statement.setInt(3, cdr.getCollege());
         statement.setInt(4, cdr.getId_cdr());
         statement.execute();
      }
   }

   @Override
   public void deleteCDR(int cdrId) throws SQLException {
      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call delete_cdr(?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         //statement.setInt(1, nominatedId); <------ Linea original
         statement.setInt(1, cdrId);
         statement.execute();
      }
   }

   @Override
   public int getIdByName(String cdrName) throws SQLException {
      CDRDto cdr = null;

      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(
                 "SELECT * FROM cdrdto where name_cdr = ?");

         pstmt.setString(1, cdrName);

         ResultSet resultSet = pstmt.executeQuery();

         while (resultSet.next()) {
            int cdrId = resultSet.getInt(1);
            String namCDR = resultSet.getString(2);
            int id_college = resultSet.getInt(3);
            int id_presidentCDR = resultSet.getInt(4);

            cdr = new CDRDto(cdrId, namCDR, id_presidentCDR, id_college);
         }
      }

      return cdr.getId_cdr();
   }
}