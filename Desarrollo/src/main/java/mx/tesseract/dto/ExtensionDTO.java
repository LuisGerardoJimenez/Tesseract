package mx.tesseract.dto;

import java.io.Serializable;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.util.GenericInterface;

public class ExtensionDTO implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String causa;
	private String region;
	
	private CasoUso casoUsoOrigen;
	
	private CasoUso casoUsoDestino;
	
	//@OneToMany(fetch = FetchType.LAZY, mappedBy = "extension", orphanRemoval = true)
	//private List<ReferenciaParametro> referencias = new ArrayList<>();


	public ExtensionDTO() {
	}

	public ExtensionDTO(String causa, String region, CasoUso casoUsoOrigen, CasoUso casoUsoDestino) {
		this.causa = causa;
		this.region = region;
		this.casoUsoOrigen = casoUsoOrigen;
		this.casoUsoDestino = casoUsoDestino;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'500', 'caracteres'})}", trim = true, maxLength = "500", shortCircuit= true)
	public String getCausa() {
		return this.causa;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'500', 'caracteres'})}", trim = true, maxLength = "500", shortCircuit= true)
	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public CasoUso getCasoUsoOrigen() {
		return casoUsoOrigen;
	}

	public void setCasoUsoOrigen(CasoUso casoUsoOrigen) {
		this.casoUsoOrigen = casoUsoOrigen;
	}

	public CasoUso getCasoUsoDestino() {
		return casoUsoDestino;
	}

	public void setCasoUsoDestino(CasoUso casoUsoDestino) {
		this.casoUsoDestino = casoUsoDestino;
	}
}
