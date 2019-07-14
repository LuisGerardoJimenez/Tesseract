package mx.tesseract.admin.entidad;

/*
 * Luis Gerardo Jiménez
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import mx.tesseract.util.GenericInterface;

@Entity
@Table(name = "rol")
public class Rol implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nombre")
	private String nombre;

	public Rol() {
	}

	public Rol(String nombre) {
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
