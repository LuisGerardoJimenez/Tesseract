package mx.tesseract.editor.entidad;

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

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.GenericInterface;


@NamedNativeQueries({
	@NamedNativeQuery(name = "Parametro.findByNombreAndProyectoId", query = "SELECT p.* FROM parametro p WHERE p.nombre = ? and p.Proyectoid = ?", resultClass = Parametro.class),
	@NamedNativeQuery(name = "Parametro.findByIdProyecto", query = "SELECT p.* FROM parametro p WHERE p.Proyectoid = ?", resultClass = Parametro.class)
	})
@Entity
@Table(name = "Parametro")
public class Parametro implements Serializable, GenericInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nombre;
	private String descripcion;
	private Proyecto proyecto;

	public Parametro() {
	}
	
	public Parametro(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public Parametro(String nombre) {
		this.nombre = nombre;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "nombre", nullable = false, length = 45)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion", nullable = false, length = 45)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	//Cambios1710
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Proyectoid", referencedColumnName ="id", nullable = false)
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	
}
