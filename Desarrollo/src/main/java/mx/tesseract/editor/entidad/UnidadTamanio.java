package mx.tesseract.editor.entidad;

/*
 * Luis Gerardo Jiménez
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;

import mx.tesseract.util.GenericInterface;

@Entity
@Table(name = "unidadtamanio")
public class UnidadTamanio implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "abreviatura")
	private String abreviatura;

	public UnidadTamanio() {
	}

	public UnidadTamanio(String nombre, String abreviatura) {
		this.nombre = nombre;
		this.abreviatura = abreviatura;
	}

//	Manda el mensaje cuando no se cumple la condicion
	@FieldExpressionValidator(expression = "not (#action.model.tipoDato.id eq 6 and #action.model.unidadTamanio.id eq -1)", message = "%{getText('MSG27')}", shortCircuit= true)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAbreviatura() { 
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	
	 

}
