package mx.tesseract.editor.entidad;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import mx.tesseract.admin.entidad.Proyecto;

@Entity
@Table(name = "mensaje")
@PrimaryKeyJoinColumn(name = "Elementoid", referencedColumnName = "id")
//@JsonTypeName("mensaje")
public class Mensaje extends Elemento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "redaccion")
	private String redaccion;
	
	@Column(name = "parametrizado")
	private boolean parametrizado;
	
//	private Set<MensajeParametro> parametros = new HashSet<MensajeParametro>(0);

	public Mensaje() {
	}

//	public Mensaje(String clave, String numero, String nombre,
//			Proyecto proyecto, String descripcion, EstadoElemento estadoElemento, String redaccion, boolean parametrizado) {
//		super(clave, numero, nombre, proyecto, descripcion, estadoElemento);
//		this.redaccion = redaccion;
//		this.parametrizado = parametrizado;
//	}

	public String getRedaccion() {
		return this.redaccion;
	}

	public void setRedaccion(String redaccion) {
		this.redaccion = redaccion;
	}

	public boolean isParametrizado() {
		return this.parametrizado;
	}

	public void setParametrizado(boolean parametrizado) {
		this.parametrizado = parametrizado;
	}

//	@OneToMany(fetch = FetchType.EAGER, mappedBy = "mensaje", cascade = CascadeType.ALL)	
//	public Set<MensajeParametro> getParametros() {
//		return parametros;
//	}
//
//	public void setParametros(Set<MensajeParametro> parametros) {
//		this.parametros = parametros;
//	}
//	
//	@JsonIgnore
//	@Transient
//	public String getType() {
//		return "mensaje";
//	}
	

}
