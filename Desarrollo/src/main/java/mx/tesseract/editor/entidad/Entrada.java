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
@Table(name = "entrada", uniqueConstraints = @UniqueConstraint(columnNames = {
		"CasoUsoElementoid", "Atributoid", "TerminoGlosarioElementoid" }))
public class Entrada implements Serializable, Comparable<Entrada>, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoParametroid", referencedColumnName = "id")
	private TipoParametro tipoParametro;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CasoUsoElementoid", referencedColumnName = "Elementoid")
	private CasoUso casoUso;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Atributoid", referencedColumnName = "id")
	private Atributo atributo;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TerminoGlosarioElementoid", referencedColumnName = "Elementoid")
	private TerminoGlosario terminoGlosario;
	
	@Column(name = "nombreHTML")
	private String nombreHTML;

	public Entrada() {
	}

	public Entrada(TipoParametro tipoParametro, CasoUso casoUso) {
		this.tipoParametro = tipoParametro;
		this.casoUso = casoUso;
	}

	public Entrada(int numeroToken, TipoParametro tipoParametro, CasoUso casoUso,
			Atributo atributo, TerminoGlosario terminoGlosario) {
		this.tipoParametro = tipoParametro;
		this.casoUso = casoUso;
		this.atributo = atributo;
		this.terminoGlosario = terminoGlosario;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public TipoParametro getTipoParametro() {
		return tipoParametro;
	}

	public void setTipoParametro(TipoParametro tipoParametro) {
		this.tipoParametro = tipoParametro;
	}
	
	public CasoUso getCasoUso() {
		return casoUso;
	}

	public void setCasoUso(CasoUso casoUso) {
		this.casoUso = casoUso;
	}
	
	public Atributo getAtributo() {
		return atributo;
	}

	public void setAtributo(Atributo atributo) {
		this.atributo = atributo;
	}
	
	public TerminoGlosario getTerminoGlosario() {
		return terminoGlosario;
	}

	public void setTerminoGlosario(TerminoGlosario terminoGlosario) {
		this.terminoGlosario = terminoGlosario;
	}
	
	public String getNombreHTML() {
		return nombreHTML;
	}

	public void setNombreHTML(String nombreHTML) {
		this.nombreHTML = nombreHTML;
	}

	public int compareTo(Entrada o) {
		Integer.compare(this.getId(), o.getId());
		return 0;
	}

}
