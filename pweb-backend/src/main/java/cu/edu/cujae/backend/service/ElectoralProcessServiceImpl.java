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

import cu.edu.cujae.backend.core.dto.ElectoralProcessDto;
import cu.edu.cujae.backend.core.service.ElectoralProcessService;

@Service
public class ElectoralProcessServiceImpl implements ElectoralProcessService {

   @Autowired
   private JdbcTemplate jdbcTemplate;

   public ElectoralProcessDto createNewDto(@NotNull ResultSet resultSet) throws SQLException {
      int id_electoral_process = resultSet.getInt(1);
      int municipality = resultSet.getInt(2);
      int roundnum = resultSet.getInt(3);
      int id_nominated = resultSet.getInt(3);

      return new ElectoralProcessDto(id_electoral_process, roundnum, municipality, id_nominated);
   }

   @Override
   public List<ElectoralProcessDto> listElectoralProcess() throws SQLException { // Aparentemente esta funcion ya esta
      List<ElectoralProcessDto> list = new ArrayList<>();

      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{?= call read_list_electoral_process()}";

         //Connection connection = jdbcTemplate.getDataSource().getConnection();
         connection.setAutoCommit(false);
         CallableStatement statement = connection.prepareCall(function);
         statement.registerOutParameter(1, Types.OTHER);
         statement.execute();

         ResultSet resultSet = (ResultSet) statement.getObject(1);

         while (resultSet.next()) {
            ElectoralProcessDto dto = createNewDto(resultSet);
            list.add(dto);
         }
      }

      return list;
   }

   @Override
   public ElectoralProcessDto getElectoralProcessById(int electoralProcessId) throws SQLException {
      ElectoralProcessDto electoralProcess = null;

      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(
                 "SELECT * FROM electoral_process where id_electoral_process = ?");

         pstmt.setInt(1, electoralProcessId);

         ResultSet resultSet = pstmt.executeQuery();

         while (resultSet.next()) {
            electoralProcess = createNewDto(resultSet);
         }
      }

      return electoralProcess;
   }

   @Override
   public void createElectoralProcess(@NotNull ElectoralProcessDto electoralProcess) throws SQLException { // Originalmente aqui no se creaba el ID
      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call create_electoral_process(?,?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         statement.setInt(1, electoralProcess.getMunicipality());
         statement.setInt(2, electoralProcess.getRoundnum());
         statement.setInt(3, electoralProcess.getId_nominated());
         statement.execute();
      }
   }

   @Override
   public void updateElectoralProcess(@NotNull ElectoralProcessDto electoralProcess) throws SQLException { // Originalmente este metodo actualizaba ademas de los valores del Dto su ID tambien
      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call update_electoral_process(?,?,?,?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         statement.setInt(1, electoralProcess.getId_electoral_process());
         statement.setInt(2, electoralProcess.getMunicipality());
         statement.setInt(3, electoralProcess.getRoundnum());
         statement.setInt(4, electoralProcess.getId_nominated());
         statement.execute();
      }
   }

   @Override
   public void deleteElectoralProcess(int electoralProcessId) throws SQLException {
      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call delete_electoral_process(?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);

         statement.setInt(1, electoralProcessId);
         statement.execute();
      }
   }
}