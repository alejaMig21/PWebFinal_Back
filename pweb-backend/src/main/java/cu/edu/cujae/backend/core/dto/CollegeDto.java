package cu.edu.cujae.backend.core.dto;

public class CollegeDto {
	
	private int idCollege;
	private String nameCollege;
	private String address;
	private int district;

	public int getIdCollege() {
		return this.idCollege;
	}

	public void setIdCollege(int id_college) {
		this.idCollege = id_college;
	}

	public String getNameCollege() {
		return this.nameCollege;
	}

	public void setNameCollege(String name_college) {
		this.nameCollege = name_college;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getDistrict() {
		return this.district;
	}

	public void setDistrict(int district) {
		this.district = district;
	}

	public CollegeDto() {
		super();
	}

	public CollegeDto(int id_college, String name_college, String address, int district) {
		this.idCollege = id_college;
		this.nameCollege = name_college;
		this.address = address;
		this.district = district;
	}
}