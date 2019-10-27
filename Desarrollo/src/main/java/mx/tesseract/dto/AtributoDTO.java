package mx.tesseract.dto;

import com.opensymphony.xwork2.validator.annotations.DoubleRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.util.Constantes;

public class AtributoDTO {
	
	private Integer id;
	private String nombre;
	private String descripcion;
	private Boolean obligatorio;
	private Integer longitud;
	private String formatoArchivo;
	private Float tamanioArchivo;
	private Integer idUnidadTamanio;
	private Integer idTipoDato;
	private String otroTipoDato;
	private Integer idEntidad;
	private Integer proyectoId;
	
	public AtributoDTO() {
//		Constructor por default
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'50', 'caracteres'})}", trim = true, maxLength = "50", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFABETICO, shortCircuit = true)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.trim();
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'999', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex= Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.trim();
	}

	public boolean isObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(Boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

//	Manda el mensaje cuando no se cumple la condicion
	@FieldExpressionValidator(expression = "not ((#action.model.idTipoDato eq 1 || #action.model.idTipoDato eq 2 || #action.model.idTipoDato eq 3) and #action.model.longitud eq null)", message = "%{getText('MSG4')}", shortCircuit= true)
	@IntRangeFieldValidator(message = "%{getText('MSG25',{'1', '10', 'digitos positivos'})}", min = "1", max = "9999999999", shortCircuit= true)
	public Integer getLongitud() {
		return longitud;
	}

	public void setLongitud(Integer longitud) {
		this.longitud = longitud;
	}

//	Manda el mensaje cuando no se cumple la condicion
	@FieldExpressionValidator(expression = "not (#action.model.idTipoDato eq 6 and #action.model.formatoArchivo eq '')", message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'50', 'caracteres'})}", trim = true, maxLength = "50", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFABETICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getFormatoArchivo() {
		return formatoArchivo;
	}

	public void setFormatoArchivo(String formatoArchivo) {
		this.formatoArchivo = formatoArchivo == null ? formatoArchivo : formatoArchivo.trim();
	}

//	Manda el mensaje cuando no se cumple la condicion
	@FieldExpressionValidator(expression = "not (#action.model.idTipoDato eq 6 and #action.model.tamanioArchivo eq null)", message = "%{getText('MSG4')}", shortCircuit= true)
	@DoubleRangeFieldValidator(message = "%{getText('MSG25',{'1.00', '12', 'digitos positivos'})}", minInclusive = "1.00", maxInclusive = "999999999.99", shortCircuit= true)
	public Float getTamanioArchivo() {
		return tamanioArchivo;
	}

	public void setTamanioArchivo(Float tamanioArchivo) {
		this.tamanioArchivo = tamanioArchivo;
	}

//	Manda el mensaje cuando no se cumple la condicion
	@FieldExpressionValidator(expression = "not (#action.model.idTipoDato eq 6 and #action.model.idUnidadTamanio eq -1)", message = "%{getText('MSG27')}", shortCircuit= true)
	public Integer getIdUnidadTamanio() {
		return idUnidadTamanio;
	}

	public void setIdUnidadTamanio(Integer idUnidadTamanio) {
		this.idUnidadTamanio = idUnidadTamanio;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX, shortCircuit = true)
	public Integer getIdTipoDato() {
		return idTipoDato;
	}

	public void setIdTipoDato(Integer idTipoDato) {
		this.idTipoDato = idTipoDato;
	}

//	Manda el mensaje cuando no se cumple la condicion
	@FieldExpressionValidator(expression = "not (#action.model.idTipoDato eq 7 and #action.model.otroTipoDato eq '')", message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'45', 'caracteres'})}", trim = true, maxLength = "45", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFABETICO, shortCircuit = true)
	public String getOtroTipoDato() {
		return otroTipoDato;
	}

	public void setOtroTipoDato(String otroTipoDato) {
		this.otroTipoDato = otroTipoDato == null? otroTipoDato : otroTipoDato.trim();
	}

	public Integer getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(Integer idEntidad) {
		this.idEntidad = idEntidad;
	}

	public Integer getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(Integer proyectoId) {
		this.proyectoId = proyectoId;
	}

}
