package cu.edu.cujae.backend.core.dto;

public class ElectoralProcessDto {

	private int id_electoral_process;
	private int municipality;
	private int roundnum;
	private int id_nominated;

	public int getId_nominated() {
		return id_nominated;
	}

	public void setId_nominated(int id_nominated) {
		this.id_nominated = id_nominated;
	}

	public ElectoralProcessDto() {
		super();
	}
	public ElectoralProcessDto(int id_electoral_process, int municipality, int roundNum, int id_nominated) {
		super();
		this.roundnum = roundNum;
		this.id_electoral_process = id_electoral_process;
		this.municipality = municipality;
		this.id_nominated = id_nominated;
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