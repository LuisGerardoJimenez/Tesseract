package mx.tesseract.dto;

/*
 * Luis Gerardo Jiménez
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.editor.entidad.ReferenciaParametro;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.GenericInterface;

public class PostPrecondicionDTO implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String redaccion;
	
	private String precondicion;
	
	private Integer idCasoUso;

	private List<ReferenciaParametro> referencias = new ArrayList<>();
	
	public PostPrecondicionDTO() {
	}

	public PostPrecondicionDTO(String redaccion,
			String precondicion, Integer idCasoUso) {
		this.redaccion = redaccion;
		this.precondicion = precondicion;
		this.idCasoUso = idCasoUso;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'999', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getRedaccion() {
		return this.redaccion;
	}

	public void setRedaccion(String redaccion) {
		this.redaccion = redaccion;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX_SOLO_STRING, shortCircuit = true)
	public String getPrecondicion() {
		return precondicion;
	}
	
	public void setPrecondicion(String precondicion) {
		this.precondicion = precondicion;
	}

	public Integer getIdCasoUso() {
		return idCasoUso;
	}

	public void setIdCasoUso(Integer idCasoUso) {
		this.idCasoUso = idCasoUso;
	}

	public List<ReferenciaParametro> getReferencias() {
		return referencias;
	}

	public void setReferencias(List<ReferenciaParametro> referencias) {
		this.referencias = referencias;
	}

}
