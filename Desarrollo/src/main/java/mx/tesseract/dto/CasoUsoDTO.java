package mx.tesseract.dto;

import javax.persistence.Column;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.util.Constantes;

public class CasoUsoDTO {
	
	private String clave;
	private Integer id;
	private String numero;
	private String nombre;
	private String descripcion;
	
	private Integer idEstadoElemento;
	
	@Column(name = "redaccionActores", length = 999)
	private String redaccionActores;
	@Column(name = "redaccionEntradas", length = 999)
	private String redaccionEntradas;
	@Column(name = "redaccionSalidas", length = 999)
	private String redaccionSalidas;
	@Column(name = "redaccionReglasNegocio", length = 999)
	private String redaccionReglasNegocio;
	
	private Integer idProyecto;
	private Integer idModulo;
	
	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'200', 'caracteres'})}", trim = true, maxLength = "200", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFABETICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getNombre() {
		return nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'1000', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	public String getRedaccionActores() {
		return redaccionActores;
	}

	public void setRedaccionActores(String redaccionActores) {
		this.redaccionActores = redaccionActores;
	}

	@StringLengthFieldValidator(message = "%{getText('MSG6',{'1000', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	public String getRedaccionEntradas() {
		return redaccionEntradas;
	}

	public void setRedaccionEntradas(String redaccionEntradas) {
		this.redaccionEntradas = redaccionEntradas;
	}

	@StringLengthFieldValidator(message = "%{getText('MSG6',{'1000', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	public String getRedaccionSalidas() {
		return redaccionSalidas;
	}

	public void setRedaccionSalidas(String redaccionSalidas) {
		this.redaccionSalidas = redaccionSalidas;
	}

	@StringLengthFieldValidator(message = "%{getText('MSG6',{'1000', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	public String getRedaccionReglasNegocio() {
		return redaccionReglasNegocio;
	}

	public void setRedaccionReglasNegocio(String redaccionReglasNegocio) {
		this.redaccionReglasNegocio = redaccionReglasNegocio;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Integer getIdEstadoElemento() {
		return idEstadoElemento;
	}

	public void setIdEstadoElemento(Integer idEstadoElemento) {
		this.idEstadoElemento = idEstadoElemento;
	}

	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	public Integer getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(Integer idModulo) {
		this.idModulo = idModulo;
	}
	
	
}
