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

import cu.edu.cujae.backend.core.dto.DistrictDto;
import cu.edu.cujae.backend.core.service.DistrictService;

@Service
public class DistrictServiceImpl implements DistrictService {

   @Autowired
   private JdbcTemplate jdbcTemplate;

   public DistrictDto createNewDto(@NotNull ResultSet resultSet) throws SQLException {
      int codDis = resultSet.getInt(1);
      String namDis = resultSet.getString(2);
      int idMunicipality = resultSet.getInt(3);

      return new DistrictDto(codDis, namDis, idMunicipality);
   }

   @Override
   public List<DistrictDto> listDistricts() throws SQLException { // Aparentemente esta funcion ya esta
      List<DistrictDto> list = new ArrayList<>();

      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{?= call read_list_district()}";

         //Connection connection = jdbcTemplate.getDataSource().getConnection();
         connection.setAutoCommit(false);
         CallableStatement statement = connection.prepareCall(function);
         statement.registerOutParameter(1, Types.OTHER);
         statement.execute();

         ResultSet resultSet = (ResultSet) statement.getObject(1);

         while (resultSet.next()) {
            DistrictDto dto = createNewDto(resultSet);
            list.add(dto);
         }
      }

      return list;
   }

   @Override
   public DistrictDto getDistrictById(int districtId) throws SQLException {
	   DistrictDto district = null;

      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(
                 "SELECT * FROM district where id_district = ?");

         pstmt.setInt(1, districtId);

         ResultSet resultSet = pstmt.executeQuery();

         while (resultSet.next()) {
            district = createNewDto(resultSet);
         }
      }

      return district;
   }

   @Override
   public void createDistrict(@NotNull DistrictDto district) throws SQLException { // Originalmente aqui no se creaba el ID
      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call create_district(?,?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         //statement.setInt(1, district.getCodDis());
         statement.setString(1, district.getNamDis());
         statement.setInt(2, district.getIdMunicipality());
         statement.execute();
      }
   }

   @Override
   public void updateDistrict(@NotNull DistrictDto district) throws SQLException { // Originalmente este metodo actualizaba ademas de los valores del Dto su ID tambien
      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         System.out.println("El municipio de la circunscripcion es " + district.getIdMunicipality());
         String function = "{call update_district(?,?,?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);
         statement.setInt(1, district.getCodDis());
         statement.setInt(2, district.getIdMunicipality());
         statement.setString(3, district.getNamDis());
         statement.execute();
      }
   }

   @Override
   public void deleteDistrict(int districtId) throws SQLException {
      System.out.println("El Distrito/Circunscripcion que llego fue " + districtId);
      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         String function = "{call delete_district(?)}";

         CallableStatement statement = jdbcTemplate.getDataSource().getConnection().prepareCall(function);

         statement.setInt(1, districtId);
         statement.execute();
      }
   }

   @Override
   public int getIdByName(String districtName) throws SQLException {
      DistrictDto district = null;

      try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
         PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(
                 "SELECT * FROM district where name_district = ?");

         pstmt.setString(1, districtName);

         ResultSet resultSet = pstmt.executeQuery();

         while (resultSet.next()) {
            int idDistrict = resultSet.getInt(1);

            district = new DistrictDto();
            district.setCodDis(idDistrict);
         }
      }

      return district.getCodDis();
   }
}