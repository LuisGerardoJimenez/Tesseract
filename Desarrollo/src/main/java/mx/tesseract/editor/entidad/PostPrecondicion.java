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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import mx.tesseract.util.GenericInterface;

@NamedNativeQueries({
	@NamedNativeQuery(name = "PostPrecondicion.findByCasoUso", query = "SELECT p.* FROM postprecondicion p WHERE p.CasoUsoElementoid = ?", resultClass = PostPrecondicion.class)
	})

@Entity
@Table(name = "postprecondicion")
public class PostPrecondicion implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "redaccion")
	private String redaccion;
	
	@Column(name = "precondicion")
	private Boolean precondicion;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CasoUsoElementoid", referencedColumnName = "Elementoid")
	private CasoUso casoUso;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "postPrecondicion")
	private List<ReferenciaParametro> referencias = new ArrayList<ReferenciaParametro>();

	public PostPrecondicion() {
	}

	public PostPrecondicion(String redaccion,
			Boolean precondicion, CasoUso casoUso) {
		this.redaccion = redaccion;
		this.precondicion = precondicion;
		this.casoUso = casoUso;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRedaccion() {
		return this.redaccion;
	}

	public void setRedaccion(String redaccion) {
		this.redaccion = redaccion;
	}
	
	public Boolean isPrecondicion() {
		return precondicion;
	}

	public void setPrecondicion(Boolean precondicion) {
		this.precondicion = precondicion;
	}
	
	public CasoUso getCasoUso() {
		return casoUso;
	}

	public void setCasoUso(CasoUso casoUso) {
		this.casoUso = casoUso;
	}
	
	public List<ReferenciaParametro> getReferencias() {
		return referencias;
	}

	public void setReferencias(List<ReferenciaParametro> referencias) {
		this.referencias = referencias;
	}

}
