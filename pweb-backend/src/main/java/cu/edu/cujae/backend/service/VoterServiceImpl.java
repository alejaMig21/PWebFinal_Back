package cu.edu.cujae.backend.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cu.edu.cujae.backend.core.dto.VoterDto;
import cu.edu.cujae.backend.core.service.VoterService;

@Service
public class VoterServiceImpl implements VoterService {

   @Autowired
   private JdbcTemplate jdbcTemplate;

   public VoterDto createNewDto(@NotNull ResultSet resultSet) throws SQLException {
      int id_voter = resultSet.getInt(1);
      String name = resultSet.getString(2);
      String adr_voter = resultSet.getString(4);
      String date = resultSet.getString(3);
      String cause = resultSet.getString(5);
      int cdr = resultSet.getInt(6);
      int vote = resultSet.getInt(7);  	

      return new VoterDto(id_voter, name, adr_voter, date, cdr, vote, cause);
   }

   @Override
   public List<VoterDto> listVoters() throws SQLException { // Aparentemente esta funcion ya esta
       List<VoterDto> list = new ArrayList<>();

       try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
           String function = "{?= call read_list_voter()}";

           //Connection connection = jdbcTemplate.getDataSource().getConnection();
           connection.setAutoCommit(false);
           CallableStatement statement = connection.prepareCall(function);
           statement.registerOutParameter(1, Types.OTHER);
           statement.execute();

           ResultSet resultSet = (ResultSet) statement.getObject(1);

           while (resultSet.next()) {
               VoterDto dto = createNewDto(resultSet);
               list.add(dto);
           }
       }

       return list;
   }

    @Override
    public List<VoterDto> listVotersByCdr(int cdr) throws SQLException { // Aparentemente esta funcion ya esta
        List<VoterDto> list = new ArrayList<>();

        VoterDto voter = null;

        try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(
                    "SELECT * FROM voters where voters.cdr = ?");

            pstmt.setInt(1, cdr);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                voter = createNewDto(resultSet);
                list.add(voter);
            }
        }

        return list;
    }

   @Override
   public VoterDto getVoterById(int voterId) throws SQLException {
	   VoterDto voter = null;

       try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
           PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(
                   "SELECT * FROM voters where voters.id_voter = ?");

           pstmt.setInt(1, voterId);

           ResultSet resultSet = pstmt.executeQuery();

           while (resultSet.next()) {
               voter = createNewDto(resultSet);
           }
           System.out.println("Llego al controller y el votante es " + voter.getName());
       }

       return voter;
   }

   @Override
   public void createVoter(@NotNull VoterDto voter) throws SQLException { // Originalmente aqui no se creaba el ID
       try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
           String function = "{call create_votante(?,?,?,?)}";

           CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
           statement.setString(1, voter.getName());
           statement.setString(2, voter.getDate());
           statement.setString(3, voter.getAdr_voter());
           statement.setInt(4, voter.getCdr());
           statement.execute();
       }
   }

  @Override
  public void updateVoter(@NotNull VoterDto voter) throws SQLException { // Originalmente este metodo actualizaba ademas de los valores del Dto su ID tambien
      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
          String function = "{call update_votante(?,?,?,?,?)}";

          CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
          statement.setInt(1, voter.getId_voter());
          statement.setString(2, voter.getName());
          statement.setString(3, voter.getDate().toString());
          statement.setString(4, voter.getAdr_voter());
          statement.setInt(5, voter.getCdr());
          statement.execute();
      }
  }

   @Override
   public void deleteVoter(int voterId) throws SQLException {
       try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
           String function = "{call delete_voter(?)}";

           CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
           statement.setInt(1, voterId);
           statement.execute();
       }
   }

   @Override
   public int getIdByName(String voterName) throws SQLException {
      VoterDto voter = null;

      try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM voters where name = ?");

            pstmt.setString(1, voterName);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int id_voter = resultSet.getInt(1);

                voter = new VoterDto();
                voter.setId_voter(id_voter);
                System.out.println("El id es " + voter.getId_voter());
            }
      }

      return voter.getId_voter();
   }
}