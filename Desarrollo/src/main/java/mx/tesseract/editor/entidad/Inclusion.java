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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import mx.tesseract.util.GenericInterface;


@Entity
@Table(name = "inclusion", uniqueConstraints = @UniqueConstraint(columnNames = {
		"CasoUsoElementoid_origen", "CasoUsoElementoid_destino"}))
public class Inclusion implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CasoUsoElementoid_origen", referencedColumnName = "Elementoid")
	private CasoUso casoUsoOrigen;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CasoUsoElementoid_destino", referencedColumnName = "Elementoid")
	private CasoUso casoUsoDestino;

	public Inclusion() {
	}

	public Inclusion(CasoUso casoUsoOrigen, CasoUso casoUsoDestino) {
		this.casoUsoOrigen = casoUsoOrigen;
		this.casoUsoDestino = casoUsoDestino;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CasoUso getCasoUsoOrigen() {
		return casoUsoOrigen;
	}

	public void setCasoUsoOrigen(CasoUso casoUsoOrigen) {
		this.casoUsoOrigen = casoUsoOrigen;
	}
	
	public CasoUso getCasoUsoDestino() {
		return casoUsoDestino;
	}

	public void setCasoUsoDestino(CasoUso casoUsoDestino) {
		this.casoUsoDestino = casoUsoDestino;
	}

}
