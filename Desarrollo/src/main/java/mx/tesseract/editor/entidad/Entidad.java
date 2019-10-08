package mx.tesseract.editor.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.ElementoInterface;
import mx.tesseract.util.GenericInterface;

@Entity
@Table(name = "entidad")
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "Elementoid", referencedColumnName = "id")
@DiscriminatorValue("ENT")
@JsonIgnoreProperties(value = { "atributos"})
public class Entidad extends Elemento implements Serializable, GenericInterface, ElementoInterface {

	private static final long serialVersionUID = 1L;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "entidad",  orphanRemoval = true)
	private List<Atributo> atributos = new ArrayList<Atributo>();
	
	public Entidad(){
	}

	public Entidad(String clave, String numero, String nombre,
			Proyecto proyecto, String descripcion, EstadoElemento estadoElemento) {
		super(clave, numero, nombre, proyecto, descripcion, estadoElemento);

	}

	public List<Atributo> getAtributos() {
		return atributos;
	}

	public void setAtributos(List<Atributo> atributos) {
		this.atributos = atributos;
	}

}
