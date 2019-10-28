package mx.tesseract.dto;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.util.Constantes;

public class PasoDTO {
	
	private Integer id;
	private Integer numero;
	private String realizaActor;
	private String redaccion;
	private Integer idTrayectoria;
	private Integer idVerbo;
	private String otroVerbo;
	private String verbo;
	//private List<ReferenciaParametro> referencias = new ArrayList<ReferenciaParametro>();


	public PasoDTO() {
	}

	public PasoDTO(Integer id, Integer numero, String realizaActor, String redaccion,
			Integer idTrayectoria, Integer idVerbo, String verbo, String otroVerbo) {
		this.id = id;
		this.numero = numero;
		this.realizaActor = realizaActor;
		this.redaccion = redaccion;
		this.idTrayectoria = idTrayectoria;
		this.idVerbo = idVerbo;
		this.verbo = verbo;
		this.otroVerbo = otroVerbo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX_SOLO_STRING, shortCircuit = true)
	public String getRealizaActor() {
		return realizaActor;
	}

	public void setRealizaActor(String realizaActor) {
		this.realizaActor = realizaActor;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'500', 'caracteres'})}", trim = true, maxLength = "500", shortCircuit= true)
	public String getRedaccion() {
		return redaccion;
	}

	public void setRedaccion(String redaccion) {
		this.redaccion = redaccion;
	}
	
	public Integer getIdTrayectoria() {
		return idTrayectoria;
	}
	
	public void setIdTrayectoria(Integer idTrayectoria) {
		this.idTrayectoria = idTrayectoria;
	}

	public Integer getIdVerbo() {
		return idVerbo;
	}

	public void setIdVerbo(Integer idVerbo) {
		this.idVerbo = idVerbo;
	}

	public String getOtroVerbo() {
		return otroVerbo;
	}

	public void setOtroVerbo(String otroVerbo) {
		this.otroVerbo = otroVerbo;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX_SOLO_STRING, shortCircuit = true)
	public String getVerbo() {
		return verbo;
	}

	public void setVerbo(String verbo) {
		this.verbo = verbo;
	}
	
}
