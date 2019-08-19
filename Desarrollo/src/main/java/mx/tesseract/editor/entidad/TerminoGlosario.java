package mx.tesseract.editor.entidad;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.ElementoInterface;
import mx.tesseract.util.GenericInterface;

@Entity
@Table(name = "terminoglosario")
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "Elementoid", referencedColumnName = "id")
@DiscriminatorValue("GLS")
public class TerminoGlosario extends Elemento implements Serializable, GenericInterface, ElementoInterface {
	
	private static final long serialVersionUID = 1L;
		
	public TerminoGlosario(){
		
	}
	
	public TerminoGlosario(String clave, String numero, String nombre,
			Proyecto proyecto, String descripcion, EstadoElemento estadoElemento) {
		super(clave, numero, nombre, proyecto, descripcion, estadoElemento);
	}

}
