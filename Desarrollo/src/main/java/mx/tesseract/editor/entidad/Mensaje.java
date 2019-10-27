package mx.tesseract.editor.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonIgnore;

import mx.tesseract.util.ElementoInterface;
import mx.tesseract.util.GenericInterface;

@Entity
@Table(name = "mensaje")
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "Elementoid", referencedColumnName = "id")
@DiscriminatorValue("MSG")
@JsonIgnoreProperties(value = { "parametros" })
public class Mensaje extends Elemento implements Serializable, GenericInterface, ElementoInterface {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "redaccion")
	private String redaccion;
	
	@Column(name = "parametrizado")
	private Boolean parametrizado;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mensaje", orphanRemoval = true)	
	private List<MensajeParametro> parametros = new ArrayList<>();

	public Mensaje() {
//		Constructor por default
	}

	public String getRedaccion() {
		return this.redaccion;
	}

	public void setRedaccion(String redaccion) {
		this.redaccion = redaccion.trim();
	}
	
	public Boolean getParametrizado() {
		return parametrizado;
	}

	public void setParametrizado(Boolean parametrizado) {
		this.parametrizado = parametrizado;
	}

	public List<MensajeParametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<MensajeParametro> parametros) {
		this.parametros = parametros;
	}
	
	@JsonIgnore
	@Transient
	public String getType() {
		return "mensaje";
	}
	

}
