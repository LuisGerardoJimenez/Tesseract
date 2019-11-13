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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.util.Constantes;
import mx.tesseract.util.GenericInterface;

@NamedNativeQueries({
	@NamedNativeQuery(name = "PostPrecondicion.findByCasoUso", query = "SELECT p.* FROM postprecondicion p WHERE p.CasoUsoElementoid = ?", resultClass = PostPrecondicion.class)
	})

@Entity
@Table(name = "postprecondicion")
public class PostPrecondicion implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "redaccion")
	private String redaccion;
	
	@Column(name = "precondicion")
	private Boolean precondicion;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CasoUsoElementoid", referencedColumnName = "Elementoid")
	private CasoUso casoUso;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "postPrecondicion")
	private List<ReferenciaParametro> referencias = new ArrayList<>();

	public PostPrecondicion() {
	}

	public PostPrecondicion(String redaccion,
			Boolean precondicion, CasoUso casoUso) {
		this.redaccion = redaccion;
		this.precondicion = precondicion;
		this.casoUso = casoUso;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'999', 'caracteres'})}", trim = true, maxLength = "999", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getRedaccion() {
		return this.redaccion;
	}

	public void setRedaccion(String redaccion) {
		this.redaccion = redaccion;
	}
	
	public Boolean isPrecondicion() {
		return precondicion;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG27')}", regex = Constantes.REGEX_COMBO_BOX_SOLO_STRING, shortCircuit = true)
	public Boolean getPrecondicion() {
		return precondicion;
	}
	
	public void setPrecondicion(Boolean precondicion) {
		this.precondicion = precondicion;
	}
	
	public CasoUso getCasoUso() {
		return casoUso;
	}

	public void setCasoUso(CasoUso casoUso) {
		this.casoUso = casoUso;
	}
	
	public List<ReferenciaParametro> getReferencias() {
		return referencias;
	}

	public void setReferencias(List<ReferenciaParametro> referencias) {
		this.referencias = referencias;
	}

}
