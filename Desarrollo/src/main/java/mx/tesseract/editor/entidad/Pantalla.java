package mx.tesseract.editor.entidad;

import java.io.Serializable;

/*
 * Luis Gerardo Jiménez
 */

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.ElementoInterface;
import mx.tesseract.util.GenericInterface;
//import mx.tesseract.generadorPruebas.model.ValorPantallaTrayectoria;

@Entity
@Table(name = "pantalla")
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "Elementoid", referencedColumnName = "id")
@DiscriminatorValue("IU")
public class Pantalla extends Elemento implements Serializable, GenericInterface, ElementoInterface {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "imagen")
	private byte[] imagen;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Moduloid", referencedColumnName = "id")	
	private Modulo modulo;
	
//	private Set<Accion> acciones = new HashSet<Accion>(0);
	
	@Column(name = "patron")
	private String patron;
	
//	private Set<ValorPantallaTrayectoria> valoresPantallaTrayectoria;

	public Pantalla() {
	}

//	public Pantalla(String clave, String numero, String nombre,
//			Proyecto proyecto, String descripcion, EstadoElemento estadoElemento, Modulo modulo) {
//		super(clave, numero, nombre, proyecto, descripcion, estadoElemento);
//		this.modulo = modulo;
//	}
//
//	public Pantalla(String clave, String numero, String nombre,
//			Proyecto proyecto, String descripcion, EstadoElemento estadoElemento,byte[] imagen, Modulo modulo) {
//		super(clave, numero, nombre, proyecto, descripcion, estadoElemento);
//		this.imagen = imagen;
//		this.modulo = modulo;
//	}

	public byte[] getImagen() {
		return this.imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public Modulo getModulo() {
		return this.modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}
	
//	@OneToMany(fetch = FetchType.EAGER, mappedBy = "pantalla", cascade = CascadeType.ALL, orphanRemoval = true)
//	public Set<Accion> getAcciones() {
//		return acciones;
//	}
//
//	public void setAcciones(Set<Accion> acciones) {
//		this.acciones = acciones;
//	}

	public String getPatron() {
		return patron;
	}

	public void setPatron(String patron) {
		this.patron = patron;
	}
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pantalla")
//	public Set<ValorPantallaTrayectoria> getValoresPantallaTrayectoria() {
//		return valoresPantallaTrayectoria;
//	}
//
//
//	public void setValoresPantallaTrayectoria(
//			Set<ValorPantallaTrayectoria> valoresPantallaTrayectoria) {
//		this.valoresPantallaTrayectoria = valoresPantallaTrayectoria;
//	}
	

	

}
