package cu.edu.cujae.backend.core.dto;

public class MunicipalityDto {
	private int codMun = 0;
	private String nameMunicipality;

	public MunicipalityDto(){}

	public MunicipalityDto(int codMun, String nameMunicipality) {
		this.codMun = codMun;
		this.nameMunicipality = nameMunicipality;
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
