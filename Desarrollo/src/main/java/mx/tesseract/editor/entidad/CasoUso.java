package mx.tesseract.editor.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;

import mx.tesseract.util.ElementoInterface;
import mx.tesseract.util.GenericInterface;

@NamedNativeQueries({
	@NamedNativeQuery(name = "CasoUso.findElementoHasCasoUsoAsociado", query = "SELECT c FROM casouso c JOIN elemento e", resultClass = CasoUso.class)
	})
@NamedQueries({
	@NamedQuery(name = "CasoUso.consultarCasosUsoByProyectoAndModulo", query = "SELECT e FROM Elemento e JOIN e.proyecto p JOIN e.modulo m WHERE p.id = :idProyecto AND e.clave = :clave AND m.id = :idModulo "),
	@NamedQuery(name = "CasoUso.consultarCasosUsoByProyectoAndModuloAndNombre", query = "SELECT e FROM Elemento e JOIN e.proyecto p JOIN e.modulo m WHERE p.id = :idProyecto AND e.clave = :clave AND m.id = :idModulo AND e.nombre = :nombre"),
	@NamedQuery(name = "CasoUso.consultarCasosUsoByProyectoAndModuloAndIdAndNombre", query = "SELECT e FROM Elemento e JOIN e.proyecto p JOIN e.modulo m WHERE p.id = :idProyecto AND e.clave = :clave AND m.id = :idModulo AND e.id != :id AND e.nombre = :nombre"),
	})
@Entity
@Table(name = "casouso")
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "Elementoid", referencedColumnName = "id")
@DiscriminatorValue("CU")
@JsonIgnoreProperties(value = { "actores", "reglas", "postprecondiciones", "entradas", "salidas", "trayectorias", "extendidoDe", "extiende", "incluidoEn", "incluye", "revisiones", "hibernateLazyInitializer" })
public class CasoUso extends Elemento implements Serializable, GenericInterface, ElementoInterface {

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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "casoUso", orphanRemoval = true)
	private List<Salida> salidas = new ArrayList<Salida>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "casoUso", orphanRemoval = true)
	@OrderBy("id")
	private List<Entrada> entradas = new ArrayList<Entrada>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "casoUso", orphanRemoval = true)
	@OrderBy("clave")
	private List<Trayectoria> trayectorias = new ArrayList<Trayectoria>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "casoUsoDestino")
	private List<Inclusion> incluidoEn = new ArrayList<Inclusion>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "casoUsoOrigen")
	private List<Inclusion> incluye = new ArrayList<Inclusion>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "casoUsoOrigen", orphanRemoval = true)
	private List<Extension> Extiende = new ArrayList<Extension>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "casoUsoDestino", orphanRemoval = true)
	private List<Extension> ExtendidoDe = new ArrayList<Extension>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "casoUso", orphanRemoval = true)
	private List<Revision> revisiones = new ArrayList<Revision>();
	
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

	public List<Salida> getSalidas() {
		return salidas;
	}

	public void setSalidas(List<Salida> salidas) {
		this.salidas = salidas;
	}

	public List<Entrada> getEntradas() {
		return entradas;
	}

	public void setEntradas(List<Entrada> entradas) {
		this.entradas = entradas;
	}

	public List<Trayectoria> getTrayectorias() {
		return trayectorias;
	}

	public void setTrayectorias(List<Trayectoria> trayectorias) {
		this.trayectorias = trayectorias;
	}

	public List<Inclusion> getIncluidoEn() {
		return incluidoEn;
	}

	public void setIncluidoEn(List<Inclusion> incluidoEn) {
		this.incluidoEn = incluidoEn;
	}

	public List<Inclusion> getIncluye() {
		return incluye;
	}

	public void setIncluye(List<Inclusion> incluye) {
		this.incluye = incluye;
	}

	public List<Extension> getExtiende() {
		return Extiende;
	}

	public void setExtiende(List<Extension> extiende) {
		Extiende = extiende;
	}

	public List<Extension> getExtendidoDe() {
		return ExtendidoDe;
	}

	public void setExtendidoDe(List<Extension> extendidoDe) {
		ExtendidoDe = extendidoDe;
	}

	public List<Revision> getRevisiones() {
		return revisiones;
	}

	public void setRevisiones(List<Revision> revisiones) {
		this.revisiones = revisiones;
	}

}
