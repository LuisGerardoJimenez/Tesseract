package mx.tesseract.dto;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.util.Constantes;

public class AccionDTO {
	
	private Integer id;
	private String nombre;
	private String descripcion;
	private Integer idTipoAccion;
	private Integer idPantallaDestino;
	private Integer idPantalla;
	private String imagenB64;
	private String nombreTipoAccion;
	private String claveModuloPantallaDestino;
	private String numeroPantallaDestino;
	private String nombrePantallaDestino;
	
	
	public AccionDTO() {
//		Constructor por default
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'45', 'caracteres'})}", trim = true, maxLength = "45", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex= Constantes.REGEX_CAMPO_ALFABETICO, shortCircuit = true)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'999', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex= Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX, shortCircuit = true)
	public Integer getIdTipoAccion() {
		return idTipoAccion;
	}

	public void setIdTipoAccion(Integer idTipoAccion) {
		this.idTipoAccion = idTipoAccion;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX, shortCircuit = true)
	public Integer getIdPantallaDestino() {
		return idPantallaDestino;
	}

	public void setIdPantallaDestino(Integer idPantallaDestino) {
		this.idPantallaDestino = idPantallaDestino;
	}

	public Integer getIdPantalla() {
		return idPantalla;
	}

	public void setIdPantalla(Integer idPantalla) {
		this.idPantalla = idPantalla;
	}

	public String getImagenB64() {
		return imagenB64;
	}

	public void setImagenB64(String imagenB64) {
		this.imagenB64 = imagenB64;
	}

	public String getNombreTipoAccion() {
		return nombreTipoAccion;
	}

	public void setNombreTipoAccion(String nombreTipoAccion) {
		this.nombreTipoAccion = nombreTipoAccion;
	}

	public String getClaveModuloPantallaDestino() {
		return claveModuloPantallaDestino;
	}

	public void setClaveModuloPantallaDestino(String claveModuloPantallaDestino) {
		this.claveModuloPantallaDestino = claveModuloPantallaDestino;
	}

	public String getNumeroPantallaDestino() {
		return numeroPantallaDestino;
	}

	public void setNumeroPantallaDestino(String numeroPantallaDestino) {
		this.numeroPantallaDestino = numeroPantallaDestino;
	}

	public String getNombrePantallaDestino() {
		return nombrePantallaDestino;
	}

	public void setNombrePantallaDestino(String nombrePantallaDestino) {
		this.nombrePantallaDestino = nombrePantallaDestino;
	}

}
