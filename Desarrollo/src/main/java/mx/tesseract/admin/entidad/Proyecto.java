package mx.tesseract.admin.entidad;

import java.io.Serializable;

/*
 * Luis Gerardo Jiménez
 */

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.opensymphony.xwork2.validator.annotations.DoubleRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

import mx.tesseract.util.Constantes;
import mx.tesseract.util.GenericInterface;


@Entity
@Table(name = "proyecto", uniqueConstraints = {
		@UniqueConstraint(columnNames = "clave"),
		@UniqueConstraint(columnNames = "nombre") })
public class Proyecto implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "clave")
	private String clave;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fechaInicioProgramada")
	private Date fechaInicioProgramada;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fechaTerminoProgramada")
	private Date fechaTerminoProgramada;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fechaInicio")
	private Date fechaInicio;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fechaTermino")
	private Date fechaTermino;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "presupuesto")
	private Double presupuesto;
	
	@Column(name = "contraparte")
	private String contraparte;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EstadoProyectoid", referencedColumnName = "id", insertable = false, updatable = false)
	private EstadoProyecto estadoProyecto;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "proyecto")
	private Set<ColaboradorProyecto> proyecto_colaboradores = new HashSet<ColaboradorProyecto>(0);

	public Proyecto() {
	}

	public Proyecto(String clave, String nombre, Date fechaInicioProgramada,
			Date fechaTerminoProgramada, String descripcion,
			String contraparte, EstadoProyecto estadoProyecto) {
		this.clave = clave;
		this.nombre = nombre;
		this.fechaInicioProgramada = fechaInicioProgramada;
		this.fechaTerminoProgramada = fechaTerminoProgramada;
		this.descripcion = descripcion;
		this.contraparte = contraparte;
		this.estadoProyecto= estadoProyecto;
	}

	public Proyecto(String clave, String nombre, Date fechaInicioProgramada,
			Date fechaTerminoProgramada, Date fechaInicio, Date fechaTermino,
			String descripcion, Double presupuesto, String contraparte,
			EstadoProyecto estadoProyecto) {
		this.clave = clave;
		this.nombre = nombre;
		this.fechaInicioProgramada = fechaInicioProgramada;
		this.fechaTerminoProgramada = fechaTerminoProgramada;
		this.fechaInicio = fechaInicio;
		this.fechaTermino = fechaTermino;
		this.descripcion = descripcion;
		this.presupuesto = presupuesto;
		this.contraparte = contraparte;
		this.estadoProyecto = estadoProyecto;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'10', 'caracteres'})}", trim = true, maxLength = "10", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex = Constantes.REGEX_CAMPO_ALFANUMERICO_MAYUSCULAS_SIN_ESPACIOS, shortCircuit = true)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave.trim();
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'50', 'caracteres'})}", trim = true, maxLength = "50", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex = Constantes.REGEX_CAMPO_ALFANUMERICO, shortCircuit = true)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	public Date getFechaInicioProgramada() {
		return this.fechaInicioProgramada;
	}

	public void setFechaInicioProgramada(Date fechaInicioProgramada) {
		this.fechaInicioProgramada = fechaInicioProgramada;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	public Date getFechaTerminoProgramada() {
		return this.fechaTerminoProgramada;
	}

	public void setFechaTerminoProgramada(Date fechaTerminoProgramada) {
		this.fechaTerminoProgramada = fechaTerminoProgramada;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaTermino() {
		return this.fechaTermino;
	}

	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'999', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex = Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@DoubleRangeFieldValidator(message = "%{getText('MSG6',{'12', 'digitos'})}", minInclusive = "0.00", maxInclusive = "999999999.99", shortCircuit= true)
	public Double getPresupuesto() {
		return this.presupuesto;
	}

	public void setPresupuesto(Double presupuesto) {
		this.presupuesto = presupuesto;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'45', 'caracteres'})}", trim = true, maxLength = "45", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex = Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getContraparte() {
		return this.contraparte;
	}

	public void setContraparte(String contraparte) {
		this.contraparte = contraparte;
	}
	
	@VisitorFieldValidator
	public EstadoProyecto getEstadoProyecto() {
		return estadoProyecto;
	}

	public void setEstadoProyecto(EstadoProyecto estadoProyecto) {
		this.estadoProyecto = estadoProyecto;
	}
	
	public Set<ColaboradorProyecto> getProyecto_colaboradores() {
		return proyecto_colaboradores;
	}

	public void setProyecto_colaboradores(
			Set<ColaboradorProyecto> proyecto_colaboradores) {
		this.proyecto_colaboradores = proyecto_colaboradores;
	}


}
