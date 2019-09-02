package mx.tesseract.dto;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.Operador;
import mx.tesseract.editor.entidad.TipoReglaNegocio;
import mx.tesseract.util.Constantes;

public class ReglaNegocioDTO {
	
	private Integer idProyecto;
	private Integer id; 
	private String clave;
	private String numero;
	private String nombre;
	private String descripcion;
	private String redaccion;
	private Integer idTipoRN;
	private Integer idEntidad1;
	private Integer idAtributo1;
	private Integer idOperador;
	private Integer idEntidad2;
	private Integer idAtributo2;
	private Integer idEntidadFormato;
	private Integer idAtributoFormato;
	private Integer idEntidadUnicidad;
	private Integer idAtributoUnicidad;
	private Atributo atributoComp1;
	private Atributo atributoComp2;
	private Operador operadorComp;
	private String expresionRegular;

	public ReglaNegocioDTO() {
		
		
	}
	
	public String getClave() {
		return clave;
	}


	public void setClave(String clave) {
		this.clave = clave;
	}
	

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'1000', 'caracteres'})}", trim = true, maxLength = "50", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_NUMERICO_ENTERO, shortCircuit = true)
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'50', 'caracteres'})}", trim = true, maxLength = "50", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex= Constantes.REGEX_CAMPO_ALFABETICO, shortCircuit = true)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.trim();
	}
	
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX, shortCircuit = true)
	public Integer getIdTipoRN() {
		return idTipoRN;
	}

	public void setIdTipoRN(Integer idTipoRN) {
		this.idTipoRN = idTipoRN;
	}
	

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'500', 'caracteres'})}", trim = true, maxLength = "500", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex= Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getRedaccion() {
		return redaccion;
	}

	public void setRedaccion(String redaccion) {
		this.redaccion = redaccion;
	}
	
	/*TIPO: COMPARACIÓN DE ATRIBUTOS*/

	@FieldExpressionValidator(expression = "not (#action.model.idTipoRN eq 2 and #action.model.idEntidad1 eq -1)", message = "%{getText('MSG27')}", shortCircuit= true)
	public Integer getIdEntidad1() {
		return idEntidad1;
	}

	public void setIdEntidad1(Integer idEntidad1) {
		this.idEntidad1 = idEntidad1;
	}
	
	@FieldExpressionValidator(expression = "not (#action.model.idTipoRN eq 2 and #action.model.idAtributo1 eq -1)", message = "%{getText('MSG27')}", shortCircuit= true)
	public Integer getIdAtributo1() {
		return idAtributo1;
	}

	public void setIdAtributo1(Integer idAtributo1) {
		this.idAtributo1 = idAtributo1;
	}

	@FieldExpressionValidator(expression = "not (#action.model.idTipoRN eq 2 and #action.model.idOperador eq -1)", message = "%{getText('MSG27')}", shortCircuit= true)
	public Integer getIdOperador() {
		return idOperador;
	}

	public void setIdOperador(Integer idOperador) {
		this.idOperador = idOperador;
	}

	@FieldExpressionValidator(expression = "not (#action.model.idTipoRN eq 2 and #action.model.idEntidad2 eq -1)", message = "%{getText('MSG27')}", shortCircuit= true)
	public Integer getIdEntidad2() {
		return idEntidad2;
	}

	public void setIdEntidad2(Integer idEntidad2) {
		this.idEntidad2 = idEntidad2;
	}

	@FieldExpressionValidator(expression = "not (#action.model.idTipoRN eq 2 and #action.model.idAtributo2 eq -1)", message = "%{getText('MSG27')}", shortCircuit= true)
	public Integer getIdAtributo2() {
		return idAtributo2;
	}

	public void setIdAtributo2(Integer idAtributo2) {
		this.idAtributo2 = idAtributo2;
	}


	/*TIPO: FORMATO CORRECTO*/
	
	@FieldExpressionValidator(expression = "not (#action.model.idTipoRN eq 9 and #action.model.idEntidadFormato eq -1)", message = "%{getText('MSG27')}", shortCircuit= true)
	public Integer getIdEntidadFormato() {
		return idEntidadFormato;
	}

	public void setIdEntidadFormato(Integer idEntidadFormato) {
		this.idEntidadFormato = idEntidadFormato;
	}
	
	@FieldExpressionValidator(expression = "not (#action.model.idTipoRN eq 9 and #action.model.idAtributoFormato eq -1)", message = "%{getText('MSG27')}", shortCircuit= true)
	public Integer getIdAtributoFormato() {
		return idAtributoFormato;
	}
	
	public void setIdAtributoFormato(Integer idAtributoFormato) {
		this.idAtributoFormato = idAtributoFormato;
	}
	
	@FieldExpressionValidator(expression = "not (#action.model.idTipoRN eq 9 and #action.model.expresionRegular eq -1)", message = "%{getText('MSG27')}", shortCircuit= true)
	public String getExpresionRegular() {
		return expresionRegular;
	}

	public void setExpresionRegular(String expresionRegular) {
		this.expresionRegular = expresionRegular;
		this.expresionRegular = expresionRegular != null? expresionRegular.trim() : expresionRegular;
	}


	/*TIPO: UNICIDAD DE PARÁMETROS*/

	@FieldExpressionValidator(expression = "not (#action.model.idTipoRN eq 3 and #action.model.idEntidadUnicidad eq -1)", message = "%{getText('MSG27')}", shortCircuit= true)
	public Integer getIdEntidadUnicidad() {
		return idEntidadUnicidad;
	}

	public void setIdEntidadUnicidad(Integer idEntidadUnicidad) {
		this.idEntidadUnicidad = idEntidadUnicidad;
	}
	
	@FieldExpressionValidator(expression = "not (#action.model.idTipoRN eq 3 and #action.model.idAtributoUnicidad eq -1)", message = "%{getText('MSG27')}", shortCircuit= true)
	public Integer getIdAtributoUnicidad() {
		return idAtributoUnicidad;
	}

	public void setIdAtributoUnicidad(Integer idAtributoUnicidad) {
		this.idAtributoUnicidad = idAtributoUnicidad;
	}


	public Atributo getAtributoComp1() {
		return atributoComp1;
	}

	public void setAtributoComp1(Atributo atributoComp1) {
		this.atributoComp1 = atributoComp1;
	}

	public Atributo getAtributoComp2() {
		return atributoComp2;
	}

	public void setAtributoComp2(Atributo atributoComp2) {
		this.atributoComp2 = atributoComp2;
	}
	
	public Operador getOperadorComp() {
		return operadorComp;
	}

	public void setOperadorComp(Operador operadorComp) {
		this.operadorComp = operadorComp;
	}
	
	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}




}
