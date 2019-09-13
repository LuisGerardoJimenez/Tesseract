package mx.tesseract.editor.entidad;

/*
 * Diego Efrain López Orozco
 */
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import mx.tesseract.util.GenericInterface;

@NamedNativeQueries({
	@NamedNativeQuery(name = "MensajeParametro.findByIdParametroIdMensaje", query = "SELECT mp.* FROM mensaje_parametro mp where mp.MensajeElementoid = ? and mp.Parametroid=?", resultClass = MensajeParametro.class),
	@NamedNativeQuery(name = "MensajeParametro.findByIdParametro", query = "SELECT mp.* FROM mensaje_parametro mp where mp.Parametroid=?", resultClass = MensajeParametro.class)
	})
@Entity
@Table(name = "Mensaje_Parametro")
public class MensajeParametro implements java.io.Serializable, GenericInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MensajeElementoid")
	private Mensaje mensaje;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Parametroid")
	private Parametro parametro;
	//private Set<ValorMensajeParametro> valores = new HashSet<ValorMensajeParametro>(0);

	public MensajeParametro() {
	}

	public MensajeParametro(Parametro parametro) {
		this.parametro = parametro;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Mensaje getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(Mensaje mensaje) { 
		this.mensaje = mensaje;
	}
	
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
