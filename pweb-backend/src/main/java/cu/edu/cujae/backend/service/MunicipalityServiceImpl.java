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
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import cu.edu.cujae.backend.core.dto.MunicipalityDto;
import cu.edu.cujae.backend.core.service.MunicipalityService;

@Service
public class MunicipalityServiceImpl implements MunicipalityService {

   @Autowired
   private JdbcTemplate jdbcTemplate;

   public MunicipalityDto createNewDto(ResultSet resultSet) throws SQLException {
      int codMun = resultSet.getInt(1);
      String namMun = resultSet.getString(2);

      return new MunicipalityDto(codMun, namMun);
   }

   @Override
   public List<MunicipalityDto> getMunicipalitys() throws SQLException { // Aparentemente esta funcion ya esta
      List<MunicipalityDto> list = new ArrayList<>();

      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{?= call read_list_municipality()}";

         //Connection connection = jdbcTemplate.getDataSource().getConnection();
         connection.setAutoCommit(false);
         CallableStatement statement = connection.prepareCall(function);
         statement.registerOutParameter(1, Types.OTHER);
         statement.execute();

         ResultSet resultSet = (ResultSet) statement.getObject(1);

         while (resultSet.next()) {
            MunicipalityDto dto = createNewDto(resultSet);
            list.add(dto);
         }
      }

      return list;
   }

   @Override
   public MunicipalityDto getMunicipalityById(int municipalityId) throws SQLException {
      MunicipalityDto municipality = null;

      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(
                 "SELECT * FROM municipality where id_municipality = ?");

         pstmt.setInt(1, municipalityId);

         ResultSet resultSet = pstmt.executeQuery();

         while (resultSet.next()) {
            municipality = createNewDto(resultSet);
         }
      }

      return municipality;
   }

   @Override
   public void createMunicipality(@NotNull MunicipalityDto municipality) throws SQLException { // Originalmente aqui no se creaba
                                                                                      // el ID
      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call create_municipality(?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         // statement.setInt(1, municipality.getCodMun());
         statement.setString(1, municipality.getNameMunicipality());
         statement.execute();
      }
   }

   @Override
   public void updateMunicipality(MunicipalityDto municipality) throws SQLException { // Originalmente este metodo
                                                                                      // actualizaba ademas de los
                                                                                      // valores del Dto su ID tambien
      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call update_municipality(?,?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         statement.setInt(1, municipality.getCodMun());
         statement.setString(2, municipality.getNameMunicipality());
         statement.execute();
      }
   }

   @Override
   public void deleteMunicipality(int municipalityId) throws SQLException {
      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call delete_municipality(?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);

         statement.setInt(1, municipalityId);
         statement.execute();
      }
   }

   @Override
   public int getIdByName(String municipalityName) throws SQLException {
      MunicipalityDto municipality = null;

      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(
                 "SELECT * FROM municipality where name = ?");

         pstmt.setString(1, municipalityName);

         ResultSet resultSet = pstmt.executeQuery();

         while (resultSet.next()) {
            int municipalityId = resultSet.getInt(1);

            municipality = new MunicipalityDto();
            municipality.setCodMun(municipalityId);
            ;
         }
      }

      return municipality.getCodMun();
   }
}