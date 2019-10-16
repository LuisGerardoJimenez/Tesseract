package mx.tesseract.dto;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.editor.entidad.CasoUso;

public class TrayectoriaDTO {

	private Integer id;
	private String clave;
	private Boolean alternativa;
	private String condicion;
	private CasoUso casoUso;
	private Boolean finCasoUso;
	
	private String jsonReglasNegocio;
	private String jsonEntidades;
	private String jsonCasosUsoProyecto;
	private String jsonPantallas;
	private String jsonMensajes;
	private String jsonActores;
	private String jsonTerminosGls;
	private String jsonAtributos;
	private String jsonPasos;
	private String jsonTrayectorias;
	private String jsonAcciones;
	
	public String getJsonReglasNegocio() {
		return jsonReglasNegocio;
	}
	public void setJsonReglasNegocio(String jsonReglasNegocio) {
		this.jsonReglasNegocio = jsonReglasNegocio;
	}
	public String getJsonEntidades() {
		return jsonEntidades;
	}
	public void setJsonEntidades(String jsonEntidades) {
		this.jsonEntidades = jsonEntidades;
	}
	public String getJsonCasosUsoProyecto() {
		return jsonCasosUsoProyecto;
	}
	public void setJsonCasosUsoProyecto(String jsonCasosUsoProyecto) {
		this.jsonCasosUsoProyecto = jsonCasosUsoProyecto;
	}
	public String getJsonPantallas() {
		return jsonPantallas;
	}
	public void setJsonPantallas(String jsonPantallas) {
		this.jsonPantallas = jsonPantallas;
	}
	public String getJsonMensajes() {
		return jsonMensajes;
	}
	public void setJsonMensajes(String jsonMensajes) {
		this.jsonMensajes = jsonMensajes;
	}
	public String getJsonActores() {
		return jsonActores;
	}
	public void setJsonActores(String jsonActores) {
		this.jsonActores = jsonActores;
	}
	public String getJsonTerminosGls() {
		return jsonTerminosGls;
	}
	public void setJsonTerminosGls(String jsonTerminosGls) {
		this.jsonTerminosGls = jsonTerminosGls;
	}
	public String getJsonAtributos() {
		return jsonAtributos;
	}
	public void setJsonAtributos(String jsonAtributos) {
		this.jsonAtributos = jsonAtributos;
	}
	public String getJsonPasos() {
		return jsonPasos;
	}
	public void setJsonPasos(String jsonPasos) {
		this.jsonPasos = jsonPasos;
	}
	public String getJsonTrayectorias() {
		return jsonTrayectorias;
	}
	public void setJsonTrayectorias(String jsonTrayectorias) {
		this.jsonTrayectorias = jsonTrayectorias;
	}
	public String getJsonAcciones() {
		return jsonAcciones;
	}
	public void setJsonAcciones(String jsonAcciones) {
		this.jsonAcciones = jsonAcciones;
	}
	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5',{'un', 'número'})}", regex = "[0-9]*", shortCircuit = true)
	@IntRangeFieldValidator(message = "%{getText('MSG14',{'El', 'identificador', '0', '2147483647'})}", shortCircuit = true, min = "0", max = "2147483647") // Pendiente
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'5', 'caracteres'})}", trim = true, maxLength = "5", shortCircuit = true)
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public Boolean isAlternativa() {
		return alternativa;
	}
	public void setAlternativa(Boolean alternativa) {
		this.alternativa = alternativa;
	}
	public String getCondicion() {
		return condicion;
	}
	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
	public CasoUso getCasoUso() {
		return casoUso;
	}
	public void setCasoUso(CasoUso casoUso) {
		this.casoUso = casoUso;
	}
	public Boolean isFinCasoUso() {
		return finCasoUso;
	}
	public void setFinCasoUso(Boolean finCasoUso) {
		this.finCasoUso = finCasoUso;
	}
	
	
}
