package mx.tesseract.editor.entidad;

/*
 * Luis Gerardo Jiménez
 */

import static javax.persistence.GenerationType.IDENTITY;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.GenericInterface;

@NamedNativeQueries({
	@NamedNativeQuery(name = "Elemento.consultarElementosGlosario", query = "SELECT e.* FROM elemento e INNER JOIN terminoglosario tg ON e.id = tg.Elementoid WHERE e.Proyectoid = ?", resultClass = Elemento.class),
	@NamedNativeQuery(name = "Elemento.consultarElementosGlosarioByNombre", query = "SELECT e.* FROM elemento e INNER JOIN terminoglosario tg ON e.id = tg.Elementoid WHERE e.nombre = ? AND e.Proyectoid = ?", resultClass = Elemento.class),
	@NamedNativeQuery(name = "Elemento.consultarElementosGlosarioByNombreAndId", query = "SELECT e.* FROM elemento e INNER JOIN terminoglosario tg ON e.id = tg.Elementoid WHERE e.nombre = ? AND e.Proyectoid = ? AND e.id != ?", resultClass = Elemento.class),
	@NamedNativeQuery(name = "Elemento.findNextNumberTerminoGlosario", query = "SELECT MAX(CAST(e.numero AS SIGNED)) FROM Elemento e INNER JOIN terminoglosario tg ON e.id = tg.Elementoid WHERE e.Proyectoid = ?", resultClass = Elemento.class),
	})

@Entity
@Table(name = "Elemento", catalog = "TESSERACT")
@Inheritance(strategy=InheritanceType.JOINED)

@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
		  include = JsonTypeInfo.As.PROPERTY,
		  property = "type")
//		@JsonSubTypes({
//		  @Type(value = Mensaje.class, name = "mensaje"),
//		  @Type(value = Pantalla.class, name = "pantalla"),
//		  @Type(value = Pantalla.class, name = "reglaNegocio")
//		})
public class Elemento implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "clave")
	private String clave;
	
	@Column(name = "numero")
	private String numero;
	
	@Column(name = "nombre")
	private String nombre;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Proyectoid")
	private Proyecto proyecto;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EstadoElementoid", referencedColumnName="id")
	private EstadoElemento estadoElemento;
	
	@Transient
	private String type;

	public Elemento() {
	}

	public Elemento(String clave, String numero, String nombre,
			Proyecto proyecto, String descripcion, EstadoElemento estadoElemento) {
		this.clave = clave;
		this.numero = numero;
		this.nombre = nombre;
		this.proyecto = proyecto;
		this.descripcion = descripcion;
		this.estadoElemento = estadoElemento;
	}

//	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
//	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5',{'un', 'número'})}", regex = "[0-9]*", shortCircuit = true)
//	@IntRangeFieldValidator(message = "%{getText('MSG14',{'El', 'identificador', '0', '2147483647'})}", shortCircuit = true, min = "0", max = "2147483647")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	//@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
//	@StringLengthFieldValidator(message = "%{getText('MSG6',{'10', 'caracteres'})}", trim = true, maxLength = "10", shortCircuit= true)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	//@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
//	@StringLengthFieldValidator(message = "%{getText('MSG6',{'20', 'números'})}", trim = true, maxLength = "20", shortCircuit= true)
	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

//	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
//	@StringLengthFieldValidator(message = "%{getText('MSG6',{'200', 'caracteres'})}", trim = true, maxLength = "200", shortCircuit= true)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

//	@StringLengthFieldValidator(message = "%{getText('MSG6',{'999', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public EstadoElemento getEstadoElemento() {
		return estadoElemento;
	}

	public void setEstadoElemento(EstadoElemento estadoElemento) {
		this.estadoElemento = estadoElemento;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
