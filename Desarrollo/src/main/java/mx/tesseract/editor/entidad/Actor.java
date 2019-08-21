package mx.tesseract.editor.entidad;

import java.io.Serializable;

/*
 * Luis Gerardo Jiménez
 */
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.ElementoInterface;
import mx.tesseract.util.GenericInterface;


@NamedNativeQueries({
	@NamedNativeQuery(name = "Actor.consultarActorByProyecto", query = "SELECT e.* FROM elemento e WHERE e.Proyectoid = ? AND e.clave = ?")
	})

@Entity
@Table(name = "actor")
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "Elementoid", referencedColumnName = "id")
@DiscriminatorValue("ACT")
public class Actor extends Elemento implements Serializable, GenericInterface, ElementoInterface {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "otraCardinalidad")
	private String otraCardinalidad;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Cardinalidadid", referencedColumnName = "id")
	private Cardinalidad cardinalidad;

	public Actor() {
	}

	public Actor(String clave, String numero, String nombre,
			Proyecto proyecto, String descripcion, EstadoElemento estadoElemento, Cardinalidad cardinalidad) {
		super(clave, numero, nombre, proyecto, descripcion, estadoElemento);
		this.cardinalidad = cardinalidad;
	}

	public Actor(String clave, String numero, String nombre,
			Proyecto proyecto, String descripcion, EstadoElemento estadoElemento, String otraCardinalidad, Cardinalidad cardinalidad) {
		super(clave, numero, nombre, proyecto, descripcion, estadoElemento);
		this.otraCardinalidad = otraCardinalidad;
		this.cardinalidad = cardinalidad;
	}
	
	public String getOtraCardinalidad() {
		return this.otraCardinalidad;
	}

	public void setOtraCardinalidad(String otraCardinalidad) {
		this.otraCardinalidad = otraCardinalidad;
	}

	public Cardinalidad getCardinalidad() {
		return this.cardinalidad;
	}

	public void setCardinalidad(Cardinalidad cardinalidad) {
		this.cardinalidad = cardinalidad;
	}

}
