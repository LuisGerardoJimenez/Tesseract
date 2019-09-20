package mx.tesseract.editor.entidad;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * Luis Gerardo Jiménez
 */

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "paso", uniqueConstraints = @UniqueConstraint(columnNames = {
		"numero", "Trayectoriaid" }))
public class Paso implements Serializable, Comparable<Paso> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "numero")
	private Integer numero;
	
	@Column(name = "realizaActor")
	private Boolean realizaActor;
	
	@Column(name = "redaccion")
	private String redaccion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Trayectoriaid", referencedColumnName ="id")
	private Trayectoria trayectoria;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Verboid",referencedColumnName="id")
	private Verbo verbo;
	
	@Column(name = "otroVerbo")
	private String otroVerbo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paso", orphanRemoval = true)
	private List<ReferenciaParametro> referencias = new ArrayList<ReferenciaParametro>();


	public Paso() {
	}

	public Paso(Integer numero, Boolean realizaActor, String redaccion,
			Trayectoria trayectoria, Verbo verbo) {
		this.numero = numero;
		this.realizaActor = realizaActor;
		this.redaccion = redaccion;
		this.trayectoria = trayectoria;
		this.verbo = verbo;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumero() {
		return this.numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Boolean isRealizaActor() {
		return this.realizaActor;
	}

	
	public void setRealizaActor(Boolean realizaActor) {
		this.realizaActor = realizaActor;
	}
	
	public String getRedaccion() {
		return this.redaccion;
	}

	public void setRedaccion(String redaccion) {
		this.redaccion = redaccion;
	}
	
	public Trayectoria getTrayectoria() {
		return trayectoria;
	}

	public void setTrayectoria(Trayectoria trayectoria) {
		this.trayectoria = trayectoria;
	}
	
	public List<ReferenciaParametro> getReferencias() {
		return referencias;
	}

	public void setReferencias(List<ReferenciaParametro> referencias) {
		this.referencias = referencias;
	}
	
	public Verbo getVerbo() {
		return verbo;
	}

	public void setVerbo(Verbo verbo) {
		this.verbo = verbo;
	}

	public String getOtroVerbo() {
		return otroVerbo;
	}

	public void setOtroVerbo(String otroVerbo) {
		this.otroVerbo = otroVerbo;
	}

	public int compareTo(Paso o) {
		return Integer.compare(this.numero, o.getNumero());
	}


}
