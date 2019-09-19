package mx.tesseract.editor.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;

import mx.tesseract.util.ElementoInterface;

@Entity
@Table(name = "casouso")
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "Elementoid", referencedColumnName = "id")
@DiscriminatorValue("CU")
@NamedNativeQueries({
	@NamedNativeQuery(name = "CasoUso.findElementoHasCasoUsoAsociado", query = "SELECT c FROM casouso c JOIN elemento e", resultClass = CasoUso.class)
	})
public class CasoUso extends Elemento implements Serializable, ElementoInterface {

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
	
	@Column(name = "reporte")
	private Boolean reporte;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "casouso", orphanRemoval = true)
	private List<CasoUsoActor> actores = new ArrayList<CasoUsoActor>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "casoUso", orphanRemoval = true)
	private List<CasoUsoReglaNegocio> reglas = new ArrayList<CasoUsoReglaNegocio>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "casoUso", orphanRemoval = true)
	private List<PostPrecondicion> postprecondiciones = new ArrayList<PostPrecondicion>();
	
//	private Set<Salida> salidas = new HashSet<Salida>(0);
//	private Set<Entrada> entradas = new HashSet<Entrada>(0);
//	private Set<Trayectoria> trayectorias = new HashSet<Trayectoria>(0);
//	private Set<Inclusion> incluidoEn = new HashSet<Inclusion>(0);
//	private Set<Inclusion> incluye = new HashSet<Inclusion>(0);
//	private Set<Extension> Extiende = new HashSet<Extension>(0);
//	private Set<Extension> ExtendidoDe = new HashSet<Extension>(0);
//	private Set<Revision> revisiones = new HashSet<Revision>(0);
//	private ConfiguracionHttp configuracionHttp;
//	private ConfiguracionBaseDatos configuracionBaseDatos;
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

	public Boolean getReporte() {
		return reporte;
	}

	public void setReporte(Boolean reporte) {
		this.reporte = reporte;
	}

	public List<CasoUsoActor> getActores() {
		return actores;
	}

	public void setActores(List<CasoUsoActor> actores) {
		this.actores = actores;
	}

	public List<CasoUsoReglaNegocio> getReglas() {
		return reglas;
	}

	public void setReglas(List<CasoUsoReglaNegocio> reglas) {
		this.reglas = reglas;
	}

	public List<PostPrecondicion> getPostprecondiciones() {
		return postprecondiciones;
	}

	public void setPostprecondiciones(List<PostPrecondicion> postprecondiciones) {
		this.postprecondiciones = postprecondiciones;
	}

}
