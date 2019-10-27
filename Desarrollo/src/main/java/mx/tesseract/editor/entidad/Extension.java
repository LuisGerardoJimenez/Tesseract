package mx.tesseract.editor.entidad;

/*
 * Luis Gerardo Jiménez
 */

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import mx.tesseract.util.GenericInterface;

@Entity
@Table(name = "extension", uniqueConstraints = @UniqueConstraint(columnNames = {
		"CasoUsoElementoid_origen", "CasoUsoElementoid_destino"}))
public class Extension implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "causa")
	private String causa;
	
	@Column(name = "region")
	private String region;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CasoUsoElementoid_origen", referencedColumnName = "Elementoid")
	private CasoUso casoUsoOrigen;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CasoUsoElementoid_destino", referencedColumnName = "Elementoid")
	private CasoUso casoUsoDestino;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "extension", orphanRemoval = true)
	private List<ReferenciaParametro> referencias = new ArrayList<>();


	public Extension() {
	}

	public Extension(String causa, String region, CasoUso casoUsoOrigen, CasoUso casoUsoDestino) {
		this.causa = causa;
		this.region = region;
		this.casoUsoOrigen = casoUsoOrigen;
		this.casoUsoDestino = casoUsoDestino;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCausa() {
		return this.causa;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
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

	public List<ReferenciaParametro> getReferencias() {
		return referencias;
	}

	public void setReferencias(List<ReferenciaParametro> referencias) {
		this.referencias = referencias;
	}

}
