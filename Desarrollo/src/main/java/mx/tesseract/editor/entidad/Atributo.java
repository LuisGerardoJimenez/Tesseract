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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.opensymphony.xwork2.validator.annotations.DoubleRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

import mx.tesseract.util.Constantes;
import mx.tesseract.util.GenericInterface;

@NamedNativeQueries({
	@NamedNativeQuery(name = "Atributo.findByEntidad", query = "SELECT a.* FROM atributo a WHERE a.EntidadElementoid = ?", resultClass = Atributo.class),
	@NamedNativeQuery(name = "Atributo.findByNameAndEntidad", query = "SELECT a.* FROM atributo a WHERE a.nombre = ? AND a.EntidadElementoid = ?", resultClass = Atributo.class),
	@NamedNativeQuery(name = "Atributo.findByNameAndIdAndEntidad", query = "SELECT a.* FROM atributo a WHERE a.nombre = ? AND a.id = ? AND a.EntidadElementoid = ?", resultClass = Atributo.class),
	})

@Entity
@Table(name = "atributo", uniqueConstraints = @UniqueConstraint(columnNames = {
		"nombre", "EntidadElementoid" }))
public class Atributo implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "obligatorio")
	private boolean obligatorio;
	
	@Column(name = "longitud")
	private Integer longitud;
	
	@Column(name = "formatoArchivo")
	private String formatoArchivo;
	
	@Column(name = "tamanioArchivo")
	private Float tamanioArchivo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UnidadTamanioid", referencedColumnName="id")
	private UnidadTamanio unidadTamanio;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EntidadElementoid", referencedColumnName = "Elementoid")
	private Entidad entidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TipoDatoid", referencedColumnName="id")
	private TipoDato tipoDato;
	
	@Column(name = "otroTipoDato")
	private String otroTipoDato;

	public Atributo() {
	}
	public Atributo(String nombre, Entidad entidad, String descripcion,
			boolean obligatorio) {
		this.nombre = nombre;
		this.entidad = entidad;
		this.descripcion = descripcion;
		this.obligatorio = obligatorio;
	}
	public Atributo(String nombre, Entidad entidad, String descripcion,
			boolean obligatorio, int longitud) {
		this.nombre = nombre;
		this.entidad = entidad;
		this.descripcion = descripcion;
		this.obligatorio = obligatorio;
		this.longitud = longitud;
	}
	
	public Atributo(String nombre, Entidad entidad, String descripcion,
			boolean obligatorio, int longitud, int id) {
		this.nombre = nombre;
		this.entidad = entidad;
		this.descripcion = descripcion;
		this.obligatorio = obligatorio;
		this.longitud = longitud;
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'50', 'caracteres'})}", trim = true, maxLength = "50", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFABETICO, shortCircuit = true)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.trim();
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'999', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex= Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.trim();
	}

	public boolean isObligatorio() {
		return this.obligatorio;
	}

	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

//	Manda el mensaje cuando no se cumple la condicion
	@FieldExpressionValidator(expression = "not ((#action.model.tipoDato.id eq 1 || #action.model.tipoDato.id eq 2 || #action.model.tipoDato.id eq 3) and #action.model.longitud eq null)", message = "%{getText('MSG4')}", shortCircuit= true)
	@IntRangeFieldValidator(message = "%{getText('MSG25',{'1', '10', 'digitos positivos'})}", min = "1", max = "9999999999", shortCircuit= true)
	public Integer getLongitud() {
		return this.longitud;
	}

	public void setLongitud(Integer longitud) {
		this.longitud = longitud;
	}
	
//	Manda el mensaje cuando no se cumple la condicion
	@FieldExpressionValidator(expression = "not (#action.model.tipoDato.id eq 6 and #action.model.formatoArchivo eq '')", message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'50', 'caracteres'})}", trim = true, maxLength = "50", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFABETICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getFormatoArchivo() {
		return this.formatoArchivo;
	}

	public void setFormatoArchivo(String formatoArchivo) {
		this.formatoArchivo = formatoArchivo.trim();
	}

//	Manda el mensaje cuando no se cumple la condicion
	@FieldExpressionValidator(expression = "not (#action.model.tipoDato.id eq 6 and #action.model.tamanioArchivo eq null)", message = "%{getText('MSG4')}", shortCircuit= true)
	@DoubleRangeFieldValidator(message = "%{getText('MSG25',{'1.00', '12', 'digitos positivos'})}", minInclusive = "1.00", maxInclusive = "999999999.99", shortCircuit= true)
	public Float getTamanioArchivo() {
		return tamanioArchivo;
	}
	
	public void setTamanioArchivo(Float tamanioArchivo) {
		this.tamanioArchivo = tamanioArchivo;
	}
	
	@VisitorFieldValidator
	public UnidadTamanio getUnidadTamanio() {
		return unidadTamanio;
	}
	
	public void setUnidadTamanio(UnidadTamanio unidadTamanio) {
		this.unidadTamanio = unidadTamanio;
	}
	
	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}
	
	@VisitorFieldValidator
	public TipoDato getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(TipoDato tipoDato) {
		this.tipoDato = tipoDato;
	}
	
//	Manda el mensaje cuando no se cumple la condicion
	@FieldExpressionValidator(expression = "not (#action.model.tipoDato.id eq 7 and #action.model.otroTipoDato eq '')", message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'45', 'caracteres'})}", trim = true, maxLength = "45", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFABETICO, shortCircuit = true)
	public String getOtroTipoDato() {
		return otroTipoDato;
	}
	
	public void setOtroTipoDato(String otroTipoDato) {
		System.out.println("OtroTipoDatoSET: "+otroTipoDato);
		this.otroTipoDato = otroTipoDato == null? otroTipoDato : otroTipoDato.trim();
	}

	
	
}
