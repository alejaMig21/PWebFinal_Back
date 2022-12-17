package cu.edu.cujae.backend.core.dto;

public class DelegatedDto {

	private int id;
	private int id_nominated;
	private int roundnum;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_nominated() {
		return id_nominated;
	}

	public void setId_nominated(int id_nominated) {
		this.id_nominated = id_nominated;
	}

	public int getRoundnum() {
		return roundnum;
	}

	public void setRoundnum(int roundnum) {
		this.roundnum = roundnum;
	}

	public DelegatedDto(){
		super();
	}

	public DelegatedDto(int id, int id_nominated, int roundnum) {
		this.id = id;
		this.id_nominated = id_nominated;
		this.roundnum = roundnum;
	}
}