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

import cu.edu.cujae.backend.core.dto.NominatedDto;
import cu.edu.cujae.backend.core.service.NominatedService;

@Service
public class NominatedServiceImpl implements NominatedService {

   @Autowired
   private JdbcTemplate jdbcTemplate;

   public NominatedDto createNewDto(ResultSet resultSet) throws SQLException {
      int id = resultSet.getInt(1); // parametro 6
      int id_voter = resultSet.getInt(2); // parametro 7
      String ocupation = resultSet.getString(3); // parametro 1
      String profetion = resultSet.getString(4); // parametro 2
      String phone = resultSet.getString(5); // parametro 3
      String int_rev = resultSet.getString(6); // parametro 4
      String bio_data = resultSet.getString(7); // parametro 5
      //int process_e = resultSet.getInt(8); // parametro 8
      int cant_vote = resultSet.getInt(8); // parametro 9

      return new NominatedDto(ocupation, profetion, phone, int_rev, bio_data, id, id_voter, /*process_e,*/ cant_vote);
   }

   @Override
   public List<NominatedDto> listNominateds() throws SQLException {
      List<NominatedDto> list = new ArrayList<>();

      //String function = "{?= call read_list_nominated()}";

      //Connection connection = jdbcTemplate.getDataSource().getConnection();

      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{?= call read_list_nominated()}";
         connection.setAutoCommit(false);
         CallableStatement statement = connection.prepareCall(function);
         statement.registerOutParameter(1, Types.OTHER);
         statement.execute();

         ResultSet resultSet = (ResultSet) statement.getObject(1);

         while (resultSet.next()) {
            NominatedDto dto = createNewDto(resultSet);
            list.add(dto);
         }
      }

      return list;
   }

   @Override
   public NominatedDto getNominatedById(int nominatedId) throws SQLException {
      NominatedDto nominated = null;

      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(
                 "SELECT * FROM nominated where id_nominated = ?");

         pstmt.setInt(1, nominatedId);

         ResultSet resultSet = pstmt.executeQuery();

         while (resultSet.next()) {
            nominated = createNewDto(resultSet);
         }
      }

      return nominated;
   }

   @Override
   public int getIdByName(String nominatedName) throws SQLException { // Obtiene el id de voter que hay dentro de nominated
      NominatedDto nominated = null;
      int idNom = 0;

      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(
                 "SELECT * FROM voter where name = ?");

         pstmt.setString(1, nominatedName);

         ResultSet resultSet = pstmt.executeQuery();

         nominated = new NominatedDto();

         int id_voter = 0;
         //int idNom = 0;
         while (resultSet.next()) {
            id_voter = resultSet.getInt(1);

            idNom = getIdNomByIdVot(id_voter); // Con el id voter obtenemos el id nominated
         }
      }

      return idNom;
   }

   public int getIdNomByIdVot(int idVoter) throws SQLException {
      NominatedDto nominated = null;

      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(
                 "SELECT * FROM nominated where id_voter = ?");

         pstmt.setInt(1, idVoter);

         ResultSet resultSet = pstmt.executeQuery();

         nominated = new NominatedDto();

         while (resultSet.next()) {
            int id_nominated = resultSet.getInt(1);

            nominated.setId(id_nominated);
         }
      }

      return nominated.getId();
   }

   @Override
   public void createNominated(@NotNull NominatedDto nominated) throws SQLException { // Originalmente aqui no se creaba el ID
      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call create_nominated(?,?,?,?,?,?,?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         statement.setInt(1, nominated.getId_voter());
         statement.setString(2, nominated.getOcupation());
         statement.setString(3, nominated.getProfetion());
         statement.setString(4, nominated.getPhone());
         statement.setString(5, nominated.getInt_rev());
         statement.setString(6, nominated.getBio_data());
         //statement.setInt(7, nominated.getProcessE());
         statement.setInt(7, nominated.getCant_vote());
         statement.execute();
      }
   }

   @Override
   public void updateNominated(@NotNull NominatedDto nominated) throws SQLException { // Originalmente este metodo actualizaba ademas de los valores del Dto su ID tambien
      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call update_nominated(?,?,?,?,?,?,?,?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         statement.setInt(1, nominated.getId());
         statement.setInt(2, nominated.getId_voter());
         statement.setString(3, nominated.getOcupation());
         statement.setString(4, nominated.getProfetion());
         statement.setString(5, nominated.getPhone());
         statement.setString(6, nominated.getInt_rev());
         //statement.setInt(7, nominated.getProcessE());
         statement.setString(7, nominated.getBio_data());
         statement.setInt(8, nominated.getCant_vote());
         statement.execute();
      }
   }

   @Override
   public void deleteNominated(int id) throws SQLException {
      try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call delete_nominated(?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         //statement.setInt(1, nominatedId); <------ Linea original
         statement.setInt(1, id);
         statement.execute();
      }
   }
}