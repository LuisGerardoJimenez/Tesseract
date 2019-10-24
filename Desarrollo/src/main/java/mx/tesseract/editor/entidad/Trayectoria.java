package mx.tesseract.editor.entidad;

/*
 * Luis Gerardo Jiménez
 */

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;

import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.util.GenericInterface;

@Entity
@Table(name = "trayectoria", uniqueConstraints = @UniqueConstraint(columnNames = { "clave",
		"CasoUsoElementoid" }))
@JsonIgnoreProperties(value = { "pasos", "hibernateLazyInitializer" })
public class Trayectoria implements Serializable, Comparable<Trayectoria>, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "clave")
	private String clave;
	
	@Column(name = "alternativa")
	private Boolean alternativa;
	
	@Column(name = "condicion")
	private String condicion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CasoUsoElementoid", referencedColumnName = "Elementoid")
	private CasoUso casoUso;
	
	@Column(name = "finCasoUso")
	private Boolean finCasoUso;
	
	// TODO: FetchType.EAGER
	@Lazy(value = true)
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trayectoria", orphanRemoval = true)
	@OrderBy("numero")
	private List<Paso> pasos = new ArrayList<Paso>();
	
	@Column(name = "Estado")
	private String Estado;

	public Trayectoria() {
	}

	public Trayectoria(String clave, Boolean alternativa, CasoUso casoUso, Boolean finCasoUso) {
		this.clave = clave;
		this.alternativa = alternativa;
		this.casoUso = casoUso;
		this.finCasoUso = finCasoUso;
	}

	public Trayectoria(String clave, Boolean alternativa, String condicion, CasoUso casoUso, Boolean finCasoUso) {
		this.clave = clave;
		this.alternativa = alternativa;
		this.condicion = condicion;
		this.casoUso = casoUso;
		this.finCasoUso = finCasoUso;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5',{'un', 'número'})}", regex = "[0-9]*", shortCircuit = true)
	@IntRangeFieldValidator(message = "%{getText('MSG14',{'El', 'identificador', '0', '2147483647'})}", shortCircuit = true, min = "0", max = "2147483647") // Pendiente
																																							// 4294967295
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'5', 'caracteres'})}", trim = true, maxLength = "5", shortCircuit = true)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Boolean isAlternativa() {
		return this.alternativa;
	}

	public void setAlternativa(Boolean alternativa) {
		this.alternativa = alternativa;
	}

	public String getCondicion() {
		return this.condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public CasoUso getCasoUso() {
		return casoUso;
	}

	public void setCasoUso(CasoUso casoUso) {
		this.casoUso = casoUso;
	}

	public Boolean isFinCasoUso() {
		return this.finCasoUso;
	}

	public void setFinCasoUso(Boolean finCasoUso) {
		this.finCasoUso = finCasoUso;
	}

	public List<Paso> getPasos() {
		return pasos;
	}

	public void setPasos(List<Paso> pasos) {
		this.pasos = pasos;
	}

	public int compareTo(Trayectoria o) {
		return this.clave.compareTo(o.getClave());
	}

	public String getEstado() {
		return this.Estado;
	}

	public void setEstado(String Estado) {
		this.Estado = Estado;
	}

}
