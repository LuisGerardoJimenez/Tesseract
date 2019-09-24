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
import javax.persistence.UniqueConstraint;

import mx.tesseract.util.GenericInterface;

@Entity
@Table(name = "salida", uniqueConstraints = @UniqueConstraint(columnNames = {
		"CasoUsoElementoid", "MensajeElementoid", "TerminoGlosarioElementoid",
		"Atributoid" }))
public class Salida implements Serializable, GenericInterface {

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
    @JoinColumn(name = "MensajeElementoid", referencedColumnName = "Elementoid")
	private Mensaje mensaje;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Atributoid", referencedColumnName = "id")
	private Atributo atributo;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TerminoGlosarioElementoid", referencedColumnName = "Elementoid")
	private TerminoGlosario terminoGlosario;

	public Salida() {
	}

	public Salida(TipoParametro tipoParametro, CasoUso casoUso) {
		this.tipoParametro = tipoParametro;
		this.casoUso = casoUso;
	}

	public Salida(TipoParametro tipoParametro, CasoUso casoUso,
			Mensaje mensaje, TerminoGlosario terminoGlosario,
			Atributo atributo) {
		this.tipoParametro = tipoParametro;
		this.casoUso = casoUso;
		this.mensaje = mensaje;
		this.terminoGlosario = terminoGlosario;
		this.atributo = atributo;
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
	
	public Mensaje getMensaje() {
		return mensaje;
	}

	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
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

}
