package mx.tesseract.editor.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.ElementoInterface;
import mx.tesseract.util.GenericInterface;

@NamedNativeQueries({
	@NamedNativeQuery(name = "ReglaNegocio.consultarReglaNegocioByProyecto", query = "SELECT e.* FROM elemento e WHERE e.Proyectoid = ? AND e.clave = ?")
	})

@Entity
@Table(name = "reglanegocio")
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "Elementoid", referencedColumnName = "id")
@DiscriminatorValue("RN")
public class ReglaNegocio extends Elemento implements Serializable, GenericInterface, ElementoInterface {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "redaccion")
	private String redaccion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TipoReglaNegocioid", referencedColumnName="id")
	private TipoReglaNegocio tiporeglanegocio;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Atributoid_unicidad", referencedColumnName="id")
	private Atributo atributo_unicidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Atributoid_fechaI", referencedColumnName="id")
	private Atributo atributo_fechaI;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Atributoid_fechaT", referencedColumnName="id")
	private Atributo atributo_fechaT;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TipoComparacionid", referencedColumnName="id")
	private TipoComparacion tipocomparacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Atributoid_comp1", referencedColumnName="id")
	private Atributo atributo_comp1;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Atributoid_comp2", referencedColumnName="id")
	private Atributo atributo_comp2;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Operadorid", referencedColumnName="id")
	private Operador operador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Atributoid_expReg", referencedColumnName="id")
	private Atributo atributo_exp_reg;
	
	@Column(name = "expresionRegular")
	private String expresionRegular;
	
	public ReglaNegocio(){
	}
	
	public ReglaNegocio(String clave, String numero, String nombre,
			Proyecto proyecto, String descripcion, EstadoElemento estadoElemento, Operador operador, TipoComparacion tipocomparacion, TipoReglaNegocio tiporeglanegocio, 
			Atributo atributo_unicidad, Atributo atributo_fechaI, Atributo atributo_fechaT, Atributo atributo_comp1, Atributo atributo_comp2) {
		super(clave, numero, nombre, proyecto, descripcion, estadoElemento);
		this.operador = operador;
		this.tipocomparacion = tipocomparacion;
		this.tiporeglanegocio = tiporeglanegocio;
		this.atributo_unicidad = atributo_unicidad;
		this.atributo_fechaI = atributo_fechaI;
		this.atributo_fechaT = atributo_fechaT;
		this.atributo_comp1 = atributo_comp1;
		this.atributo_comp2 = atributo_comp2;
	}
	
	public String getRedaccion() {
		return redaccion;
	}

	public void setRedaccion(String redaccion) {
		this.redaccion = redaccion;
	}

	public TipoReglaNegocio getTiporeglanegocio() {
		return tiporeglanegocio;
	}

	public void setTiporeglanegocio(TipoReglaNegocio tiporeglanegocio) {
		this.tiporeglanegocio = tiporeglanegocio;
	}

	public Atributo getAtributo_unicidad() {
		return atributo_unicidad;
	}

	public void setAtributo_unicidad(Atributo atributo_unicidad) {
		this.atributo_unicidad = atributo_unicidad;
	}

	public Atributo getAtributo_fechaI() {
		return atributo_fechaI;
	}

	public void setAtributo_fechaI(Atributo atributo_fechaI) {
		this.atributo_fechaI = atributo_fechaI;
	}

	public Atributo getAtributo_fechaT() {
		return atributo_fechaT;
	}

	public void setAtributo_fechaT(Atributo atributo_fechaT) {
		this.atributo_fechaT = atributo_fechaT;
	}

	public TipoComparacion getTipocomparacion() {
		return tipocomparacion;
	}

	public void setTipocomparacion(TipoComparacion tipocomparacion) {
		this.tipocomparacion = tipocomparacion;
	}

	public Atributo getAtributo_comp1() {
		return atributo_comp1;
	}

	public void setAtributo_comp1(Atributo atributo_comp1) {
		this.atributo_comp1 = atributo_comp1;
	}

	public Atributo getAtributo_comp2() {
		return atributo_comp2;
	}

	public void setAtributo_comp2(Atributo atributo_comp2) {
		this.atributo_comp2 = atributo_comp2;
	}

	public Operador getOperador() {
		return operador;
	}

	public void setOperador(Operador operador) {
		this.operador = operador;
	}

	public Atributo getAtributo_exp_reg() {
		return atributo_exp_reg;
	}

	public void setAtributo_exp_reg(Atributo atributo_exp_reg) {
		this.atributo_exp_reg = atributo_exp_reg;
	}

	public String getExpresionRegular() {
		return expresionRegular;
	}

	public void setExpresionRegular(String expresionRegular) {
		this.expresionRegular = expresionRegular;
	}
	
}
