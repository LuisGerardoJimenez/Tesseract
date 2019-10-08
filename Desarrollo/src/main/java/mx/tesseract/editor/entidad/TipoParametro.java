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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import mx.tesseract.admin.entidad.Colaborador;

@Entity
@Table(name = "tipoparametro")
@NamedNativeQueries({
	@NamedNativeQuery(name = "TipoParametro.consultarTipoParametroById", query = "SELECT t.* FROM tipoparametro t WHERE t.id != :id", resultClass = TipoParametro.class),
	@NamedNativeQuery(name = "TipoParametro.consultarTipoParametroByNombre", query = "SELECT t.* FROM tipoparametro t WHERE t.nombre = :nombre", resultClass = TipoParametro.class),
	})
public class TipoParametro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nombre")
	private String nombre;

	public TipoParametro() {
	}

	public TipoParametro(String nombre) {
		this.nombre = nombre;
	}

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

}
