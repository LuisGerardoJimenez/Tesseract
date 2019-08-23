package mx.tesseract.dto;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.util.Constantes;

public class ActorDTO {
	
	private Integer id; 
	private String nombre;
	private String descripcion;
	private Integer idProyecto;
	private Integer cardinalidadId;
	private String otraCardinalidad;
	
	public ActorDTO() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'200', 'caracteres'})}", trim = true, maxLength = "200", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFABETICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.trim();
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'999', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.trim();
	}

	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}
	
	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX, shortCircuit = true)
	public Integer getCardinalidadId() {
		return cardinalidadId;
	}

	public void setCardinalidadId(Integer cardinalidadId) {
		this.cardinalidadId = cardinalidadId;
	}

//	Manda el mensaje cuando no se cumple la condicion
	@FieldExpressionValidator(expression = "not (#action.model.cardinalidadId eq 3 and #action.model.otraCardinalidad eq '')", message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG25',{'1', '45', 'caracteres'})}", trim = true, minLength = "2", maxLength = "45", shortCircuit = true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFABETICO, shortCircuit = true)
	public String getOtraCardinalidad() {
		return otraCardinalidad;
	}

	public void setOtraCardinalidad(String otraCardinalidad) {
		this.otraCardinalidad = otraCardinalidad.trim();
	}

}
