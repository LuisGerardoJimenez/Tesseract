package mx.tesseract.editor.entidad;

/*
 * Luis Gerardo Jiménez
 */

import static javax.persistence.GenerationType.IDENTITY;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.ElementoInterface;
import mx.tesseract.util.GenericInterface;

@NamedNativeQueries({
	@NamedNativeQuery(name = "Elemento.findNextNumber", query = "SELECT COALESCE(MAX(e.numero), 0) FROM elemento e WHERE e.Proyectoid = ? AND e.clave = ?"),
	})

@NamedQueries({
	@NamedQuery(name = "Elemento.consultarElementosByProyectoAndClave", query = "SELECT e FROM Elemento e JOIN e.proyecto p WHERE p.id = :idProyecto AND e.clave = :clave"),
	@NamedQuery(name = "Elemento.consultarElementosByProyectoAndNombreAndClave", query = "SELECT e FROM Elemento e JOIN e.proyecto p  WHERE p.id = :idProyecto AND e.nombre = :nombre AND e.clave = :clave"),
	@NamedQuery(name = "Elemento.consultarElementosByProyectoAndIdAndNombreAndClave", query = "SELECT e FROM Elemento e JOIN e.proyecto p  WHERE p.id = :idProyecto AND e.id != :id AND e.nombre = :nombre AND e.clave = :clave"),
	@NamedQuery(name = "Elemento.consultarPantallasByProyectoAndModulo", query = "SELECT e FROM Elemento e JOIN e.proyecto p JOIN e.modulo m WHERE p.id = :idProyecto AND e.clave = :clave AND m.id = :idModulo "),
	@NamedQuery(name = "Elemento.consultarPantallasByProyectoAndModuloAndNumero", query = "SELECT e FROM Elemento e JOIN e.proyecto p JOIN e.modulo m WHERE p.id = :idProyecto AND e.clave = :clave AND m.id = :idModulo AND e.numero = :numero"),
	@NamedQuery(name = "Elemento.consultarPantallasByProyectoAndModuloAndIdAndNumero", query = "SELECT e FROM Elemento e JOIN e.proyecto p JOIN e.modulo m WHERE p.id = :idProyecto AND e.clave = :clave AND m.id = :idModulo AND e.id != :id AND e.numero = :numero"),
	@NamedQuery(name = "Elemento.consultarPantallasByProyectoAndModuloAndNombre", query = "SELECT e FROM Elemento e JOIN e.proyecto p JOIN e.modulo m WHERE p.id = :idProyecto AND e.clave = :clave AND m.id = :idModulo AND e.nombre = :nombre"),
	@NamedQuery(name = "Elemento.consultarPantallasByProyectoAndModuloAndIdAndNombre", query = "SELECT e FROM Elemento e JOIN e.proyecto p JOIN e.modulo m WHERE p.id = :idProyecto AND e.clave = :clave AND m.id = :idModulo AND e.id != :id AND e.nombre = :nombre"),
	})

@Entity
@Table(name = "elemento")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="clave", discriminatorType = DiscriminatorType.STRING, length=10)

@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
		  include = JsonTypeInfo.As.PROPERTY,
		  property = "type")
		@JsonSubTypes({
		  @Type(value = Mensaje.class, name = "mensaje"),
		  @Type(value = Pantalla.class, name = "pantalla"),
		  @Type(value = Pantalla.class, name = "reglaNegocio")
		})
public class Elemento implements Serializable, GenericInterface, ElementoInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "clave", insertable = false)
	private String clave;
	
	@Column(name = "numero")
	private String numero;
	
	@Column(name = "nombre")
	private String nombre;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Proyectoid")
	private Proyecto proyecto;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EstadoElementoid", referencedColumnName="id")
	private EstadoElemento estadoElemento;

	public Elemento() {
	}

	public Elemento(String clave, String numero, String nombre,
			Proyecto proyecto, String descripcion, EstadoElemento estadoElemento) {
		this.clave = clave;
		this.numero = numero;
		this.nombre = nombre;
		this.proyecto = proyecto;
		this.descripcion = descripcion;
		this.estadoElemento = estadoElemento;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.trim();
	}
	
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.trim();
	}
	
	public EstadoElemento getEstadoElemento() {
		return estadoElemento;
	}

	public void setEstadoElemento(EstadoElemento estadoElemento) {
		this.estadoElemento = estadoElemento;
	}

	
}
