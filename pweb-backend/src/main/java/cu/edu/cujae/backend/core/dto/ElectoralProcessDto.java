package cu.edu.cujae.backend.core.dto;

public class ElectoralProcessDto {

	private int id_electoral_process;
	private int municipality;
	private int roundnum;
	private int total_nominateds;

	public int getTotal_nominateds() {
		return total_nominateds;
	}

	public void setTotal_nominateds(int total_nominateds) {
		this.total_nominateds = total_nominateds;
	}

	public ElectoralProcessDto() {
		super();
	}
	public ElectoralProcessDto(int id_electoral_process, int municipality, int roundNum, int total_nominateds) {
		super();
		this.roundnum = roundNum;
		this.id_electoral_process = id_electoral_process;
		this.municipality = municipality;
		this.total_nominateds = total_nominateds;
	}
	public int getId_electoral_process() {
		return id_electoral_process;
	}
	public void setId_electoral_process(int id_electoral_process) {
		this.id_electoral_process = id_electoral_process;
	}
	public void setMunicipality(int municipality) {
		this.municipality = municipality;
	}
	public int getMunicipality() {
		return municipality;
	}
	public int getRoundnum() {
		return roundnum;
	}
	public void setRoundnum(int roundnum) {
		this.roundnum = roundnum;
	}
}