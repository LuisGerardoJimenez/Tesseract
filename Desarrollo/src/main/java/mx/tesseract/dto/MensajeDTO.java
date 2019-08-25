package mx.tesseract.dto;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.util.Constantes;

public class MensajeDTO {
	
	private String clave;
	private Integer idProyecto;
	private Integer numero;
	private String nombre;
	private Integer id;
	private String redaccion;
	private Integer parametrizado;
	private String descripcion;
	
	public MensajeDTO() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'100', 'caracteres'})}", trim = true, maxLength = "100", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFABETICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getRedaccion() {
		return redaccion;
	}

	public void setRedaccion(String redaccion) {
		this.redaccion = redaccion;
	}

	public Integer getParametrizado() {
		return parametrizado;
	}

	public void setParametrizado(Integer parametrizado) {
		this.parametrizado = parametrizado;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'100', 'caracteres'})}", trim = true, maxLength = "100", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFABETICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
}
