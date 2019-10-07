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
@Table(name = "casouso_reglanegocio", uniqueConstraints = @UniqueConstraint(columnNames = {
		"CasoUsoElementoid", "ReglaNegocioElementoid"}))
public class CasoUsoReglaNegocio implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CasoUsoElementoid", referencedColumnName = "Elementoid")
	private CasoUso casoUso;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReglaNegocioElementoid", referencedColumnName = "Elementoid")
	private ReglaNegocio reglaNegocio;
	
	public CasoUsoReglaNegocio() {
	}

	public CasoUsoReglaNegocio(CasoUso casoUso, ReglaNegocio reglaNegocio) {
		this.casoUso = casoUso;
		this.reglaNegocio = reglaNegocio;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer  id) {
		this.id = id;
	}
	
	public CasoUso getCasoUso() {
		return casoUso;
	}

	public void setCasoUso(CasoUso casoUso) {
		this.casoUso = casoUso;
	}
	
	public ReglaNegocio getReglaNegocio() {
		return reglaNegocio;
	}

	public void setReglaNegocio(ReglaNegocio reglaNegocio) {
		this.reglaNegocio = reglaNegocio;
	}
	
}
