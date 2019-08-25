package mx.tesseract.editor.entidad;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import mx.tesseract.util.GenericInterface;

@Entity
@Table(name = "tiporeglanegocio")

public class TipoReglaNegocio implements Serializable, GenericInterface {
	
	private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = IDENTITY)
		@Column(name = "id")
		private Integer id;
		
		@Column(name = "nombre")
		private String nombre;
		
		public TipoReglaNegocio() {
			
		}
		
		public TipoReglaNegocio(Integer id, String nombre) {
			this.id = id;
			this.nombre = nombre;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		
		


}
