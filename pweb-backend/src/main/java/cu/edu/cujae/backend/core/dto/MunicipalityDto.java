package cu.edu.cujae.backend.core.dto;

public class MunicipalityDto {
	private int codMun = 0;
	private String nameMunicipality;
	private int id_electoral_process;
	public MunicipalityDto(){}

	public MunicipalityDto(int codMun, String nameMunicipality, int id_electoral_process) {
		this.codMun = codMun;
		this.nameMunicipality = nameMunicipality;
		this.id_electoral_process = id_electoral_process;
	}

	public int getId_electoral_process() {
		return id_electoral_process;
	}

	public void setId_electoral_process(int id_electoral_process) {
		this.id_electoral_process = id_electoral_process;
	}

	public int getCodMun() {
		return codMun;
	}

	public void setCodMun(int codMun) {
		this.codMun = codMun;
	}

	public String getNameMunicipality() {
		return nameMunicipality;
	}

	public void setNameMunicipality(String nameMunicipality) {
		this.nameMunicipality = nameMunicipality;
	}
}
