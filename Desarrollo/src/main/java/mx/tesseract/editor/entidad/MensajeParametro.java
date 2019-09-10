package mx.tesseract.editor.entidad;

/*
 * Diego Efrain López Orozco
 */
import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

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
@Table(name = "Mensaje_Parametro")
public class MensajeParametro implements java.io.Serializable, GenericInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Mensaje mensaje;
	private Parametro parametro;
	//private Set<ValorMensajeParametro> valores = new HashSet<ValorMensajeParametro>(0);

	public MensajeParametro() {
	}

	public MensajeParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MensajeElementoid", referencedColumnName ="Elementoid", nullable = false)
	public Mensaje getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(Mensaje mensaje) { 
		this.mensaje = mensaje;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Parametroid", referencedColumnName ="id", nullable = false)
	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "mensajeParametro", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<ValorMensajeParametro> getValores() {
		return valores;
	}

	public void setValores(Set<ValorMensajeParametro> valores) {
		this.valores = valores;
	}*/

}
