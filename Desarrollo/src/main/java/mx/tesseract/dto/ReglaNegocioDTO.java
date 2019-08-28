package mx.tesseract.dto;

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
	private TipoReglaNegocio tipoReglaNegocio;
	private Integer Atributoid_unicidad;
	private Integer Atributoid_fechaI;
	private Integer Atributoid_fechaT;
	private Integer TipoComparacionid;
	private String TipoComparacionnombre;
	private Atributo atributoComp1;
	private Atributo atributoComp2;
	private Operador operadorComp;
	private Integer Operadorid;
	private Atributo Atributoid_expReg;
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
	
	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX_STRING, shortCircuit = true)
	public Integer getIdTipoRN() {
		return idTipoRN;
	}

	public void setIdTipoRN(Integer idTipoRN) {
		this.idTipoRN = idTipoRN;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'100', 'caracteres'})}", trim = true, maxLength = "50", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex= Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getRedaccion() {
		return redaccion;
	}

	public void setRedaccion(String redaccion) {
		this.redaccion = redaccion;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX, shortCircuit = true)
	public Integer getAtributoid_unicidad() {
		return Atributoid_unicidad;
	}

	public void setAtributoid_unicidad(Integer atributoid_unicidad) {
		Atributoid_unicidad = atributoid_unicidad;
	}
	
	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX, shortCircuit = true)
	public Integer getAtributoid_fechaI() {
		return Atributoid_fechaI;
	}

	public void setAtributoid_fechaI(Integer atributoid_fechaI) {
		Atributoid_fechaI = atributoid_fechaI;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX, shortCircuit = true)
	public Integer getAtributoid_fechaT() {
		return Atributoid_fechaT;
	}

	public void setAtributoid_fechaT(Integer atributoid_fechaT) {
		Atributoid_fechaT = atributoid_fechaT;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX, shortCircuit = true)
	public Integer getTipoComparacionid() {
		return TipoComparacionid;
	}

	public void setTipoComparacionid(Integer tipoComparacionid) {
		TipoComparacionid = tipoComparacionid;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX, shortCircuit = true)
	public Atributo getAtributoComp1() {
		return atributoComp1;
	}

	public void setAtributoComp1(Atributo atributoComp1) {
		this.atributoComp1 = atributoComp1;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX, shortCircuit = true)
	public Atributo getAtributoComp2() {
		return atributoComp2;
	}

	public void setAtributoComp2(Atributo atributoComp2) {
		this.atributoComp2 = atributoComp2;
	}
	
	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX, shortCircuit = true)
	public Integer getOperadorid() {
		return Operadorid;
	}

	public void setOperadorid(Integer operadorid) {
		Operadorid = operadorid;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX, shortCircuit = true)
	public Atributo getAtributoid_expReg() {
		return Atributoid_expReg;
	}

	public void setAtributoid_expReg(Atributo atributoid_expReg) {
		Atributoid_expReg = atributoid_expReg;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'50', 'caracteres'})}", trim = true, maxLength = "50", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex= Constantes.REGEX_CAMPO_ALFABETICO, shortCircuit = true)
	public String getExpresionRegular() {
		return expresionRegular;
	}

	public void setExpresionRegular(String expresionRegular) {
		this.expresionRegular = expresionRegular;
	}

	public String getTipoComparacionnombre() {
		return TipoComparacionnombre;
	}

	public void setTipoComparacionnombre(String tipoComparacionnombre) {
		TipoComparacionnombre = tipoComparacionnombre;
	}

	public TipoReglaNegocio getTipoReglaNegocio() {
		return tipoReglaNegocio;
	}

	public void setTipoReglaNegocio(TipoReglaNegocio tipoReglaNegocio) {
		this.tipoReglaNegocio = tipoReglaNegocio;
	}

	public Operador getOperadorComp() {
		return operadorComp;
	}

	public void setOperadorComp(Operador operadorComp) {
		this.operadorComp = operadorComp;
	}



}
