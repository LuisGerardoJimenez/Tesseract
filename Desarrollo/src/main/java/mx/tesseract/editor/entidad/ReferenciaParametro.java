package mx.tesseract.editor.entidad;

/*
 * Luis Gerardo Jiménez
 */

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//import mx.tesseract.generadorPruebas.model.Query;
//import mx.tesseract.generadorPruebas.model.ValorMensajeParametro;
//import mx.tesseract.generadorPruebas.model.ValorMensajeParametroTrayectoria;


@Entity
@Table(name = "referenciaparametro")
public class ReferenciaParametro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TipoParametroid", referencedColumnName="id")
	private TipoParametro tipoParametro;
	
	// Entidad que hizo la referencia:
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PostPrecondicionid", referencedColumnName="id")
	private PostPrecondicion postPrecondicion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Pasoid", referencedColumnName="id")
	private Paso paso;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Extensionid", referencedColumnName="id")
	private Extension extension;
	
	// Entidad a la que se hizo referencia:
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PasoidDestino", referencedColumnName="id")
	private Paso pasoDestino;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Trayectoriaid", referencedColumnName="id")
	private Trayectoria trayectoria;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ElementoidDestino", referencedColumnName="id")
	private Elemento elementoDestino;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AccionidDestino", referencedColumnName="id")
	private Accion accionDestino;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Atributoid", referencedColumnName="id")
	private Atributo atributo;

	public ReferenciaParametro() {
	}

	public ReferenciaParametro(TipoParametro tipoParametro) {
		this.tipoParametro = tipoParametro;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public PostPrecondicion getPostPrecondicion() {
		return postPrecondicion;
	}

	public void setPostPrecondicion(PostPrecondicion postPrecondicion) {
		this.postPrecondicion = postPrecondicion;
	}

	public Paso getPaso() {
		return paso;
	}

	public void setPaso(Paso paso) {
		this.paso = paso;
	}

	public Extension getExtension() {
		return extension;
	}

	public void setExtension(Extension extension) {
		this.extension = extension;
	}
	
	public Paso getPasoDestino() {
		return pasoDestino;
	}

	public void setPasoDestino(Paso pasoDestino) {
		this.pasoDestino = pasoDestino;
	}
	
	public Elemento getElementoDestino() {
		return elementoDestino;
	}

	public void setElementoDestino(Elemento elementoDestino) {
		this.elementoDestino = elementoDestino;
	}

	public Accion getAccionDestino() {
		return accionDestino;
	}

	public void setAccionDestino(Accion accionDestino) {
		this.accionDestino = accionDestino;
	}
	
	public Atributo getAtributo() {
		return atributo;
	}

	public void setAtributo(Atributo atributo) {
		this.atributo = atributo;
	}

	public TipoParametro getTipoParametro() {
		return tipoParametro;
	}

	public void setTipoParametro(TipoParametro tipoParametro) {
		this.tipoParametro = tipoParametro;
	}

	public Trayectoria getTrayectoria() {
		return trayectoria;
	}

	public void setTrayectoria(Trayectoria trayectoria) {
		this.trayectoria = trayectoria;
	}

}
