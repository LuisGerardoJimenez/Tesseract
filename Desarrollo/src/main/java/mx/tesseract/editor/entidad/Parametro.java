package mx.tesseract.editor.entidad;

import javax.persistence.CascadeType;

/*
 * Diego Efrain López Orozco
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.Table;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.GenericInterface;


@NamedNativeQueries({
	@NamedNativeQuery(name = "Parametro.findByNombreAndProyectoId", query = "SELECT p.* FROM parametro p WHERE p.nombre = ? and p.Proyectoid = ?", resultClass = Parametro.class),
	@NamedNativeQuery(name = "Parametro.findByIdProyecto", query = "SELECT p.* FROM parametro p WHERE p.Proyectoid = ?", resultClass = Parametro.class)
	})
@Entity
@Table(name = "parametro")
public class Parametro implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Proyectoid", referencedColumnName ="id")
	private Proyecto proyecto;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parametro", orphanRemoval = true)	
	private Set<MensajeParametro> parametros = new HashSet<MensajeParametro>(0);
	
	
	public Parametro() {
	}
	
	public Parametro(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public Parametro(String nombre) {
		this.nombre = nombre;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	public Set<MensajeParametro> getParametros() {
		return parametros;
	}

	public void setParametros(Set<MensajeParametro> parametros) {
		this.parametros = parametros;
	}

	@Override
	public String toString() {
		return "Parametro [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", proyecto=" + proyecto
				+ "]";
	}
	
}
