package cu.edu.cujae.backend.core.dto;

//import java.util.ArrayList;

public class CDRDto {
	
	private int id_cdr;
	private String name_cdr;
	private int id_president;
	private int college;

	public CDRDto(int id_cdr, String name_cdr, int id_president, int college) {
		super();
		this.id_cdr = id_cdr;
		this.name_cdr = name_cdr;
		this.id_president = id_president;
		this.college = college;
	}

	public CDRDto() {
		super();
	}

	public void setCollege(int college) {
		this.college = college;
	}

	public int getCollege() {
		return this.college;
	}

	public int getId_cdr() {
		return id_cdr;
	}
	public void setId_cdr(int id_cdr) {
		this.id_cdr = id_cdr;
	}
	public String getName_cdr() {
		return name_cdr;
	}
	public void setName_cdr(String name_cdr) {
		this.name_cdr = name_cdr;
	}
	public int getId_president() {
		return this.id_president;
	}
	public void setId_president(int id_president) {
		this.id_president = id_president;
	}
}