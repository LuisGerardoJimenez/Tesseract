package mx.tesseract.editor.entidad;

/*
 * Luis Gerardo Jiménez
 */

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.tesseract.util.GenericInterface;

@NamedNativeQueries({
	@NamedNativeQuery(name = "Atributo.findByEntidad", query = "SELECT a.* FROM atributo a WHERE a.EntidadElementoid = ?", resultClass = Atributo.class),
	@NamedNativeQuery(name = "Atributo.findByNameAndEntidad", query = "SELECT a.* FROM atributo a WHERE a.nombre = ? AND a.EntidadElementoid = ?", resultClass = Atributo.class),
	@NamedNativeQuery(name = "Atributo.findByNameAndIdAndEntidad", query = "SELECT a.* FROM atributo a WHERE a.nombre = ? AND a.id != ? AND a.EntidadElementoid = ?", resultClass = Atributo.class),
	})

@Entity
@Table(name = "atributo", uniqueConstraints = @UniqueConstraint(columnNames = {
		"nombre", "EntidadElementoid" }))
@JsonIgnoreProperties(value = { "unidadTamanio", "entidad", "tipoDato", "hibernateLazyInitializer" })
public class Atributo implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "obligatorio")
	private Boolean obligatorio;
	
	@Column(name = "longitud")
	private Integer longitud;
	
	@Column(name = "formatoArchivo")
	private String formatoArchivo;
	
	@Column(name = "tamanioArchivo")
	private Float tamanioArchivo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UnidadTamanioid", referencedColumnName="id")
	private UnidadTamanio unidadTamanio;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EntidadElementoid", referencedColumnName = "Elementoid")
	private Entidad entidad;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TipoDatoid", referencedColumnName="id")
	private TipoDato tipoDato;
	
	@Column(name = "otroTipoDato")
	private String otroTipoDato;

	public Atributo() {
	}
	public Atributo(String nombre, Entidad entidad, String descripcion,
			Boolean obligatorio) {
		this.nombre = nombre;
		this.entidad = entidad;
		this.descripcion = descripcion;
		this.obligatorio = obligatorio;
	}
	public Atributo(String nombre, Entidad entidad, String descripcion,
			Boolean obligatorio, int longitud) {
		this.nombre = nombre;
		this.entidad = entidad;
		this.descripcion = descripcion;
		this.obligatorio = obligatorio;
		this.longitud = longitud;
	}
	
	public Atributo(String nombre, Entidad entidad, String descripcion,
			Boolean obligatorio, int longitud, int id) {
		this.nombre = nombre;
		this.entidad = entidad;
		this.descripcion = descripcion;
		this.obligatorio = obligatorio;
		this.longitud = longitud;
		this.id = id;
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

	public Boolean isObligatorio() {
		return this.obligatorio;
	}

	public void setObligatorio(Boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	public Integer getLongitud() {
		return this.longitud;
	}

	public void setLongitud(Integer longitud) {
		this.longitud = longitud;
	}
	
	public String getFormatoArchivo() {
		return this.formatoArchivo;
	}

	public void setFormatoArchivo(String formatoArchivo) {
		this.formatoArchivo = formatoArchivo;
	}

	public Float getTamanioArchivo() {
		return tamanioArchivo;
	}
	
	public void setTamanioArchivo(Float tamanioArchivo) {
		this.tamanioArchivo = tamanioArchivo;
	}
	
	public UnidadTamanio getUnidadTamanio() {
		return unidadTamanio;
	}
	
	public void setUnidadTamanio(UnidadTamanio unidadTamanio) {
		this.unidadTamanio = unidadTamanio;
	}
	
	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}
	
	public TipoDato getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(TipoDato tipoDato) {
		this.tipoDato = tipoDato;
	}
	
	public String getOtroTipoDato() {
		return otroTipoDato;
	}
	
	public void setOtroTipoDato(String otroTipoDato) {
		this.otroTipoDato = otroTipoDato;
	}
	
}
