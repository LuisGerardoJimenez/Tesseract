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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.GenericInterface;

@NamedNativeQueries({
	@NamedNativeQuery(name = "Modulo.findByIdProyecto", query = "SELECT m.* FROM modulo m WHERE m.Proyectoid = ?", resultClass = Modulo.class)
	})
@Entity
@Table(name = "Modulo")
public class Modulo implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "clave")
	private String clave;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Proyectoid")
	private Proyecto proyecto;
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modulo", orphanRemoval = true)
//	private List<CasoUso> casosdeuso = new ArrayList<CasoUso>();
//	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modulo", orphanRemoval = true)
//	private List<Pantalla> pantallas = new ArrayList<Pantalla>();


	public Modulo() {
	}

	public Modulo(String clave, String nombre, String descripcion,
			Proyecto proyecto) {
		this.clave = clave;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.proyecto = proyecto;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	} 

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'10', 'caracteres'})}", trim = true, maxLength = "10", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex= Constantes.REGEX_CAMPO_ALFANUMERICO_MAYUSCULAS_SIN_ESPACIOS, shortCircuit = true)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'45', 'caracteres'})}", trim = true, maxLength = "45", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex= Constantes.REGEX_CAMPO_ALFABETICO, shortCircuit = true)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'999', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex= Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
//	public List<CasoUso> getCasosdeuso() {
//		return casosdeuso;
//	}
//
//	public void setCasosdeuso(List<CasoUso> casosdeuso) {
//		this.casosdeuso = casosdeuso;
//	}
//	
//	public List<Pantalla> getPantallas() {
//		return pantallas;
//	}
//
//	public void setPantallas(List<Pantalla> pantallas) {
//		this.pantallas = pantallas;
//	}

}
