package mx.tesseract.editor.entidad;

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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.GenericInterface;

@Entity
@Table(name = "terminoglosario")

public class TerminoGlosario extends Elemento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Elementoid")
	private Elemento elemento;
		
	public TerminoGlosario(){
		
	}
	public TerminoGlosario(String clave, String numero, String nombre,
			Proyecto proyecto, String descripcion, EstadoElemento estadoElemento) {
		super(clave, numero, nombre, proyecto, descripcion, estadoElemento);
	}
	
	@Override
	@Transient
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'100', 'caracteres'})}", trim = true, maxLength = "100", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex = Constantes.REGEX_CAMPO_ALFABETICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getNombre() {
		return super.getNombre();
	}
	
	@Override
	@Transient
	public void setNombre(String nombre) {
		super.setNombre(nombre);
	}
	
	@Override
	@Transient
	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit= true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG50')}", regex = Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES, shortCircuit = true)
	public String getDescripcion() {
		return super.getDescripcion();
	}
	
	@Override
	@Transient
	public void setDescripcion(String descripcion) {
		super.setDescripcion(descripcion);
	}
	private Proyecto proyecto;

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

}
