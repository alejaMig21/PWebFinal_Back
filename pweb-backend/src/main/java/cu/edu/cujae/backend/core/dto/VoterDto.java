package cu.edu.cujae.backend.core.dto;

import java.time.LocalDate;

public class VoterDto {

  private int id_voter;
  private String name;
  private String adr_voter;
  private String date;
  private int cdr;
  private int vote;
  private String cause;

  public VoterDto() {
    super();
  }

  public VoterDto(int id_voter, String name, String adr_voter,
    String date, int cdr, int vote, String cause) {
    super();
    this.id_voter = id_voter;
    this.name = name;
    this.adr_voter = adr_voter;
    this.date = date;
    this.cdr = cdr;
    this.vote = vote;
    this.cause = cause;
  }

  public String getCause() {
    return cause;
  }

  public void setCause(String cause) {
    this.cause = cause;
  }

  public int getVote() {
    return vote;
  }

  public void setVote(int vote) {
    this.vote = vote;
  }

  public int getCdr() {
    return cdr;
  }

  public void setCdr(int cdr) {
    this.cdr = cdr;
  }

  public int getId_voter() {
    return id_voter;
  }

  public void setId_voter(int id_voter) {
    this.id_voter = id_voter;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAdr_voter() {
    return adr_voter;
  }

  public void setAdr_voter(String adrVot) {
    this.adr_voter = adrVot;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String dateVot) {
    this.date = dateVot;
  }

}