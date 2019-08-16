package mx.tesseract.admin.entidad;

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
import javax.persistence.Table;

import mx.tesseract.util.GenericInterface;

@Entity
@Table(name = "colaborador_proyecto")
public class ColaboradorProyecto implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ColaboradorCURP")
	private Colaborador colaborador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Rolid")
	private Rol rol;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Proyectoid")
	private Proyecto proyecto;

	public ColaboradorProyecto() {
	}

	public ColaboradorProyecto(Colaborador colaborador, Rol rol, Proyecto proyecto) {
		this.colaborador = colaborador;
		this.rol = rol;
		this.proyecto = proyecto;

	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

}
