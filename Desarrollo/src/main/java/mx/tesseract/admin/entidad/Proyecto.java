package mx.tesseract.admin.entidad;

/*
 * Luis Gerardo Jiménez
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.opensymphony.xwork2.validator.annotations.DoubleRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

import mx.tesseract.util.Constantes;
import mx.tesseract.util.GenericInterface;

@NamedNativeQueries({
	@NamedNativeQuery(name = "Proyecto.findByClave", query = "SELECT p.* FROM proyecto p WHERE p.clave = ?", resultClass = Proyecto.class),
	@NamedNativeQuery(name = "Proyecto.findByClaveAndId", query = "SELECT p.* FROM proyecto p WHERE p.clave = ? AND p.id != ?", resultClass = Proyecto.class),
	@NamedNativeQuery(name = "Proyecto.findByNombre", query = "SELECT p.* FROM proyecto p WHERE p.nombre = ?", resultClass = Proyecto.class),
	@NamedNativeQuery(name = "Proyecto.findByNombreAndId", query = "SELECT p.* FROM proyecto p WHERE p.nombre = ? AND p.id != ?", resultClass = Proyecto.class),
	@NamedNativeQuery(name = "Proyecto.findElementosByIdProyecto", query = "SELECT p.* FROM proyecto p INNER JOIN elemento e ON e.proyectoid = p.id WHERE p.id = ? LIMIT 1", resultClass = Proyecto.class),
	@NamedNativeQuery(name = "Proyecto.findByColaboradorCurp", query = "SELECT p.* FROM proyecto p INNER JOIN colaborador_proyecto cp ON cp.proyectoid = p.id WHERE cp.ColaboradorCURP = ?", resultClass = Proyecto.class)
	})
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
	@JoinColumn(name = "EstadoProyectoid", referencedColumnName = "id")
	private EstadoProyecto estadoProyecto;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto", orphanRemoval = true)
	private List<ColaboradorProyecto> proyecto_colaboradores = new ArrayList<>(0);
	
	@Transient
	private String colaboradorCurp;
	
	@Transient
	private Integer idEstadoProyecto;

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
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFANUMERICO_MAYUSCULAS_SIN_ESPACIOS, shortCircuit = true)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave.trim();
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'50', 'caracteres'})}", trim = true, maxLength = "50", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFANUMERICO, shortCircuit = true)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.trim();
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
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.trim();
	}
	
	@DoubleRangeFieldValidator(message = "%{getText('MSG6',{'12', 'digitos positivos'})}", minInclusive = "0.00", maxInclusive = "999999999.99", shortCircuit= true)
	public Double getPresupuesto() {
		return this.presupuesto;
	}

	public void setPresupuesto(Double presupuesto) {
		this.presupuesto = presupuesto;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'45', 'caracteres'})}", trim = true, maxLength = "45", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getContraparte() {
		return this.contraparte;
	}

	public void setContraparte(String contraparte) {
		this.contraparte = contraparte;
	}
	
	public EstadoProyecto getEstadoProyecto() {
		return estadoProyecto;
	}

	public void setEstadoProyecto(EstadoProyecto estadoProyecto) {
		this.estadoProyecto = estadoProyecto;
	}
	
	public List<ColaboradorProyecto> getProyecto_colaboradores() {
		return proyecto_colaboradores;
	}

	public void setProyecto_colaboradores(List<ColaboradorProyecto> proyecto_colaboradores) {
		this.proyecto_colaboradores = proyecto_colaboradores;
	}
	
	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX_STRING, shortCircuit = true)
	public String getColaboradorCurp() {
		return colaboradorCurp;
	}
	
	public void setColaboradorCurp(String colaboradorCurp) {
		this.colaboradorCurp = colaboradorCurp.trim();
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX, shortCircuit = true)
	public Integer getIdEstadoProyecto() {
		return idEstadoProyecto;
	}

	public void setIdEstadoProyecto(Integer idEstadoProyecto) {
		this.idEstadoProyecto = idEstadoProyecto;
	}

}
