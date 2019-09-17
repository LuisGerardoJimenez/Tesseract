package mx.tesseract.editor.entidad;

/*
 * Luis Gerardo Jiménez
 */
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import mx.tesseract.util.GenericInterface;

//import mx.tesseract.generadorPruebas.model.ValorAccionTrayectoria;

@NamedNativeQueries({
	@NamedNativeQuery(name = "Accion.findByPantalla", query = "SELECT a.* FROM accion a WHERE a.PantallaElementoid = ?", resultClass = Accion.class),
	@NamedNativeQuery(name = "Accion.findByNameAndPantalla", query = "SELECT a.* FROM accion a WHERE a.nombre = ? AND a.PantallaElementoid = ?", resultClass = Accion.class),
	@NamedNativeQuery(name = "Accion.findByNameAndIdAndPantalla", query = "SELECT a.* FROM accion a WHERE a.nombre = ? AND a.id != ? AND a.PantallaElementoid = ?", resultClass = Accion.class),
	})

@Entity
@Table(name = "Accion")
public class Accion implements java.io.Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PantallaElementoid", referencedColumnName = "Elementoid")
	private Pantalla pantalla;
	
	@Column(name = "nombre")
	private String nombre;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TipoAccionid", referencedColumnName = "id")
	private TipoAccion tipoAccion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PantallaElementoidDestino", referencedColumnName = "Elementoid")
	private Pantalla pantallaDestino;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "imagen")
	private byte[] imagen;
	
	@Column(name = "urlDestino")
	private String urlDestino;
	
	@Column(name = "metodo")
	private String metodo;
	
//	private Set<ValorAccionTrayectoria> valoresAccionTrayectoria;

	public Accion() {
	}

	public Accion(Pantalla pantalla, String nombre,
			TipoAccion tipoAccion) {
		this.pantalla = pantalla;
		this.nombre = nombre;
		this.tipoAccion = tipoAccion;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pantalla getPantalla() {
		return pantalla;
	}

	public void setPantalla(Pantalla pantalla) {
		this.pantalla = pantalla;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public TipoAccion getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(TipoAccion tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public Pantalla getPantallaDestino() {
		return pantallaDestino;
	}

	public void setPantallaDestino(Pantalla pantallaDestino) {
		this.pantallaDestino = pantallaDestino;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte[] getImagen() {
		return this.imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getUrlDestino() {
		return urlDestino;
	}

	public void setUrlDestino(String urlDestino) {
		this.urlDestino = urlDestino;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "accion") 
//	public Set<ValorAccionTrayectoria> getValoresAccionTrayectoria() {
//		return valoresAccionTrayectoria;
//	}
//
//	public void setValoresAccionTrayectoria(Set<ValorAccionTrayectoria> valoresAccionTrayectoria) {
//		this.valoresAccionTrayectoria = valoresAccionTrayectoria;
//	}
	
	
	
}
