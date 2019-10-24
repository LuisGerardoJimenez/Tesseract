package mx.tesseract.editor.entidad;

/*
 * Luis Gerardo Jiménez
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.tesseract.util.GenericInterface;
@NamedQueries({
	@NamedQuery(name = "Verbo.findByName", query = "SELECT v FROM Verbo v WHERE v.nombre = :nombre"),
	})
@Entity
@Table(name = "verbo")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Verbo implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nombre")
	private String nombre;

	public Verbo() {
	}

	public Verbo(String nombre) {
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

}
