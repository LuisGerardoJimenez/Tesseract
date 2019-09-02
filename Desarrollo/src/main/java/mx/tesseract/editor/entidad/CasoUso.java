package mx.tesseract.editor.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;

import mx.tesseract.util.ElementoInterface;

@Entity
@Table(name = "casouso")
@PrimaryKeyJoinColumn(name = "Elementoid", referencedColumnName = "id")
@NamedNativeQueries({
	@NamedNativeQuery(name = "CasoUso.findElementoHasCasoUsoAsociado", query = "SELECT c FROM casouso c JOIN elemento e", resultClass = CasoUso.class)
	})
public class CasoUso extends Elemento implements java.io.Serializable, ElementoInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "redaccionActores", length = 999)
	private String redaccionActores;
	
	@Column(name = "redaccionEntradas", length = 999)
	private String redaccionEntradas;
	
	@Column(name = "redaccionSalidas", length = 999)
	private String redaccionSalidas;
	
	@Column(name = "redaccionReglasNegocio", length = 999)
	private String redaccionReglasNegocio;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Moduloid")
	private Modulo modulo;
	/*private boolean reporte;
	private Set<CasoUsoActor> actores = new HashSet<CasoUsoActor>(0);
	private Set<Salida> salidas = new HashSet<Salida>(0);
	private Set<Entrada> entradas = new HashSet<Entrada>(0);
	private Set<CasoUsoReglaNegocio> reglas = new HashSet<CasoUsoReglaNegocio>(0);
	private Set<PostPrecondicion> postprecondiciones = new HashSet<PostPrecondicion>(0);
	private Set<Trayectoria> trayectorias = new HashSet<Trayectoria>(0);
	private Set<Inclusion> incluidoEn = new HashSet<Inclusion>(0);
	private Set<Inclusion> incluye = new HashSet<Inclusion>(0);
	private Set<Extension> Extiende = new HashSet<Extension>(0);
	private Set<Extension> ExtendidoDe = new HashSet<Extension>(0);
	private Set<Revision> revisiones = new HashSet<Revision>(0);
	private ConfiguracionHttp configuracionHttp;
	private ConfiguracionBaseDatos configuracionBaseDatos;*/
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'1000', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	public String getRedaccionActores() {
		return redaccionActores;
	}
	
	public void setRedaccionActores(String redaccionActores) {
		this.redaccionActores = redaccionActores;
	}
	
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'1000', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	public String getRedaccionEntradas() {
		return redaccionEntradas;
	}
	public void setRedaccionEntradas(String redaccionEntradas) {
		this.redaccionEntradas = redaccionEntradas;
	}
	
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'1000', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	public String getRedaccionSalidas() {
		return redaccionSalidas;
	}
	
	public void setRedaccionSalidas(String redaccionSalidas) {
		this.redaccionSalidas = redaccionSalidas;
	}
	
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'1000', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	public String getRedaccionReglasNegocio() {
		return redaccionReglasNegocio;
	}
	
	public void setRedaccionReglasNegocio(String redaccionReglasNegocio) {
		this.redaccionReglasNegocio = redaccionReglasNegocio;
	}
	
	public Modulo getModulo() {
		return modulo;
	}
	
	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

}
