package cu.edu.cujae.backend.core.dto;

public class NominatedDto /*extends VoterDto*/ {
	
	private String ocupation;
	private String profetion;
	private String phone;
	private String int_rev;
	private String bio_data;
	private int id;
	private int id_voter;
	//private int processE;
	private int cant_vote;

	public NominatedDto() {
		super();
	}
	public NominatedDto(String ocupation, String profetion, String phone,
			String int_rev, String bio_data, int id,
			int id_voter/*, int processE*/, int cant_vote) {
		super();
		this.ocupation = ocupation;
		this.profetion = profetion;
		this.phone = phone;
		this.int_rev = int_rev;
		this.bio_data = bio_data;
		this.id = id;
		this.id_voter = id_voter;
		//this.processE = processE;
		this.cant_vote = cant_vote;
	}
	public int getId() { // Nombre original = getIdNominated
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getId_voter() {
		return id_voter;
	}
	public void setId_voter(int id_voter) {
		this.id_voter = id_voter; // Cambie esta linea por la de abajo
		//setNumID(idVoter);
	}

//	public int getProcessE() {
//		return processE;
//	}
//	public void setProcessE(int processE) {
//		this.processE = processE;
//	}

	public int getCant_vote() {
		return cant_vote;
	}
	public void setCant_vote(int cant_vote) {
		this.cant_vote = cant_vote;
	}

	public String getOcupation() {
		return ocupation;
	}
	public void setOcupation(String ocupation) {
		this.ocupation = ocupation;
	}

	public String getProfetion() {
		return profetion;
	}
	public void setProfetion(String profetion) {
		this.profetion = profetion;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInt_rev() {
		return int_rev;
	}
	public void setInt_rev(String int_rev) {
		this.int_rev = int_rev;
	}
	
	public String getBio_data() {
		return bio_data;
	}
	public void setBio_data(String bio_data) {
		this.bio_data = bio_data;
	}
}