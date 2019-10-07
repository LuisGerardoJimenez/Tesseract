package mx.tesseract.editor.entidad;

/*
* Luis Gerardo Jiménez
*/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import mx.tesseract.util.GenericInterface;

@Entity
@Table(name = "revision")
public class Revision implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "observaciones")
	private String observaciones;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CasoUsoElementoid", referencedColumnName = "Elementoid")
	private CasoUso casoUso;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Seccionid", referencedColumnName = "id")
	private Seccion seccion;
	
	@Column(name = "revisado")
	private Boolean revisado;

	public Revision() {
	}

	public Revision(String observaciones, CasoUso casoUso, Seccion seccion) {
		this.observaciones = observaciones;
		this.casoUso = casoUso;
		this.seccion = seccion;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	public CasoUso getCasoUso() {
		return casoUso;
	}

	public void setCasoUso(CasoUso casoUso) {
		this.casoUso = casoUso;
	}

	public Seccion getSeccion() {
		return seccion;
	}

	public void setSeccion(Seccion seccion) {
		this.seccion = seccion;
	}

	public Boolean isRevisado() {
		return revisado;
	}

	public void setRevisado(Boolean revisado) {
		this.revisado = revisado;
	}

}
