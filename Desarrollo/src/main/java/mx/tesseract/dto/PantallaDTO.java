package mx.tesseract.dto;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.util.Constantes;

public class PantallaDTO {
	
	private Integer id;
	private String clave;
	private String numero;
	private String nombre;
	private String descripcion;
	private Integer idProyecto;
	private Integer idModulo;
	private byte[] pantallaB64;
	
	public PantallaDTO() {
//		Constructor por default
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'19', 'caracteres'})}", trim = true, maxLength = "19", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex= Constantes.REGEX_NUMERO_PANTALLAS, shortCircuit = true)
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero == null ? numero : numero.trim();
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'50', 'caracteres'})}", trim = true, maxLength = "50", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex= Constantes.REGEX_CAMPO_ALFABETICO, shortCircuit = true)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre == null ? nombre : nombre.trim();
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'999', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex= Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion == null ? descripcion : descripcion.trim();
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

	public byte[] getPantallaB64() {
		return pantallaB64;
	}

	public void setPantallaB64(byte[] pantallaB64) {
		this.pantallaB64 = pantallaB64;
	}

}
