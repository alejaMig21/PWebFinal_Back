package cu.edu.cujae.backend.core.dto;

public class UserDto {
  private int id_user;
  private String user_name;
  private String user_password;
  private String full_name;
  private String email;
  private int roles;

  public UserDto(){  }

  public UserDto(int id_user, String user_name, String user_password, String full_name, String email, int roles) {
    this.id_user = id_user;
    this.user_name = user_name;
    this.user_password = user_password;
    this.full_name = full_name;
    this.email = email;
    this.roles = roles;
  }

  public int getId_user() {
    return id_user;
  }

  public void setId_user(int id_user) {
    this.id_user = id_user;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public String getUser_password() {
    return user_password;
  }

  public void setUser_password(String user_password) {
    this.user_password = user_password;
  }

  public String getFull_name() {
    return full_name;
  }

  public void setFull_name(String full_name) {
    this.full_name = full_name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getRoles() {
    return roles;
  }

  public void setRoles(int roles) {
    this.roles = roles;
  }
}