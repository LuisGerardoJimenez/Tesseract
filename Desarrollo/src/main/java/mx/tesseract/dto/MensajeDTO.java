package mx.tesseract.dto;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.editor.entidad.EstadoElemento;
import mx.tesseract.editor.entidad.MensajeParametro;
import mx.tesseract.util.Constantes;

public class MensajeDTO {
	
	private String clave;
	private Integer idProyecto;
	private String numero;
	private String nombre;
	private Integer id;
	private String redaccion;
	private Boolean parametrizado;
	private String descripcion;
	EstadoElemento estadoElemento;
	private List<MensajeParametro> parametros = new ArrayList<>();
	
	public MensajeDTO() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'500', 'caracteres'})}", trim = true, maxLength = "500", shortCircuit= true)
	public String getRedaccion() {
		return redaccion;
	}

	public void setRedaccion(String redaccion) {
		this.redaccion = redaccion;
	}

	public Boolean getParametrizado() {
		return parametrizado;
	}

	public void setParametrizado(Boolean parametrizado) {
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

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public List<MensajeParametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<MensajeParametro> parametros) {
		this.parametros = parametros;
	}

	public EstadoElemento getEstadoElemento() {
		return estadoElemento;
	}

	public void setEstadoElemento(EstadoElemento estadoElemento) {
		this.estadoElemento = estadoElemento;
	}
}
