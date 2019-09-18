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

@Entity
@Table(name = "casouso_actor",uniqueConstraints = @UniqueConstraint(columnNames = {
		"CasoUsoElementoid", "ActorElementoid" }))
public class CasoUsoActor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CasoUsoElementoid", referencedColumnName ="Elementoid")
	private CasoUso casouso;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ActorElementoid", referencedColumnName ="Elementoid")
	private Actor actor;

	public CasoUsoActor() {
	}

	public CasoUsoActor(CasoUso casouso, Actor actor) {
		this.casouso = casouso;
		this.actor = actor;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public CasoUso getCasouso() {
		return casouso;
	}

	public void setCasouso(CasoUso casouso) {
		this.casouso = casouso;
	}
	
	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

}
